package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC008_fileUploadTest extends internetHerokuAppTestBase{

    @Test
    public void TC008_fileUploadTest() throws IOException, InterruptedException {
        logger.info(" Inside TC008_fileUploadTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("File Upload"));
        String filepath = System.getProperty("user.dir") + "//pom.xml";
        Assert.assertEquals(ihk.fileUploadTest(filepath),"File Uploaded!\npom.xml");
    }
}