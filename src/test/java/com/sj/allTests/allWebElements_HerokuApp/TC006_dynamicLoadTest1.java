package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC006_dynamicLoadTest1 extends internetHerokuAppTestBase{

    @Test
    public void TC006_dynamicLoadTest1() throws IOException, InterruptedException {
        logger.info(" Inside TC006_dynamicLoadTest1 -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Loading"));
        Assert.assertEquals(ihk.dynamicLoadTest("hiddenElement"), "Hello World!");
    }
}