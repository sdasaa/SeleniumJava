package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.BaseTest;
import com.sj.PageObjects.InternetHerokuApp;
import com.sj.utils.DriverManager;
import com.sj.utils.ExcelSheetUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InternetHerokuAppTest extends BaseTest {

    InternetHerokuApp ihk;
    public InternetHerokuAppTest() {
        System.out.println(" $$$$$$$$$$$$$$$$$$ Inside Subclass InternetHerokuAppTest constructor $$$$$$$$$$$$$$$$$$ ");
    }

    private Logger logger = LogManager.getLogger(this.getClass());

    @BeforeMethod
    public void ihkSetupBeforeMethod() throws IOException {
        logger.info(" Started Execution of ihkSetupBeforeMethod() ");
        DriverManager.setDriver(new ChromeDriver(getChromeDriverService(), getChromeOptions()));
        getThreadLocalDriver().manage().window().maximize();
        getThreadLocalDriver().manage().deleteAllCookies();
        getThreadLocalDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getThreadLocalDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        getThreadLocalDriver().get(url);
        ihk = new InternetHerokuApp(DriverManager.getDriver());
        System.out.println("Browser created by Thread : " + Thread.currentThread().getId() + " and Driver reference is : " + getThreadLocalDriver());

    }

    @AfterMethod(alwaysRun = true)
    public void ihkTeardownAfterMethod(){
        System.out.println(" Inside @ihkTeardownAfterMethod -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        //getThreadLocalDriver().quit();
        DriverManager.quitDriver();
    }

    @Test(priority = 1)
    public void brokenImagesTest() throws IOException, InterruptedException {
        System.out.println(" Inside brokenImagesTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        List<Boolean> expectedList = new ArrayList<>();
        expectedList.add(false);
        expectedList.add(false);
        expectedList.add(true);
        Assert.assertTrue(ihk.selectFromMenu("Broken Images"));
        Assert.assertEquals(ihk.brokenImagesTest() , expectedList , "Validation of Broken Images");
    }

    @Test(priority = 2)
    public void challengingDOMTest() throws IOException, InterruptedException {
        System.out.println(" Inside challengingDOMTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Challenging DOM"));
        Assert.assertTrue(ihk.challengingDomTest(), "Validation of Challenging DOM");
    }

    @Test(priority = 3)
    public void dragAndDropDemoTest() throws IOException, InterruptedException {
        System.out.println(" Inside challengingDOMTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Drag and Drop"));
        Assert.assertTrue(ihk.dragAndDropDemoTest(), "Validation of Drag And Drop");
    }

    @Test(priority = 4)
    public void dynamicControlsCBtest() throws IOException, InterruptedException {
        System.out.println(" Inside dynamicControlsCBtest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Dynamic Controls"));
        Assert.assertEquals(ihk.dynamicControlsCBtest("Remove"), "It's gone!");
        Assert.assertEquals(ihk.dynamicControlsCBtest("Add"), "It's back!");
    }

    @Test(priority = 5)
    public void dynamicControlsIPTest() throws IOException, InterruptedException {
        System.out.println(" Inside dynamicControlsIPTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Dynamic Controls"));
        Assert.assertEquals(ihk.dynamicControlsIPTest(true), "It's enabled!");
        Assert.assertEquals(ihk.dynamicControlsIPTest(false),"It's disabled!");
    }

    @Test(priority = 6)
    public void dynamicLoadTest1() throws IOException, InterruptedException {
        System.out.println(" Inside dynamicLoadTest1 -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Dynamic Loading"));
        Assert.assertEquals(ihk.dynamicLoadTest("hiddenElement"), "Hello World!");
    }

    @Test(priority = 7)
    public void dynamicLoadTest2() throws IOException, InterruptedException {
        System.out.println(" Inside dynamicLoadTest2 -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Dynamic Loading"));
        Assert.assertEquals(ihk.dynamicLoadTest("renderedElement"),"Hello World!");
    }

    @Test(priority = 8)
    public void fileUploadTest() throws IOException, InterruptedException {
        System.out.println(" Inside fileUploadTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("File Upload"));
        String filepath = System.getProperty("user.dir") + "//pom.xml";
        Assert.assertEquals(ihk.fileUploadTest(filepath),"File Uploaded!\npom.xml");
    }

    @Test(priority = 9)
    public void horizontalScrollingTest() throws IOException, InterruptedException {
        System.out.println(" Inside horizontalScrollingTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Horizontal Slider"));
        Assert.assertNotEquals(ihk.horizontalScrollingTest("10") , "");
    }

    @Test(priority = 10)
    public void hoverImagesTest() throws IOException, InterruptedException {
        System.out.println(" Inside hoverImagesTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Hovers"));
        Assert.assertTrue(ihk.hoverImagesTest());
    }

    @Test(priority = 11)
    public void infiniteScrollTest() throws IOException, InterruptedException {
        System.out.println(" Inside infiniteScrollTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Infinite Scroll"));
        ihk.infiniteScrollTest(8);
    }

    @Test(priority = 12)
    public void jQueryMenuListTest() throws IOException, InterruptedException {
        System.out.println(" Inside jQueryMenuListTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("JQuery UI Menus"));
        ihk.jQueryMenuListTest("PDF");
        ihk.jQueryMenuListTest("CSV");
        ihk.jQueryMenuListTest("Excel");
    }

    @Test(priority = 13)
    public void keyPressEventTest() throws IOException, InterruptedException {
        System.out.println(" Inside keyPressEventTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Key Presses"));
        Assert.assertTrue(ihk.keyPressEventTest("d"));
        Assert.assertTrue(ihk.keyPressEventTest("a"));
        Assert.assertTrue(ihk.keyPressEventTest("s"));
    }

    @Test(priority = 14)
    public void shadowDOMtest() throws IOException, InterruptedException {
        System.out.println(" Inside shadowDOMtest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());

        Assert.assertTrue(ihk.selectFromMenu("Shadow DOM"));
        ihk.shadowDOMtest();
    }

    @Test(priority = 15, dataProvider = "loginTestdataDP", dataProviderClass = ExcelSheetUtils.class)
    public void formAuthenticationTest(String uname, String pwd) throws IOException, InterruptedException {
        System.out.println(" Inside formAuthenticationTest DP example -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Form Authentication"));
        ihk.formAuthenticationTest(uname,pwd);
    }


