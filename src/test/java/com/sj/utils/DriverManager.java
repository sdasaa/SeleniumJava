package com.sj.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    public static WebDriver getDriver() {
        //logger.info("In DriverManager-getDriver(), Invoked by Thread -> {} & Getting driver -> {}", Thread.currentThread().getId(), DriverManager.threadLocalDriver.get().toString());
        return threadLocalDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        DriverManager.threadLocalDriver.set(driver);
        logger.info("In DriverManager-setDriver(), Invoked by Thread -> {} & Setting driver -> {}", Thread.currentThread().getId(), DriverManager.threadLocalDriver.get().toString());
    }

    public static void quitDriver() {
        logger.info("In DriverManager-quitDriver(), Invoked by Thread -> {} & Removing driver -> {}", Thread.currentThread().getId(), DriverManager.threadLocalDriver.get().toString());
        //DriverManager.threadLocalDriver.get().quit();
        DriverManager.threadLocalDriver.get().close();
        DriverManager.threadLocalDriver.remove();
    }
}