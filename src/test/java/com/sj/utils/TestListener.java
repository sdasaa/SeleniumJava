package com.sj.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.sj.TestBase;

import java.io.IOException;

// public class TestListener extends TestBase implements ITestListener {
public class TestListener implements ITestListener {

    public static ExtentSparkReporter extentSparkReporter;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    public static String extentReportsHtml;
    Logger logger = LogManager.getLogger(this.getClass());
    TestEventHandlers testEventHandlers;

    //Constructor
    public TestListener(){
        logger.info(" In TestListener constructor ");
    }

    public static void setupExtentReports(){
        extentReportsHtml = TestBase.userDir + "//extentReports//"+TestBase.formattedDate+"//extentReport_"+TestBase.formattedDate+".html";
        extentSparkReporter = new ExtentSparkReporter(extentReportsHtml);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        /* extentSparkReporter Configuration */
        extentSparkReporter.config().setDocumentTitle("TestAutomationReport");
        extentSparkReporter.config().setReportName("TestExecutionReport");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        /* extentReports Configuration */
        extentReports.setSystemInfo("Tester","Das");
        extentReports.setSystemInfo("OS", "Windows");
        extentReports.setSystemInfo("Browser", TestBase.props.getProperty("browser"));
        extentReports.setSystemInfo("AppUnderTest", TestBase.props.getProperty("url"));
        extentReports.setSystemInfo("screenshotType", TestBase.props.getProperty("screenshotType"));
        extentReports.setSystemInfo("autoOpenExtentReport", TestBase.props.getProperty("autoOpenExtentReport"));
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info(" In onStart(), Invoked by Thread -> {} " +
                                            "getAllTestMethods -> {} \n" +
                                            "getCurrentXmlTest -> {} \n",
                                            Thread.currentThread().getId(), context.getAllTestMethods(), context.getCurrentXmlTest()
                    );
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info(" In onFinish(), Invoked by Thread -> {} ", Thread.currentThread().getId());
    }

    @Override
    public void onTestStart(ITestResult result) {
        //logger.info("Started Execution of Test -> {} and the status of the test is -> {}", result.getName() , result.getStatus());
        logger.info(" In onTestStart(), Invoked by Thread -> {} getName -> {} getTestClass -> {}", Thread.currentThread().getId(), result.getName(), result.getTestClass());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //logger.info("The Test -> {} is PASSED -> {} ", result.getName() , result.getStatus());
        logger.info(" In onTestSuccess(), Invoked by Thread -> {} ", Thread.currentThread().getId());
        extentTest = extentReports.createTest(result.getName());
        //extentReports.createTest(result.getTestClass().getName());
        extentTest.log(Status.PASS , "The test " + result.getName() + " is PASSED ");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info(" In onTestFailure(), Invoked by Thread -> {} ", Thread.currentThread().getId());
        //logger.error(" This Test -> {} has FAILED! -> {} ",result.getName(), result.getStatus() );
        extentTest = extentReports.createTest(result.getName());
        //extentReports.createTest(result.getTestClass().getName());
        extentTest.log(Status.FAIL , "The test " + result.getName() + " is FAILED! ");
        try {
//            extentReportsUtils.extentTest.log(Status.FAIL, result.getName());
//            getScreenShotAsFile(result.getName());
//            testEventHandlers.takeScreenshotAsFile2(result);
            logger.info(" In onTestFailure(), calling addScreenCaptureFromPath() passing test name -> ", result.getName());
            extentTest.addScreenCaptureFromPath(TestEventHandlers.takeScreenshot(result.getName()));
        } catch (Exception e) {
            try {
                logger.error(" Taking screenshot for the Test -> {} has FAILED! -> {} ; Initiating tearDownByForce() ",result.getName(), result.getStatus() );
                testEventHandlers.tearDownByForce();
            } catch (Exception ex) {
                logger.error(" tearDownByForce() has Failed! ");
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        ITestListener.super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn(" This Test -> {} has been SKIPPED -> {} ",result.getName(), result.getStatus() );
        //extentReportsUtils.extentTest.log(Status.SKIP, result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn(" onTestFailedButWithinSuccessPercentage -> {} ", result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logger.warn(" onTestFailedButWithinSuccessPercentage -> {} ", result.getName());
    }


}