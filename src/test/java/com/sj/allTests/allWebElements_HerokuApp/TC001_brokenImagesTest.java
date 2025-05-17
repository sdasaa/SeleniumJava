package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TC001_brokenImagesTest extends internetHerokuAppTestBase {
    private static final Logger logger = LogManager.getLogger(TC001_brokenImagesTest.class);
    @Test
    public void TC001_brokenImagesTest() {
        logger.info(" Inside TC001_brokenImagesTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        List<Boolean> expectedList = new ArrayList<>();
        expectedList.add(false);
        expectedList.add(false);
        expectedList.add(true);
        Assert.assertTrue(ihk.selectFromMenu("Broken Images"));
        try {
            Assert.assertEquals(ihk.brokenImagesTest(), expectedList, "Validation of Broken Images");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}