package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC005_dynamicControlsIPTest extends internetHerokuAppTestBase{

    private static final Logger logger = LogManager.getLogger(TC005_dynamicControlsIPTest.class);
    @Test
    public void TC005_dynamicControlsIPTest() throws IOException, InterruptedException {
        logger.info(" Inside TC005_dynamicControlsIPTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Controls"));
        Assert.assertEquals(ihk.dynamicControlsIPTest(true), "It's enabled!");
        Assert.assertEquals(ihk.dynamicControlsIPTest(false),"It's disabled!");
    }

}