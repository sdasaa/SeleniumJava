package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC014_shadowDOMtest extends internetHerokuAppTestBase{

    @Test
    public void TC014_shadowDOMtest() throws IOException, InterruptedException {
        logger.info(" Inside TC014_shadowDOMtest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Shadow DOM"));
        ihk.shadowDOMtest();
    }
}