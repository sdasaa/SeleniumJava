package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC004_dynamicControlsCBtest extends internetHerokuAppTestBase{

    @Test
    public void TC004_dynamicControlsCBtest() throws IOException, InterruptedException {
        logger.info(" Inside TC004_dynamicControlsCBtest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Dynamic Controls"));
        Assert.assertEquals(ihk.dynamicControlsCBtest("Remove"), "It's gone!");
        Assert.assertEquals(ihk.dynamicControlsCBtest("Add"), "It's back!");
    }

}