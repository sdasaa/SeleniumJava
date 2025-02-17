package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.BaseTest;
import com.sj.PageObjects.InternetHerokuApp;
import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.time.Duration;

public class internetHerokuAppTestBase extends BaseTest {
    //private static Logger logger = LogManager.getLogger(internetHerokuAppTestBase.class);
    protected WebDriver driver;
    public Logger logger;

    @BeforeClass
    public void ihkTestSetupBeforeClass() throws IOException {
        logger = LogManager.getLogger(this.getClass());
        logger.info("In ihkTestSetupBeforeClass(), Invoked by Thread -> {} ", Thread.currentThread().getId());
        driver = new ChromeDriver(getChromeDriverService(), getChromeOptions());
        DriverManager.setDriver(driver);
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        DriverManager.getDriver().get(url);
        ihk = new InternetHerokuApp(DriverManager.getDriver());
        logger.info("In ihkTestSetupBeforeClass(), Browser instantiated by Thread -> {} and Driver -> {} ",Thread.currentThread().getId(), DriverManager.getDriver());
    }

    @AfterClass
    public void ihkTestTeardownAfterClass(){
        logger.info("In ihkTestTeardownAfterClass(), Quitting the driver instance of Thread -> {} and Driver -> {} ",Thread.currentThread().getId(), DriverManager.getDriver());
        DriverManager.quitDriver();
    }
}