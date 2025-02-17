package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC011_infiniteScrollTest extends internetHerokuAppTestBase{

    @Test
    public void TC011_infiniteScrollTest() throws IOException, InterruptedException {
        logger.info(" Inside TC011_infiniteScrollTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("Infinite Scroll"));
        ihk.infiniteScrollTest(8);
    }
}