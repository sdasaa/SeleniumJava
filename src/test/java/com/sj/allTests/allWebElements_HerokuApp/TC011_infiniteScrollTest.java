package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC011_infiniteScrollTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC011_infiniteScrollTest.class);
    @Test
    public void TC011_infiniteScrollTest() throws IOException, InterruptedException {
        logger.info(" Inside TC011_infiniteScrollTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Infinite Scroll"));
        ihk.infiniteScrollTest(8);
    }
}