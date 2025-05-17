package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC014_shadowDOMtest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC014_shadowDOMtest.class);
    @Test
    public void TC014_shadowDOMtest() throws IOException, InterruptedException {
        logger.info(" Inside TC014_shadowDOMtest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Shadow DOM"));
        ihk.shadowDOMtest();
    }
}