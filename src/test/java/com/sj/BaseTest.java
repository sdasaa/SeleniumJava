package com.sj;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sj.PageObjects.AllWebElementsRS;
import com.sj.PageObjects.InternetHerokuApp;
//import com.sj.allTests.allWebElements_HerokuApp.InternetHerokuAppTest;
import com.sj.utils.DriverManager;
import com.sj.utils.ExtentReportsUtils;
import com.sj.utils.TestEventHandlers;
import com.sj.utils.TestListener;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chromium.ChromiumDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.time.LocalDateTime;

public class BaseTest {

    protected WebDriver driver;
    protected static FirefoxOptions firefoxOptions;
    protected static FirefoxDriverService firefoxService;
    protected static ChromeOptions chromeOptions;
    protected static ChromeDriverService chromeDriverService;
    public static TestEventHandlers testEventHandlers;
    public static ExtentReportsUtils extentReportsUtils;
    public static File seleniumBrowserLogs;
    public static String formattedDate;
    public static String browser;
    public static String url;
    protected static String screenshotType;
    public static Properties props;
    public static String userDir = System.getProperty("user.dir");

    protected BaseTest bt;
    protected AllWebElementsRS rs;
    protected InternetHerokuApp ihk;
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    
    public WebDriver getDriver() {
        return driver;
    }
    public ChromeOptions getChromeOptions() {
        return chromeOptions;
    }
    public ChromeDriverService getChromeDriverService() {
        return chromeDriverService;
    }
    public static WebDriver getThreadLocalDriver() {
        System.out.println("->"+threadLocalDriver.getClass().toString());
        return threadLocalDriver.get();
    }

