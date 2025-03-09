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
    @Test(groups = {"Smoke", "Sanity", "Regression"})
    public void TC001_brokenImagesTest() {
        logger.info(" In TC001_brokenImagesTest -> test invoked by Thread -> {} & Driver -> {} ", Thread.currentThread().getId(), DriverManager.getDriver());
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