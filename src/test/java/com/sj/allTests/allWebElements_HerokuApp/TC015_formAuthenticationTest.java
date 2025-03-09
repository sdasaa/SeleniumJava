package com.sj.allTests.allWebElements_HerokuApp;

import com.sj.utils.ExcelSheetUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


public class TC015_formAuthenticationTest extends internetHerokuAppTestBase{

    @Test(dataProvider = "loginTestdataDP", dataProviderClass = ExcelSheetUtils.class)
    public void TC015_formAuthenticationTest(String uname, String pwd) throws IOException, InterruptedException {
        logger.info(" Inside TC015_formAuthenticationTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " +getThreadLocalDriver());
        logger.info(" User -> "+uname+" Pwd-> "+pwd);
        Assert.assertTrue(ihk.selectFromMenu("Form Authentication"));
        String result = ihk.formAuthenticationTest(uname,pwd);
        ExcelSheetUtils.writeToExcel(uname, pwd, result);
    }
}