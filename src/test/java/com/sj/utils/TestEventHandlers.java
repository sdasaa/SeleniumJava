package com.sj.utils;

import com.sj.TestBase;
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

public class TestEventHandlers extends TestBase {

    private static final Logger logger = LogManager.getLogger(TestEventHandlers.class);
    private static boolean flag = false;
    private static String currentScreenshotDir = System.getProperty("user.dir") + "//screenshots//" + formattedDate;
    private static String path;

    public static String takeScreenshot(ITestResult result) {
        logger.info(" In TestEventHandlers takeScreenshot() of TestEventHandlers Class ");
        if(!new File(currentScreenshotDir).exists()){
            logger.info(" {} doesn't exist, creating a new dir  ", currentScreenshotDir);
            new File(currentScreenshotDir).mkdirs();
        }
        if(ConfigurationUtilities.getProperty(Constants.SCREENSHOT_TYPE).equalsIgnoreCase("file")) {
            path = takeScreenshotAsFile(result.getName());
            logger.info("screenshotType is -> {} and the Path is -> {}  ", ConfigurationUtilities.getProperty(Constants.SCREENSHOT_TYPE), path);
        }else {
            path = takeScreenshotAsBase64();
            logger.info("screenshotType is -> {} and the Path is -> {}  ", ConfigurationUtilities.getProperty(Constants.SCREENSHOT_TYPE), path);
        }
        return path;
    }

    public static String takeScreenshotAsFile(String testName) {
        File screenshotFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        // replacing the %s in the SCREENSHOT_AS_FILE with testName
        String targetFileName = String.format(Constants.SCREENSHOT_AS_FILE, testName);
        logger.info(" Target ScreenshotAsFile name --> {}", targetFileName);
        File targetFile = new File(targetFileName);
        try {
            FileUtils.copyFile(screenshotFile, targetFile);
        } catch (Exception e) {
            logger.error(" Unable to Copy the screenshot file to destination {} ", e.toString());
            DriverManager.quitDriver();
        }
        logger.info(" Screenshot copied to path --> {}",targetFile.getPath());
        return targetFile.getPath();
    }

    public void takeScreenshotAsFile2(ITestResult result) {
        logger.warn(" Inside takeScreenshotAsFile2 for failed Test {} ", result.getName());
        File screenshotFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(screenshotFile , new File(System.getProperty("user.dir") + "//screenshots//" +formattedDate+"//image_" + result.getName() + ".png"));
        } catch (Exception e) {
            logger.error(" Unable to Copy the screenshot file to destination {} ", e.toString());
            DriverManager.quitDriver();
        }
    }

    public static String takeScreenshotAsBase64(){
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
    }

}