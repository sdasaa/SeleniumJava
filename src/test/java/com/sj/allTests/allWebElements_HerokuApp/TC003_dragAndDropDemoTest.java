package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC003_dragAndDropDemoTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC003_dragAndDropDemoTest.class);
    @Test
    public void TC003_dragAndDropDemoTest() throws IOException, InterruptedException {
        logger.info(" Inside TC003_dragAndDropDemoTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("Drag and Drop"));
        Assert.assertTrue(ihk.dragAndDropDemoTest(), "Validation of Drag And Drop");
    }
}