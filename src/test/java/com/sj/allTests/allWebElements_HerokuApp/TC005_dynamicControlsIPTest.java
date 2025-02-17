package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC005_dynamicControlsIPTest extends internetHerokuAppTestBase{

    @Test
    public void TC005_dynamicControlsIPTest() throws IOException, InterruptedException {
        logger.info(" Inside TC005_dynamicControlsIPTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Controls"));
        Assert.assertEquals(ihk.dynamicControlsIPTest(true), "It's enabled!");
        Assert.assertEquals(ihk.dynamicControlsIPTest(false),"It's disabled!");
    }

}