/*
    @BeforeTest
    public void ihkBeforeTest() {
        System.out.println("**************************** Inside @ihkBeforeTest ****************************");
    }
    @BeforeTest
    public void ihkBeforeTest2() {
        System.out.println("**************************** Inside @ihkBeforeTest2 ****************************");
    }

    @AfterTest
    public void ihkAfterTest() {
        System.out.println("**************************** Inside @ihkAfterTest ****************************");
    }
    @AfterTest
    public void ihkAfterTest2() {
        System.out.println("**************************** Inside @ihkAfterTest2 ****************************");
    }
    /*
         WebDriverManager.chromedriver().setup();
         driver = new FirefoxDriver(firefoxService , firefoxOptions);
         threadLocalDriver.set(new FirefoxDriver());
         driver = new EdgeDriver();
         driver = new ChromeDriver();
        driver = new ChromeDriver(service , options);
        threadLocalDriver.set(driver);
        this.driver = threadLocalDriver.get();
        threadLocalDriver.set(new EdgeDriver());
        threadLocalDriver.set(new FirefoxDriver());
        threadLocalDriver.set(new FirefoxDriver());
        threadLocalDriver.set(new ChromeDriver(chromeService, chromeOptions));
                //WebDriverManager.chromedriver().setup();
                        //getScreenShotAsFile();
        //ihk = new InternetHerokuApp(getThreadLocalDriver());
 */
}