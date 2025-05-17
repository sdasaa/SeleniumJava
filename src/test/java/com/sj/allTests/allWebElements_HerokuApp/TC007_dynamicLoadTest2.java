package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC007_dynamicLoadTest2 extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC007_dynamicLoadTest2.class);
    @Test
    public void TC007_dynamicLoadTest2() throws IOException, InterruptedException {
        logger.info(" Inside TC007_dynamicLoadTest2 -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Loading"));
        Assert.assertEquals(ihk.dynamicLoadTest("renderedElement"),"Hello World!");
    }
}