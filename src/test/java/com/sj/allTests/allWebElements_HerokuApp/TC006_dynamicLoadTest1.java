package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC006_dynamicLoadTest1 extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC006_dynamicLoadTest1.class);
    @Test
    public void TC006_dynamicLoadTest1() throws IOException, InterruptedException {
        logger.info(" Inside TC006_dynamicLoadTest1 -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Loading"));
        Assert.assertEquals(ihk.dynamicLoadTest("hiddenElement"), "Hello World!");
    }
}