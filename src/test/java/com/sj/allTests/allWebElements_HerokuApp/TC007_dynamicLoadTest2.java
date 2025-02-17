package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC007_dynamicLoadTest2 extends internetHerokuAppTestBase{

    @Test
    public void TC007_dynamicLoadTest2() throws IOException, InterruptedException {
        logger.info(" Inside TC007_dynamicLoadTest2 -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Loading"));
        Assert.assertEquals(ihk.dynamicLoadTest("renderedElement"),"Hello World!");
    }
}