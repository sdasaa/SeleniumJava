package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.TestBase;
import com.sj.PageObjects.InternetHerokuApp;
import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;

public class internetHerokuAppTestBase extends TestBase {
    //private static Logger logger = LogManager.getLogger(internetHerokuAppTestBase.class);
    protected WebDriver driver;
    protected RemoteWebDriver remoteWebDriver;
    public Logger logger;

    @BeforeClass
    public void ihkTestSetupBeforeClass() throws IOException {
        logger = LogManager.getLogger(this.getClass());
        logger.info("In ihkTestSetupBeforeClass(), Invoked by Thread -> {} ", Thread.currentThread().getId());
        if(runOnSeleniumGrid) {
            logger.info("In ihkTestSetupBeforeClass(), Invoked by Thread -> {} Running on GRID ", Thread.currentThread().getId());
            setRemoteWebDriver();
        }else {
            logger.info("In ihkTestSetupBeforeClass(), Invoked by Thread -> {} Running on LOCAL ", Thread.currentThread().getId());
            setLocalWebDriver();
        }
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        DriverManager.getDriver().get(url);
        ihk = new InternetHerokuApp(DriverManager.getDriver());
        logger.info("In ihkTestSetupBeforeClass(), Browser instantiated by Thread -> {} and Driver -> {} ",Thread.currentThread().getId(), DriverManager.getDriver());
    }

    public void setLocalWebDriver(){
        driver = browser.equalsIgnoreCase("chrome") ?
                    new ChromeDriver(chromeDriverService, chromeOptions)
                    :
                    new FirefoxDriver(firefoxService, firefoxOptions);
        DriverManager.setDriver(driver);
    }

    void setRemoteWebDriver() throws MalformedURLException {
        remoteWebDriver = browser.equalsIgnoreCase("chrome") ?
                            new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions)
                            :
                            new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);
        DriverManager.setDriver(remoteWebDriver);
    }

    @AfterClass
    public void ihkTestTeardownAfterClass(){
        logger.info("In ihkTestTeardownAfterClass(), Quitting the driver instance of Thread -> {} and Driver -> {} ",Thread.currentThread().getId(), DriverManager.getDriver());
        DriverManager.quitDriver();
    }
}