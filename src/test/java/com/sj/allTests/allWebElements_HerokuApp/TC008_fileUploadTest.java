package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC008_fileUploadTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC008_fileUploadTest.class);
    @Test
    public void TC008_fileUploadTest() throws IOException, InterruptedException {
        logger.info(" Inside TC008_fileUploadTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        Assert.assertTrue(ihk.selectFromMenu("File Upload"));
        String filepath = System.getProperty("user.dir") + "//pom.xml";
        Assert.assertEquals(ihk.fileUploadTest(filepath),"File Uploaded!\npom.xml");
    }
}