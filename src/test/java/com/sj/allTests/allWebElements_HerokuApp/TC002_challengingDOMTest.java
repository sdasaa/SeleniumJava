package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC002_challengingDOMTest extends internetHerokuAppTestBase {

    @Test(groups = {"Smoke", "Sanity", "Regression"})
    public void TC002_challengingDOMTest() throws IOException, InterruptedException {
        logger.info(" In TC002_challengingDOMTest -> test invoked by Thread -> {} & Driver -> {} ", Thread.currentThread().getId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Challenging DOM"));
        Assert.assertTrue(ihk.challengingDomTest(), "Validation of Challenging DOM");
    }

}