package com.sj.PageObjects;

import com.sj.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AllWebElementsRS extends BasePage {

    WebDriver dirver;

    public AllWebElementsRS(WebDriver driver){
        super(driver);
        this.dirver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "body>h1")
    private WebElement headerElm;

    @FindBy(css = "#radio-btn-example input")
    private List<WebElement> radioButtonList;

    @FindBy(css = "input#autocomplete")
    private WebElement dynamicDDInput;

    @FindBy(css = ".ui-autocomplete")
    private WebElement dynamicDDautoComplete;

    @FindBy(css = "#dropdown-class-example")
    private WebElement staticDD;

    @FindBy(css = "#checkbox-example input")
    private List<WebElement> checkBoxList;

    @FindBy(css = "#openwindow")
    private WebElement switchWindowBtn;

    @FindBy(css = "fieldset #opentab")
    private WebElement switchTabBtn;

    @FindBy(css = "[name*='enter-name']")
    private WebElement alertInput;

    @FindBy(css = "#alertbtn")
    private WebElement alertBtn;

    @FindBy(css = "#confirmbtn")
    private WebElement alertConfirmBtn;

    @FindBy(css = "table[name='courses']")
    private WebElement staticWebTable;

    @FindBy(css = "#hide-textbox")
    private WebElement hideElemBtn;

    @FindBy(css = "#show-textbox")
    private WebElement showElemBtn;

    @FindBy(css = "#displayed-text")
    private WebElement hiddenElm;

    @FindBy(css = ".tableFixHead table#product tbody")
    private WebElement dynamicTableElm;

    @FindBy(css = "div.mouse-hover")
    private WebElement mouseHoverBtn;

    @FindBy(css = "iframe#courses-iframe")
    private WebElement iFrameCourses;

    @FindBy(css = "div[class*='logo-outer']")
    private WebElement iniFrameLogoElm;

    @FindBy(css = "div.header-upper nav[class*='main-menu'] li")
    private List<WebElement>  iniFrameHeaderOptionList;

    @Override
    public boolean isAt() {
        this.getWebWait().until(ExpectedConditions.visibilityOf(this.headerElm));
        return this.headerElm.isDisplayed();
    }

    public boolean selectRadioButton(int index){
        WebElement[] arr = this.radioButtonList.toArray(new WebElement[0]);
        arr[index].click();
        return arr[index].isSelected();
    }

    public String selectDynamicDropdown(String country){
        this.dynamicDDInput.clear();
        this.dynamicDDInput.sendKeys(country.substring(0, 2));
        getWebWait().until( d -> {
           return this.dynamicDDautoComplete.isDisplayed();
        });
        List <WebElement> countries = this.dynamicDDautoComplete.findElements(By.cssSelector("div"));
        for(WebElement elm : countries) {
            System.out.println("Country : " + elm.getText());
            String result = elm.getText();
            if (elm.getText().equalsIgnoreCase(country)) {
                elm.click();
                return result;
            }
        }
        return "Country not Found";
    }

    public String selectStaticDD(String option){
        Select s = new Select(staticDD);
        s.selectByIndex(1);
        s.selectByValue(option.toLowerCase());
        s.selectByVisibleText(option);
        System.out.println(s.getFirstSelectedOption().getText());
        return s.getFirstSelectedOption().getText();
    }

    public boolean selectCheckBox(String option) {
        for (WebElement cb : this.checkBoxList) {
            System.out.println("checkbox id attribute : " + cb.getAttribute("id"));
            System.out.println("checkbox value attribute : " + cb.getAttribute("value"));
            System.out.println("checkbox name attribute : " + cb.getAttribute("name"));
            System.out.println("checkbox type attribute : " + cb.getAttribute("type"));
            if (cb.getAttribute("value").equalsIgnoreCase(option)) {
                cb.click();
                return cb.isSelected();
            }
        }
        return false;
    }

    public void switchWindowDemo(){
        int count = 0;
        while(count < 3) {
            this.switchWindowBtn.click();
            count ++;
        }
        String parentHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for(String handle : windowHandles){
            driver.switchTo().window(handle);
            System.out.println(driver.getTitle());
        }
        driver.switchTo().window(parentHandle);
        System.out.println(driver.getTitle());
        // driver.switchTo().newWindow(WindowType.TAB);
    }

    public void switchTabDemo(){
        int count = 0;
        while(count < 3) {
            this.switchTabBtn.click();
            count ++;
        }
        String parentHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for(String handle : windowHandles){
            driver.switchTo().window(handle);
            System.out.println(driver.getTitle());
        }
        driver.switchTo().window(parentHandle);
        System.out.println(driver.getTitle());

        // driver.switchTo().newWindow(WindowType.WINDOW);
    }

    public String handleAlert(String name, String option){
        String message = "";
        this.alertInput.sendKeys(name);
        if(option.equalsIgnoreCase("alert")) {
            this.alertBtn.click();
            message = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
        }else{
            this.alertConfirmBtn.click();
            message = driver.switchTo().alert().getText();
            driver.switchTo().alert().dismiss();
        }
        System.out.println(message);
        return message;
    }

    public int staticWebTableDemo(){
        List<WebElement> rows = this.staticWebTable.findElements(By.tagName("tr"));
        System.out.println(rows.size());
        int sum = 0;
        for(WebElement row : rows){
            getActions().moveToElement(row).perform();
            List<WebElement> al = row.findElements(By.tagName("td"));
            if(!al.isEmpty()) {
                System.out.println(al.get(al.size() - 1).getText());
                sum += Integer.parseInt(al.get(al.size() - 1).getText());
            }
        }
        System.out.println("Final sum of all the courses is : "+sum);
        return sum;
    }

    public boolean hiddenElementDemo(){
        this.hiddenElm.sendKeys("typing");
        this.hideElemBtn.click();
        this.showElemBtn.click();
        return this.showElemBtn.isDisplayed();
    }

    public int dynamicWebTableDemo(){
        getWebWait().until( d -> {
            List<WebElement> dynamicRows = this.dynamicTableElm.findElements(By.cssSelector("tr"));
            // getJsExecutor().executeScript("window.scrollBy(0, 10)");
            WebElement lastRow = dynamicRows.get(dynamicRows.size()-1);
            getJsExecutor().executeScript("arguments[0].scrollIntoView();", lastRow);
            return lastRow.isDisplayed();
        });
        List<WebElement> dynamicRows = this.dynamicTableElm.findElements(By.cssSelector("tr"));
        int sum = 0;
        for (WebElement row : dynamicRows){
            sum += Integer.parseInt(row.findElement(By.cssSelector("td:nth-child(4)")).getText());
            System.out.println(sum);
        }
        return sum;
    }

    public void mouseHoverDemo() {
        List<WebElement> hoverOptions = this.mouseHoverBtn.findElements(By.tagName("a"));
            getActions()
                    .moveToElement(this.mouseHoverBtn)
                    .moveByOffset(0, -10)
                    .moveToElement(hoverOptions.get(0))
                    .clickAndHold(hoverOptions.get(0))
                    .release()
                    .perform();
    }

    public void iFrameDemo(){
        getJsExecutor().executeScript("arguments[0].scrollIntoView();", this.mouseHoverBtn);
        driver.switchTo().frame(this.iFrameCourses);
        getActions().moveToElement(this.iniFrameLogoElm).perform();
        for(WebElement option : this.iniFrameHeaderOptionList){
            getActions().moveToElement(option).perform();
            System.out.println(option.getText() + " Staleness of element : " + ExpectedConditions.stalenessOf(option).apply(driver));
            if(option.getText().equalsIgnoreCase("Job Support")){
                option.click();
                break;
            }
        }
        getWebWait().until( d -> {
            return this.iniFrameLogoElm.isDisplayed();
        });
        driver.switchTo().defaultContent();
        getActions().moveToElement(this.headerElm).perform();
    }
}