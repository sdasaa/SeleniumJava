package com.sj.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sj.TestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    public static ExtentSparkReporter extentSparkReporter;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    public static String extentReportsHtml;
    public static final Logger logger = LogManager.getLogger(TestListener.class);

    public static void setupExtentReports(){
        logger.info(" In setupExtentReports(), initializing defaults, EXTENT_REPORT_FILE -> {} ", Constants.EXTENT_REPORT_FILE);
        extentReportsHtml = Constants.EXTENT_REPORT_FILE;
        extentSparkReporter = new ExtentSparkReporter(extentReportsHtml);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        logger.info(" extentReportsHtml -> {} ", extentReportsHtml);

        /* extentSparkReporter Configuration */
        extentSparkReporter.config().setDocumentTitle(Constants.DOCUMENT_TITLE);
        extentSparkReporter.config().setReportName(Constants.REPORT_NAME);
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat(Constants.TIME_STAMP_FORMAT);

        /* extentReports Configuration */
        extentReports.setSystemInfo(Constants.TESTER,Constants.DAS);
        extentReports.setSystemInfo(Constants.OS, Constants.WINDOWS);
        extentReports.setSystemInfo(Constants.APP_UNDER_TEST, Constants.INTERNET_HEROKU_APP);
        extentReports.setSystemInfo(Constants.BROWSER, ConfigurationUtilities.getProperty(Constants.BROWSER));
        extentReports.setSystemInfo(Constants.SCREENSHOT_TYPE, ConfigurationUtilities.getProperty(Constants.SCREENSHOT_TYPE));
        extentReports.setSystemInfo(Constants.AUTO_OPEN_EXECUTION_REPORT, ConfigurationUtilities.getProperty(Constants.AUTO_OPEN_EXECUTION_REPORT));
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info(" In onStart(), Invoked by Thread -> {} " +
                                            "getAllTestMethods -> {} \n" +
                                            "getCurrentXmlTest -> {} \n",
                                            Thread.currentThread().threadId(), context.getAllTestMethods(), context.getCurrentXmlTest()
                    );
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info(" In onFinish(), Invoked by Thread -> {} ", Thread.currentThread().threadId());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info(" In onTestStart(), Invoked by Thread -> {} getName -> {} getTestClass -> {}", Thread.currentThread().threadId(), result.getName(), result.getTestClass());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info(" In onTestSuccess(), Invoked by Thread -> {} ", Thread.currentThread().threadId());
        logger.info("The Test -> {} is PASSED -> {} ", result.getName() , result.getStatus());
        extentTest = extentReports.createTest(result.getName());
        extentTest.log(Status.PASS , "The test " + result.getName() + " is PASSED ");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info(" In onTestFailure(), Invoked by Thread -> {} ", Thread.currentThread().threadId());
        logger.error(" This Test -> {} has FAILED! -> {} ",result.getName(), result.getStatus() );
        extentTest = extentReports.createTest(result.getName());
        extentTest.log(Status.FAIL , "The test " + result.getName() + " is FAILED! ");
        logger.info(" In onTestFailure(), calling addScreenCaptureFromPath() passing test name -> ", result.getName());
        extentTest.addScreenCaptureFromPath(TestEventHandlers.takeScreenshot(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn(" This Test -> {} has been SKIPPED -> {} ",result.getName(), result.getStatus() );
        extentTest = extentReports.createTest(result.getName());
        extentTest.log(Status.SKIP, result.getName());
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