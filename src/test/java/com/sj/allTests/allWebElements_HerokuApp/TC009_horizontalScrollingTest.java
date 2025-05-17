package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC009_horizontalScrollingTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC009_horizontalScrollingTest.class);
    @Test
    public void TC009_horizontalScrollingTest() throws IOException, InterruptedException {
        logger.info(" Inside TC009_horizontalScrollingTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Horizontal Slider"));
        Assert.assertNotEquals(ihk.horizontalScrollingTest("10") , "");
    }
}