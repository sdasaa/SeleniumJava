package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC013_keyPressEventTest extends internetHerokuAppTestBase{

    @Test
    public void TC013_keyPressEventTest() throws IOException, InterruptedException {
        logger.info(" Inside TC013_keyPressEventTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Key Presses"));
        Assert.assertTrue(ihk.keyPressEventTest("d"));
        Assert.assertTrue(ihk.keyPressEventTest("a"));
        Assert.assertTrue(ihk.keyPressEventTest("s"));
    }
}