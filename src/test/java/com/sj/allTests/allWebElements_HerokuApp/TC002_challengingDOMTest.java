package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC002_challengingDOMTest extends internetHerokuAppTestBase {
    private static final Logger logger = LogManager.getLogger(TC002_challengingDOMTest.class);
    @Test
    public void TC002_challengingDOMTest() throws IOException, InterruptedException {
        logger.info(" Inside TC002_challengingDOMTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Challenging DOM"));
        Assert.assertTrue(ihk.challengingDomTest(), "Validation of Challenging DOM");
    }

}