package com.sj.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

public class ExcelSheetUtils {

    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wbook;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static DataFormatter dataFormatter;

    public static int totalRowCount;
    public static int totalColCount;
    public static String excelFilePath = Constants.EXCEL_FILE;
    public static final File excelFile = new File(excelFilePath);
    public static String[][] loginTestdata2D;

    private static Path sourcePath;
    private static Path writablePath;

    public static final Logger logger = LogManager.getLogger(ExcelSheetUtils.class);

    @DataProvider(name = "loginTestdataDP")
    public static String[][] readExcel() throws IOException {
        try {
            dataFormatter = new DataFormatter();
            wbook = new XSSFWorkbook(ConfigurationUtilities.resourceLoader(Constants.EXCEL_FILE));
            sheet = wbook.getSheetAt(0);
            totalRowCount = sheet.getLastRowNum();
            totalColCount = sheet.getRow(totalRowCount).getLastCellNum();
            System.out.println("totalRowCount->"+totalRowCount+" totalColCount->"+totalColCount);
            // [totalColCount-2] since we dont need the columns -> S.No & RESULT
            loginTestdata2D = new String[totalRowCount][totalColCount-2];
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()){
                Row currRow = rows.next();
                int currRowNum = currRow.getRowNum();
                System.out.println("currRowNum->"+currRowNum);
                if(currRowNum > 0){
                    Iterator<Cell> Cells = currRow.cellIterator();
                    while (Cells.hasNext()) {
                        Cell currCell = Cells.next();
                        int currCellNum = currCell.getColumnIndex();
                        System.out.println("currCellNum->"+currCellNum);
                        if(currCellNum > 0 && currCellNum < totalColCount-1) {
                            System.out.println(dataFormatter.formatCellValue(currCell));
                            loginTestdata2D[currRowNum-1][currCellNum-1] = dataFormatter.formatCellValue(currCell);
                        }
                    }
                }
            }
            System.out.println(Arrays.deepToString(loginTestdata2D));
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginTestdata2D;
    }


    /*
        ************* Writing back to an excel under target/test-classes dir ******************

        If the excel path is specified as src/test/resrouces/testData/loginTestData.xlsx
        it will work when running from IDE but will fail while running after packaging,
        Since the structure would be target/test-classes/testData/loginTestData.xlsx

        Now the problem is reading from classpath is easy, classLoader().getResourcerAsStream()
        But writing to it is not, Any artifacts under the target/classes or target/test-classes
        are read-only.

        To overcome this issue, we need to do the following:
            1. Get the classLoader
            2. Locate the source path of the excel from classpath dirs (i.e.) target/test-classes/testData/loginTestdata.xlsx
            3. Create a writable target path thats not read-only, (ex) target/loginTestData.xlsx
            4. Copy the excel from source to target path
            5. write back to the excel
    */
    public static void writeToExcel(String user, String pwd, String result) throws IOException {
        try{
            if(!Files.exists(Paths.get(Constants.TARGET_DIR_EXCEL_FILE))) {
                // getClassLoader()
                ClassLoader classLoader = ExcelSheetUtils.class.getClassLoader();
                // create sourcePath
                sourcePath = Paths.get(classLoader.getResource(Constants.EXCEL_FILE).toURI());
                // create targetPath
                writablePath = Paths.get(Constants.TARGET_DIR_EXCEL_FILE);
                // Move the excel from source to target
                // Files.copy(sourcePath, writablePath);
                Files.move(sourcePath, writablePath);
            }
            dataFormatter = new DataFormatter();
            fi = new FileInputStream(writablePath.toFile());
            wbook = new XSSFWorkbook(fi);
            sheet = wbook.getSheetAt(0);
            totalRowCount = sheet.getLastRowNum();
            totalColCount = sheet.getRow(totalRowCount).getLastCellNum();
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()){
                Row currRow = rows.next();
                int currRowNum = currRow.getRowNum();
                logger.info(" currRowNum -> {} ",currRowNum);
                int count = 0;
                if(currRowNum > 0){
                    Iterator<Cell> Cells = currRow.cellIterator();
                    while (Cells.hasNext()) {
                        Cell currCell = Cells.next();
                        int currCellNum = currCell.getColumnIndex();
                        logger.info(" currRowNum -> {} currCellNum -> {} count -> {} ", currRowNum, currCellNum, count);
                        if(count == 2){
                            logger.info(" Count==2, Reached result column, now writing back the result -> {} to exccel ",dataFormatter.formatCellValue(currCell));
                            currCell.setCellValue(result);
                            fo = new FileOutputStream(writablePath.toFile());
                            wbook.write(fo);
                            fo.close();
                        }else if(count < 2 && currCellNum > 0) {
                            String value = dataFormatter.formatCellValue(currCell);
                            if(value.equalsIgnoreCase(user) || value.equalsIgnoreCase(pwd)) {
                                logger.info("Iterating these cells to reach result column, currCellValue -> {} ",dataFormatter.formatCellValue(currCell));
                                logger.info(" current count -> {} , this will be increased by +1 for the next iteration", count);
                                count++;
                            }
                        }
                    }
                }
            }
            wbook.close();
            fi.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}