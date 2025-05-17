package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC012_jQueryMenuListTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC012_jQueryMenuListTest.class);
    @Test
    public void TC012_jQueryMenuListTest() throws IOException, InterruptedException {
        logger.info(" Inside TC012_jQueryMenuListTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("JQuery UI Menus"));
        ihk.jQueryMenuListTest("PDF");
        ihk.jQueryMenuListTest("CSV");
        ihk.jQueryMenuListTest("Excel");
    }
}