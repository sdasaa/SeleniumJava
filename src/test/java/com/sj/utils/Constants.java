package com.sj.utils;

import com.sj.TestBase;

public class Constants {
    public static final String BROWSER = "browser";
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String GRID_ENADLED = "grid.enabled";
    public static final String GRID_URL_FORMAT = "grid.url.format";
    public static final String GRID_HOST = "grid.host";
    public static final String SCREENSHOT_TYPE = "screenshot.type";
    public static final String AUTO_OPEN_EXECUTION_REPORT = "auto.open.execution.report";
    public static final String TEST_URL = "test.url";
    public static final String CLEANUP_LOCAL = "cleanup.local";

    // ExtentReport specific constants:
    public static final String DOCUMENT_TITLE = "TestAutomationReport";
    public static final String REPORT_NAME = "TestExecutionReport";
    public static final String TIME_STAMP_FORMAT = "EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'";
    public static final String TESTER = "Tester";
    public static final String DAS = "Das";
    public static final String OS = "OS";
    public static final String WINDOWS = "Windows";
    public static final String APP_UNDER_TEST = "AppUnderTest";
    public static final String INTERNET_HEROKU_APP = "InternetHerokuApp";

    // process
    public static final String LOCAL_CHROME_KILL = "TASKKILL /F /IM chromedriver.exe /T";

    // Directories & files
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String LOGS_DIR = USER_DIR + "//logs";
    public static final String SELENIUM_BROWSER_LOGS_DIR = LOGS_DIR + "//seleniumBrowserLogs";
    public static final String SELENIUM_BROWSER_LOGS_CURR_DIR = LOGS_DIR + "//seleniumBrowserLogs//" + TestBase.formattedDateAndTime;
    public static final String LOG4J2_DIR = LOGS_DIR + "//log4j";
    public static final String SCREENSHOTS_DIR = USER_DIR + "//screenshots";
    public static final String SCREENSHOTS_CURR_DIR = USER_DIR + "//screenshots//" + TestBase.formattedDateAndTime;
    public static final String EXTENT_REPORTS_DIR = USER_DIR + "//extentReports";
    public static final String EXTENT_REPORTS_CURR_DIR = USER_DIR + "//extentReports//" + TestBase.formattedDateAndTime;

    public static final String DEFAULT_PROPERTIES_FILE = "configurations//defaults.properties";
    public static final String SELENIUM_BROWSER_LOGS_FILE = SELENIUM_BROWSER_LOGS_CURR_DIR + "//" + TestBase.formattedDateAndTime+ ".txt";
    public static final String EXTENT_REPORT_FILE = EXTENT_REPORTS_CURR_DIR + "//" + TestBase.formattedDateAndTime+ ".html";
    public static final String SCREENSHOT_AS_FILE= SCREENSHOTS_CURR_DIR + "//image" + "_%s_" +TestBase.formattedDateAndTime+ ".png";

    // Test data
    public static final String EXCEL_FILE = "testData//loginTestData.xlsx";
    public static final String TARGET_DIR_EXCEL_FILE = "target//loginTestData.xlsx";
}