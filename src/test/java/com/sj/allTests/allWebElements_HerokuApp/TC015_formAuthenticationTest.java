package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.DriverManager;
import com.sj.utils.ExcelSheetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC015_formAuthenticationTest extends internetHerokuAppTestBase{
    private static final Logger logger = LogManager.getLogger(TC015_formAuthenticationTest.class);
    @Test(dataProvider = "loginTestdataDP", dataProviderClass = ExcelSheetUtils.class)
    public void TC015_formAuthenticationTest(String uname, String pwd) throws IOException, InterruptedException {
        logger.info(" Inside TC015_formAuthenticationTest -> Thread : {} & Driver {}", Thread.currentThread().threadId(), DriverManager.getDriver());
        logger.info(" User -> "+uname+" Pwd-> "+pwd);
        Assert.assertTrue(ihk.selectFromMenu("Form Authentication"));
        String result = ihk.formAuthenticationTest(uname,pwd);
        ExcelSheetUtils.writeToExcel(uname, pwd, result);
    }
}