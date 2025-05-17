package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC004_dynamicControlsCBtest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC004_dynamicControlsCBtest.class);
    @Test
    public void TC004_dynamicControlsCBtest() throws IOException, InterruptedException {
        logger.info(" Inside TC004_dynamicControlsCBtest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Controls"));
        Assert.assertEquals(ihk.dynamicControlsCBtest("Remove"), "It's gone!");
        Assert.assertEquals(ihk.dynamicControlsCBtest("Add"), "It's back!");
    }

}