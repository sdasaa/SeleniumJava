package com.sj;

import com.sj.PageObjects.AllWebElementsRS;
import com.sj.PageObjects.InternetHerokuApp;
import com.sj.utils.ConfigurationUtilities;
import com.sj.utils.Constants;
import com.sj.utils.TestListener;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.NoSuchDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class TestBase {

    protected WebDriver driver;
    protected RemoteWebDriver remoteWebDriver;
    protected InternetHerokuApp ihk;
    protected AllWebElementsRS rs;
    protected static FirefoxOptions firefoxOptions;
    protected static FirefoxDriverService firefoxService;
    protected static ChromeOptions chromeOptions;
    protected static ChromeDriverService chromeDriverService;
    protected static File seleniumBrowserLogFile;
    public static String formattedDate;
    public static String formattedTime;
    public static String formattedDateAndTime;
    private static final Logger logger = LogManager.getLogger(TestBase.class);

    @BeforeSuite(alwaysRun = true)
    public void initializeBeforeSuite() throws IOException {
        logger.info(" In initializeBeforeSuite(), Invoked by Thread -> {}", Thread.currentThread().getId());
        this.setupFormattedDate();
        ConfigurationUtilities.loadDefaultProperties(Constants.DEFAULT_PROPERTIES_FILE);
        if(Boolean.parseBoolean(ConfigurationUtilities.getProperty(Constants.CLEANUP_LOCAL)))
            this.cleanUpLocal();
        switch (ConfigurationUtilities.getProperty(Constants.BROWSER)) {
            case Constants.CHROME -> setupChromeBrowser();
            case Constants.FIREFOX -> setupFirefoxBrowser();
            default -> throw new NoSuchDriverException(" The browser defined is not supported, try again with CHROME or FIREFOX ");
        }
        TestListener.setupExtentReports();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownAfterSuite() throws IOException {
        logger.info(" In tearDownAfterSuite(), Invoked by thread -> {}",Thread.currentThread().getId());
        TestListener.extentReports.flush();
        if(!Boolean.parseBoolean(ConfigurationUtilities.getProperty(Constants.GRID_ENADLED))) {
            logger.info(" Killing unwanted chrome instances ");
            Runtime.getRuntime().exec(Constants.LOCAL_CHROME_KILL);
            if (Boolean.parseBoolean(ConfigurationUtilities.getProperty(Constants.AUTO_OPEN_EXECUTION_REPORT))) {
                try {
                    logger.info(" Excecution completed, Opening the results.html in local browser ");
                    String htmlPath = TestListener.extentReportsHtml;
                    htmlPath = htmlPath.replaceAll("\\//", "\\/");
                    htmlPath = htmlPath.replaceAll("\\\\", "\\/");
                    URI urlToOpenAutomatically = new URI(htmlPath);
                    Desktop.getDesktop().browse(urlToOpenAutomatically);
                } catch (Exception e) {
                    logger.error(" Unable to open TestExecution report !! ");
                    e.printStackTrace();
                }
            }
        }
    }

    void setupChromeBrowser(){
        logger.info(" In setupChromeBrowser(), Invoked by thread -> {}",Thread.currentThread().threadId());
        chromeDriverService = new ChromeDriverService.Builder()
                            .withLogLevel(ChromiumDriverLogLevel.INFO)
                            .withAppendLog(false)
                            .withReadableTimestamp(true)
                            .withLogFile(seleniumBrowserLogFile)
                            .withVerbose(true)
                            .build();
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(10));
    }

    void setupFirefoxBrowser(){
        logger.info(" In setupFirefoxBrowser(), Invoked by thread -> {}",Thread.currentThread().threadId());
        firefoxService = new GeckoDriverService.Builder()
                .withLogLevel(FirefoxDriverLogLevel.INFO)
                .withLogFile(seleniumBrowserLogFile)
                .build();
        firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.setAcceptInsecureCerts(true);
        firefoxOptions.setPageLoadTimeout(Duration.ofSeconds(10));
    }

    void setupFormattedDate(){
        logger.info(" In setupFormattedDate(), Invoked by thread -> {}",Thread.currentThread().threadId());
        // Method-1
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        formattedDate = simpleDateFormat.format(date);
        formattedTime = new SimpleDateFormat("hh-mm-ss").format(date);
        formattedDateAndTime = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(date);
        logger.info(" Method 1 -> formattedDate -> {} formattedTime -> {} formattedDateAndTime -> {}",formattedDate, formattedTime, formattedDateAndTime);
        // Method-2
        LocalDateTime currentDateTime = LocalDateTime.now();
        logger.info(" Method 2 -> currentDateTime -> {}",currentDateTime);
    }

    void cleanUpLocal(){
        logger.info(" CLEANUP_LOCAL is TRUE, Cleanup initiated ");
        this.setupSeleniumBrowserLogs();
        this.systemCleanup();
    }

    void setupSeleniumBrowserLogs(){
        logger.info(" In setupSeleniumBrowserLogs(), Invoked by thread -> {}",Thread.currentThread().threadId());
        File todaysSeleniumLogDir = new File(Constants.SELENIUM_BROWSER_LOGS_DIR);
        if(!todaysSeleniumLogDir.exists()) {
            todaysSeleniumLogDir.mkdirs();
        }
        seleniumBrowserLogFile = new File(Constants.SELENIUM_BROWSER_LOGS_FILE);
    }

    void systemCleanup() {
        logger.info(" In systemCleanup(), Cleaning up obsolete Reports, Screenshots, Selenium & Execution logs ");
        File seleniumLogsDir    = new File(Constants.SELENIUM_BROWSER_LOGS_DIR);
        File executionLogsDir   = new File(Constants.LOG4J2_DIR);
        File extentReportsDir   = new File(Constants.EXTENT_REPORTS_DIR);
        File screenshotDir      = new File(Constants.SCREENSHOTS_DIR);
        logger.info(" Cleaning will be attempted on the following dirs -> \n" +
                            " SELENIUM_BROWSER_LOGS_DIR -> {} \n" +
                            " LOG4J2_DIR -> {} \n" +
                            " EXTENT_REPORTS_DIR -> {} \n" +
                            " SCREENSHOTS_DIR -> {} \n" ,
        seleniumLogsDir.getPath(), executionLogsDir.getPath(), extentReportsDir.getPath(), screenshotDir.getPath());
        try {
            if (Integer.parseInt(FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(seleniumLogsDir)).split(" ")[0]) > 8)
                FileUtils.cleanDirectory(seleniumLogsDir);
            if (Integer.parseInt(FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(extentReportsDir)).split(" ")[0]) > 8)
                FileUtils.cleanDirectory(extentReportsDir);
            if (Integer.parseInt(FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(executionLogsDir)).split(" ")[0]) > 8)
                FileUtils.cleanDirectory(executionLogsDir);
            if (Integer.parseInt(FileUtils.byteCountToDisplaySize(FileUtils.sizeOf(screenshotDir)).split(" ")[0]) > 8)
                FileUtils.cleanDirectory(screenshotDir);
            FileUtils.forceMkdir(new File(Constants.SELENIUM_BROWSER_LOGS_CURR_DIR));
            FileUtils.forceMkdir(new File(Constants.EXTENT_REPORTS_CURR_DIR));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}