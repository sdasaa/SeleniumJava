package com.sj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;


import java.lang.reflect.Type;
import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait webWait;
    protected FluentWait<Type> fluWait;
    protected JavascriptExecutor jsExecutor;
    protected Actions actions;
    protected Logger logger = LogManager.getLogger(this.getClass());

    public BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.webWait = new WebDriverWait(this.driver, Duration.ofSeconds(20));
        this.actions = new Actions(this.driver);
        this.jsExecutor = (JavascriptExecutor) this.driver;
        logger.info(" In BasePage Constructor, Invoked by Thread -> {} & Driver -> {} is assigned to all PageObjects",Thread.currentThread().getId(), driver.toString());
    }

    public JavascriptExecutor getJsExecutor() {
        return jsExecutor;
    }

    public WebDriverWait getWebWait() {
        return webWait;
    }

    public Actions getActions() {
        return actions;
    }

    /*
     * All sub-classes needs to implement this abstract method
     * Method to verify if the required page is displayed before execution
     */
    public abstract boolean isAt();

/*
        WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.findElement(By.cssSelector("a[href*='/1']")).click();
        webWait.until( driver -> {
        // System.out.println(driver.getTitle());
        // return !driver.getTitle().equalsIgnoreCase("Practice Page");
        return driver.findElement(By.cssSelector("div#start button")).isDisplayed();
        });
        driver.findElement(By.cssSelector("div#start button")).click();
        webWait.until( driver -> {
        // System.out.println(driver.getTitle());
        // return !driver.getTitle().equalsIgnoreCase("Practice Page");
        return driver.findElement(By.cssSelector("div#finish h4")).isDisplayed();
        });
        System.out.println(driver.findElement(By.cssSelector("div#finish h4")).getText());
*/
    
}