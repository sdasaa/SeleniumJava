package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC010_hoverImagesTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC010_hoverImagesTest.class);
    @Test
    public void TC010_hoverImagesTest() throws IOException, InterruptedException {
        logger.info(" Inside TC010_hoverImagesTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Hovers"));
        Assert.assertTrue(ihk.hoverImagesTest());
    }
}