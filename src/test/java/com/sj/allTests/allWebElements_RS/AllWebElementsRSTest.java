package com.sj.allTests.allWebElements_RS;

import com.sj.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AllWebElementsRSTest extends BaseTest {

    @Test(priority = 0)
    public void launchSiteTest(){
        rs.isAt();
        String title = getDriver().getTitle();
        Assert.assertEquals(title, "Practice Page");
    }

    @Test(priority = 1)
    public void radioButtonTest(){
        Assert.assertTrue(rs.selectRadioButton(0));
        Assert.assertTrue(rs.selectRadioButton(1));
        Assert.assertTrue(rs.selectRadioButton(2));
    }

    @Test(priority = 2)
    public void dynamicDDTest(){
        Assert.assertEquals(rs.selectDynamicDropdown("United States Minor Outlying Islands") , "United States Minor Outlying Islands");
        Assert.assertEquals(rs.selectDynamicDropdown("Solomon Islands") , "Solomon Islands");
        Assert.assertEquals(rs.selectDynamicDropdown("England") , "Country not Found");
    }

    @Test(priority = 3)
    public void staticDDTest(){
        Assert.assertEquals(rs.selectStaticDD("Option1") , "Option1");
        Assert.assertEquals(rs.selectStaticDD("Option2") , "Option2");
    }

    @Test(priority = 4)
    public void checkBoxTest(){
        Assert.assertTrue(rs.selectCheckBox("option1"));
        Assert.assertTrue(rs.selectCheckBox("option2"));
        Assert.assertFalse(rs.selectCheckBox("option4"));
    }

    @Test(priority = 5)
    public void switchWindowTest(){
        rs.switchWindowDemo();
    }

    @Test(priority = 6)
    public void switchTabTest(){
        rs.switchTabDemo();
    }

    @Test(priority = 7)
    public void handleAlertTest(){
        rs.handleAlert("Das", "alert");
        rs.handleAlert("Das", "alert1");
    }

    @Test(priority = 8)
    public void staticWebTableTest(){
        Assert.assertEquals(rs.staticWebTableDemo() , 235);
    }

    @Test(priority = 9)
    public void hiddenElementTest(){
        Assert.assertTrue(rs.hiddenElementDemo());
    }

    @Test(priority = 10)
    public void dynamicWebTableTest(){
        Assert.assertEquals(rs.dynamicWebTableDemo() , 296);
    }

    @Test(priority = 11)
    public void mouseHoverTest(){
        rs.mouseHoverDemo();
    }

    @Test(priority = 12)
    public void iFrameTest(){
        rs.iFrameDemo();
    }
}