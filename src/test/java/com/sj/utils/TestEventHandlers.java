package com.sj.utils;

import com.sj.BaseTest;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Driver;

public class TestEventHandlers extends BaseTest {

    private static Logger logger = LogManager.getLogger(TestEventHandlers.class);
    private static int counter = 1;
    private static boolean flag = false;
    private static String currentScreenshotDir = System.getProperty("user.dir") + "//screenshots//" + formattedDate;
    private static String path;

    public TestEventHandlers(){
        logger.info("***** Inside Subclass TestEventHandlers constructor *****");
    }

    public static String takeScreenshot(String testName) throws IOException {
        logger.info(" In TestEventHandlers takeScreenshot() of TestEventHandlers Class ");
        if(!new File(currentScreenshotDir).exists()){
            logger.info(" {} doesn't exist, creating a new dir  ", currentScreenshotDir);
            new File(currentScreenshotDir).mkdirs();
        }
        if(screenshotType.equalsIgnoreCase("file")) {
            path = takeScreenshotAsFile(testName);
            logger.info("screenshotType is -> {} and the Path is -> {}  ", screenshotType, path);
        }else {
            path = takeScreenshotAsBase64();
            logger.info("screenshotType is -> {} and the Path is -> {}  ", screenshotType, path);
        }
        return path;
    }

    public static String takeScreenshotAsFile(String testName) throws IOException {
        File screenshotFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        String targetFileName = System.getProperty("user.dir") + "//screenshots//"+formattedDate+"//image_" + testName + "_" +formattedDate+".png";
        logger.info("In takeScreenshotAsFile() targetFileName -> ",targetFileName);
        File targetFile = new File(targetFileName);
        FileUtils.copyFile(screenshotFile, targetFile);
        //FileUtils.copyFile(screenshotFile , new File(System.getProperty("user.dir") + "/screenshots/image_"+testName+"_"+counter+".png"));
        //File screenshotFile = ((TakesScreenshot) getThreadLocalDriver()).getScreenshotAs(OutputType.FILE);
        counter++;
        logger.info(" Returning this path --> ",targetFile.getPath());
        return targetFile.getPath();
    }

    public void takeScreenshotAsFile2(ITestResult result) throws IOException {
        logger.warn(" Inside takeScreenshotAsFile2 for failed Test {} ", result.getName());
        File screenshotFile = ((TakesScreenshot) getThreadLocalDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile , new File(System.getProperty("user.dir") + "//screenshots//" +formattedDate+"//image_" + result.getName() + ".png"));
    }

    public static String takeScreenshotAsBase64(){
        return ((TakesScreenshot) getThreadLocalDriver()).getScreenshotAs(OutputType.BASE64);
    }

    void tearDownByForce() throws IOException {
        Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe /T");
    }
}