    @BeforeSuite(alwaysRun = true)
    // @Parameters({"TestModule"})
    public void initializeBeforeSuite() throws IOException {
        logger.info(" In initializeBeforeSuite(), Invoked by Thread -> {}",Thread.currentThread().getId());
        cleanupLogs();
        setupFormattedDate();
        loadGlobalProperties();
        setupSeleniumBrowserLogs();
        if(browser.equalsIgnoreCase("chrome"))
            setupChromeBrowser();
        else if (browser.equalsIgnoreCase("firefox"))
            setupFirefoxBrowser();
        TestListener.setupExtentReports();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownAfterSuite() throws IOException {
        logger.info(" In tearDownAfterSuite(), Invoked by thread -> {}",Thread.currentThread().getId());
        TestListener.extentReports.flush();
        Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe /T");
        if(Boolean.parseBoolean(props.getProperty("autoOpenExtentReport"))) {
            try {
                String htmlPath = TestListener.extentReportsHtml;
                htmlPath = htmlPath.replaceAll("\\//", "\\/");
                htmlPath = htmlPath.replaceAll("\\\\", "\\/");
                URI urlToOpenAutomatically = new URI(htmlPath);
                Desktop.getDesktop().browse(urlToOpenAutomatically);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Properties loadGlobalProperties() throws IOException {
        props = new Properties();
        FileReader reader = new FileReader(userDir + "//globalProperties.properties");
        props.load(reader);
        url = props.getProperty("testUrl");
        browser = props.getProperty("browser");
        screenshotType = props.getProperty("screenshotType");
        logger.info(" In loadGlobalProperties(), The following values have been set globally url->{} browser->{} screenshotType->{}", url, browser, screenshotType);
        return props;
    }

    void setupChromeBrowser(){
        logger.info(" In setupChromeBrowser(), Invoked by thread -> {}",Thread.currentThread().getId());
        chromeDriverService = new ChromeDriverService.Builder()
                            .withLogLevel(ChromiumDriverLogLevel.INFO)
                            .withAppendLog(false)
                            .withReadableTimestamp(true)
                            .withLogFile(seleniumBrowserLogs)
                            .withVerbose(true)
                            .build();
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
    }

    void setupFirefoxBrowser(){
        logger.info(" In setupFirefoxBrowser(), Invoked by thread -> {}",Thread.currentThread().getId());
        firefoxService = new GeckoDriverService.Builder()
                .withLogLevel(FirefoxDriverLogLevel.INFO)
                .withLogFile(seleniumBrowserLogs)
                .build();
        firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
    }

    void setupFormattedDate(){
        logger.info(" In setupFormattedDate(), Invoked by thread -> {}",Thread.currentThread().getId());
        //Method-1
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        Date date = new Date();
        formattedDate = formatter.format(date).toString();
        logger.info(" Method 1 -> formattedDate -> {}",formattedDate);
        // Method-2
        LocalDateTime currentDateTime = LocalDateTime.now();
        logger.info(" Method 2 -> currentDateTime -> {}",currentDateTime);
    }
    void setupSeleniumBrowserLogs(){
        logger.info(" In setupSeleniumBrowserLogs(), Invoked by thread -> {}",Thread.currentThread().getId());
        File currentSeleniumLogs = new File(userDir + "//logs//SeleniumBrowserlogs//"+formattedDate);
        if(!currentSeleniumLogs.exists())
            currentSeleniumLogs.mkdirs();
        seleniumBrowserLogs = new File(currentSeleniumLogs +"//seleniumLogs.txt");
    }

    TestEventHandlers setupTestEventHandlers() {
        logger.info(" In setupTestEventHandlers(), Invoked by thread -> {}",Thread.currentThread().getId());
        testEventHandlers = new TestEventHandlers();
        return testEventHandlers;
    }

    void cleanupLogs() throws IOException {
        logger.info(" In cleanupLogs(), Cleaning up Selenium browser logs ");
        File oldSeleniumLogs = new File(BaseTest.userDir + "\\logs\\SeleniumBrowserLogs");
        File oldTestExecutionLogs = new File(BaseTest.userDir + "\\logs\\logs4j2");
        if(!FileUtils.isEmptyDirectory(oldSeleniumLogs))
            FileUtils.cleanDirectory(oldSeleniumLogs);
//        if(!FileUtils.isEmptyDirectory(oldTestExecutionLogs))
//            FileUtils.cleanDirectory(oldTestExecutionLogs);
    }

    ExtentReportsUtils setupExtentReportsUtils(){
        logger.info(" In setupExtentReports(), Invoked by thread -> {}",Thread.currentThread().getId());
        extentReportsUtils = new ExtentReportsUtils();
        extentReportsUtils.setupExtentReports();
        return extentReportsUtils;
    }

    /*
        formattedDate = formattedDate.replaceAll("[^a-zA-Z0-9]", "_");
        formattedDate = formattedDate.replaceAll("/", "");
        formattedDate = formattedDate.replaceAll(":", "");
        System.out.println("AF formattedDate ->" + formattedDate);
         File file = new File(System.getProperty("user.dir") + "//logs//SeleniumBrowserlogs//Log_" + dummy + ".log");
         File file = new File(System.getProperty("user.dir") + "//logs//SeleniumBrowserlogs//Log_" + LocalDateTime.now() + ".log");
        seleniumBrowserLogs = new File(System.getProperty("user.dir") + "//logs//SeleniumBrowserlogs//log_1.txt");

        @AfterMethod
        public void AfterMethod(String testModule){
            System.out.println("**************************** Inside @AfterMethod ****************************");
            driver.quit();
            driver = new ChromeDriver(service , options);
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.get(url);
        }
        @AfterMethod
        public void tearDown(){
            System.out.println("**************************** Inside @AfterTest ****************************");
            driver.quit();
        }
    }
        protected TestEventHandlers getScreenShotAsFile(String testName) throws IOException {
        logger.warn(" The test -> {} has failed !! Initiating Screenshot ", testName);
        if(this.testEventHandlers == null) {
            System.out.println(" testEventHandlers ref is null, creating a new obj !! ");
            testEventHandlers = new TestEventHandlers();
        }
        testEventHandlers = new TestEventHandlers();
        testEventHandlers.takeScreenshot(testName);
        return testEventHandlers;
    }
    */
}