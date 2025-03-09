package com.sj.utils;

import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.testng.annotations.DataProvider;

import java.io.*;
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
    public static String excelFilePath = System.getProperty("user.dir") + "//src//test//resources//loginTestData.xlsx";
    public static final File excelFile = new File(excelFilePath);
    public static String[][] loginTestdata2D;

    @DataProvider(name = "loginTestdataDP")
    public static String[][] readExcel() throws IOException {
        try {
            dataFormatter = new DataFormatter();
            fi = new FileInputStream(excelFile);
            wbook = new XSSFWorkbook(fi);
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
        }finally {
            fi.close();
        }
        return loginTestdata2D;
    }

    public static void writeToExcel(String user, String pwd, String result) throws IOException {
        try{
            dataFormatter = new DataFormatter();
            fi = new FileInputStream(excelFile);
            wbook = new XSSFWorkbook(fi);
            sheet = wbook.getSheetAt(0);
            totalRowCount = sheet.getLastRowNum();
            totalColCount = sheet.getRow(totalRowCount).getLastCellNum();
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()){
                Row currRow = rows.next();
                int currRowNum = currRow.getRowNum();
                System.out.println("currRowNum->"+currRowNum);
                int count = 0;
                if(currRowNum > 0){
                    Iterator<Cell> Cells = currRow.cellIterator();
                    while (Cells.hasNext()) {
                        Cell currCell = Cells.next();
                        int currCellNum = currCell.getColumnIndex();
                        System.out.println("currCellNum->"+currCellNum);
                        if(count == 2){
                            currCell.setCellValue(result);
                            fo = new FileOutputStream(excelFilePath);
                            wbook.write(fo);
                            System.out.println("Count==2"+dataFormatter.formatCellValue(currCell));
                        }else if(count < 2 && currCellNum > 0) {
                            String value = dataFormatter.formatCellValue(currCell);
                            if(value.equalsIgnoreCase(user) || value.equalsIgnoreCase(pwd)) {
                                count++;
                                System.out.println("Count-> "+count+" "+dataFormatter.formatCellValue(currCell));
                            }
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            wbook.close();
            fo.close();
            fi.close();
        }
    }
    public static void main(String[] args) throws IOException {
/*        readExcel();
        writeToExcel("admin1", "pass1", "Fail");
        writeToExcel("admin2", "pass2", "Fail");
        writeToExcel("admin3", "pass3", "Fail");
        writeToExcel("tomsmith", "SuperSecretPassword!", "Pass");
        String s = "sudhakar";
        String result = s.contains("hak")? "True" : "False";
        System.out.println(result);*/
    }

}