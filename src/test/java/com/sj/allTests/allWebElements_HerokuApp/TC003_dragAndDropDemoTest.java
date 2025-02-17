package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC003_dragAndDropDemoTest extends internetHerokuAppTestBase{

    @Test
    public void TC003_dragAndDropDemoTest() throws IOException, InterruptedException {
        logger.info(" In TC003_dragAndDropDemoTest -> test invoked by Thread -> {} & Driver -> {} ", Thread.currentThread().getId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Drag and Drop"));
        Assert.assertTrue(ihk.dragAndDropDemoTest(), "Validation of Drag And Drop");
    }
}