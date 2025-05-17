package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC013_keyPressEventTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC013_keyPressEventTest.class);
    @Test
    public void TC013_keyPressEventTest() throws IOException, InterruptedException {
        logger.info(" Inside TC013_keyPressEventTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Key Presses"));
        Assert.assertTrue(ihk.keyPressEventTest("d"));
        Assert.assertTrue(ihk.keyPressEventTest("a"));
        Assert.assertTrue(ihk.keyPressEventTest("s"));
    }
}