package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC010_hoverImagesTest extends internetHerokuAppTestBase{

    @Test
    public void TC010_hoverImagesTest() throws IOException, InterruptedException {
        logger.info(" Inside TC010_hoverImagesTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Hovers"));
        Assert.assertTrue(ihk.hoverImagesTest());
    }
}