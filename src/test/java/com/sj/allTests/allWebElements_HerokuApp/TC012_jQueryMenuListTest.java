package com.sj.allTests.allWebElements_HerokuApp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TC012_jQueryMenuListTest extends internetHerokuAppTestBase{

    @Test
    public void TC012_jQueryMenuListTest() throws IOException, InterruptedException {
        logger.info(" Inside TC012_jQueryMenuListTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        Assert.assertTrue(ihk.selectFromMenu("JQuery UI Menus"));
        ihk.jQueryMenuListTest("PDF");
        ihk.jQueryMenuListTest("CSV");
        ihk.jQueryMenuListTest("Excel");
    }
}