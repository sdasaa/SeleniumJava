package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.PageObjects.InternetHerokuApp;
import com.sj.TestBase;
import com.sj.utils.ConfigurationUtilities;
import com.sj.utils.Constants;
import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class internetHerokuAppTestBase extends TestBase {
    private final Logger logger = LogManager.getLogger(internetHerokuAppTestBase.class);
    @BeforeClass
    public void ihkTestSetupBeforeClass() throws IOException {
        logger.debug("In ihkTestSetupBeforeClass(), Invoked by Thread -> {} ", Thread.currentThread().threadId());
        if(Boolean.parseBoolean(System.getProperty(Constants.GRID_ENADLED))){
            logger.info(" GRID_ENABLED is TRUE, Test execution on GRID ");
            logger.debug("In ihkTestSetupBeforeClass(), Invoked by Thread -> {} ", Thread.currentThread().threadId());
            setRemoteWebDriver();
        }else{
            logger.info(" GRID_ENABLED is FALSE, Test execution on LOCAL ");
            logger.debug("In ihkTestSetupBeforeClass(), Invoked by Thread -> {} ", Thread.currentThread().threadId());
            setLocalWebDriver();
        }
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        DriverManager.getDriver().get(ConfigurationUtilities.getProperty(Constants.TEST_URL));
        ihk = new InternetHerokuApp(DriverManager.getDriver());
    }

    public void setLocalWebDriver(){
        logger.debug(" In setLocalWebDriver(), Creating Local WebDriver instances ");
        driver = ConfigurationUtilities.getProperty(Constants.BROWSER).equalsIgnoreCase(Constants.CHROME) ?
                    new ChromeDriver(chromeDriverService, chromeOptions)
                    :
                    new FirefoxDriver(firefoxService, firefoxOptions);
        DriverManager.setDriver(driver);
    }

    void setRemoteWebDriver() throws MalformedURLException {
        logger.debug(" In setRemoteWebDriver(), Creating RemoteWebDriver instances ");
        // replacing the %s in the GRID_URL_FORMAT with GRID_HOST
        String hubUrl = String.format(ConfigurationUtilities.getProperty(Constants.GRID_URL_FORMAT), ConfigurationUtilities.getProperty(Constants.GRID_HOST));
        logger.info(" The Hub URL is formatted as -> {}", hubUrl);
        remoteWebDriver = ConfigurationUtilities.getProperty(Constants.BROWSER).equalsIgnoreCase(Constants.CHROME) ?
                            new RemoteWebDriver(new URL(hubUrl), chromeOptions)
                            :
                            new RemoteWebDriver(new URL(hubUrl), firefoxOptions);
        DriverManager.setDriver(remoteWebDriver);
    }

    @AfterClass
    public void ihkTestTeardownAfterClass(){
        logger.info("In ihkTestTeardownAfterClass(), Quitting the driver instance of Thread -> {} and Driver -> {} ",Thread.currentThread().threadId(), DriverManager.getDriver());
        DriverManager.quitDriver();
    }
}