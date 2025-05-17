package com.sj.utils;

import com.sj.TestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        threadLocalDriver.set(driver);
        logger.info("In DriverManager-setDriver(), Invoked by Thread -> {} & Setting driver -> {}", Thread.currentThread().threadId(), DriverManager.threadLocalDriver.get().toString());
    }

    public static void quitDriver() {
        logger.info("In DriverManager-quitDriver(), Invoked by Thread -> {} & Removing driver -> {}", Thread.currentThread().threadId(), DriverManager.threadLocalDriver.get().toString());
        if(Boolean.parseBoolean(ConfigurationUtilities.getProperty(Constants.GRID_ENADLED))) {
            logger.info(" GRID_ENABLED is TRUE, quitting all WebDriver instances");
            threadLocalDriver.get().quit();
        }else {
            logger.info(" GRID_ENABLED is FALSE, Closing this WebDriver instance alone -> {}", Thread.currentThread().threadId());
            threadLocalDriver.get().close();
        }
        threadLocalDriver.remove();
    }
}