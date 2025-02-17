package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC009_horizontalScrollingTest extends internetHerokuAppTestBase{

    @Test
    public void TC009_horizontalScrollingTest() throws IOException, InterruptedException {
        logger.info(" Inside TC009_horizontalScrollingTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Horizontal Slider"));
        Assert.assertNotEquals(ihk.horizontalScrollingTest("10") , "");
    }
}