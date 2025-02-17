package com.sj.PageObjects;

import com.sj.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InternetHerokuApp extends BasePage {

    public InternetHerokuApp(WebDriver driver) {
        super(driver);
    }

    // @FindBy(css = "div#content li a")
    // List<WebElement> menuList;
    By menuList = By.cssSelector("div#content li a");

    By brokenImagesList = By.cssSelector("div[class*='example'] img");

    By challengingDomBtn1 = By.cssSelector("a[class='button']"); // suffixed a to fail the test intentionally

    By challengingDomBtn2 = By.cssSelector("a[class='button alert']");

    By challengingDomBtn3 = By.cssSelector("a[class='button success'");

    By challengingDomTableRowsElm = By.cssSelector("div.large-10 tbody tr");

    By challengingDomCanvasElm = By.cssSelector("canvas[id='canvas']");

    By dragAndDropSrcElm = By.cssSelector("div#column-a");  // suffixed a to fail the test intentionally

    By dragAndDropDestElm = By.cssSelector("div#column-b");

    By dynCtrlsAddRemoveResultElm = By.cssSelector("form[id='checkbox-example'] p"); // suffixed a to fail the test intentionally

    By dynCtrlsAddRemoveCB = By.cssSelector("form[id='checkbox-example'] input");

    By dynCtrlsAddRemoveBtn = By.cssSelector("form[id='checkbox-example'] button");

    By dynCtrlsEnableDisableIp = By.cssSelector("form[id='input-example'] input");

    By dynCtrlsEnableDisableBtn = By.cssSelector("form[id='input-example'] button");

    By dynCtrlsEnableDisableResultElm = By.cssSelector("form[id='input-example'] p");

    By DOMLoadingElm = By.cssSelector("div#loading");

    By dynLoadMenuList = By.cssSelector("div.example a");

    By dynLoadStartBtn = By.cssSelector("div#content div#start button");

    By dynLoadFinishElm = By.cssSelector("div#content div#finish");

    By fileDownLoadList = By.cssSelector("div.example a");

    By fileUploadChooseFileIp = By.cssSelector("input#file-upload");

    By fileUploadSubmitBtn = By.cssSelector("input#file-submit");

    By fileUploadSuccessElm = By.cssSelector("div.example");

    By horizantalScrollBtn = By.cssSelector("div.sliderContainer input");

    By horizantalScrollRangeElm = By.cssSelector("div.sliderContainer span");

    By hoverImageList = By.cssSelector("div.figure");

    By infiniteScrollList = By.cssSelector("div.jscroll-added");

    By infiniteScrollNextParentElm = By.cssSelector("div.jscroll-next-parent");

    By jQueryMenuList = By.cssSelector("ul#menu li a");

    By keyPressEventIp = By.cssSelector("div.example input");

    By keyPressEventResultElm = By.cssSelector("div.example p#result");

    By shadowDOMlist = By.cssSelector("div#content my-paragraph");

    By shiftingElmlist = By.cssSelector("div.example li");

    By formAuthUnameIp = By.cssSelector("input[name*='username']");

    By formAuthPwdIp = By.cssSelector("input[name*='password']");

    By formAuthSubmitBtn = By.cssSelector("button[type*='submit']");

    By formAuthResponseElm = By.cssSelector("div[data-alert][id='flash']");
    // You logged into a secure area!

    By commonFooterElm = By.cssSelector("div#page-footer");



    @Override
    public boolean isAt() {
        logger.info(" In isAt(), Thread -> {} & Driver -> {} ",Thread.currentThread().getId(), driver.toString());
        WebElement firstElmInMenu = driver.findElements(menuList).get(0);
        getWebWait().until(ExpectedConditions.visibilityOf(firstElmInMenu));
        return firstElmInMenu.isDisplayed();
    }

    public boolean selectFromMenu(String option){
        logger.info("Inside selectFromMenu -> Thread -> {} & Driver -> {} ",Thread.currentThread().getId(), driver.toString());
        getWebWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(menuList, 40));
        Boolean flag = isAt();
        if(flag) {
            List<WebElement> menuList = driver.findElements(this.menuList);
            for (WebElement elm : menuList) {
                System.out.println(elm.getText());
                if (elm.getText().equalsIgnoreCase(option)) {
                    System.out.println("The desired option is selected");
                    getActions().moveToElement(elm).perform();
                    elm.click();
                    getWebWait().until(ExpectedConditions.visibilityOfElementLocated(commonFooterElm));
                    break;
                }
            }
            return driver.findElement(commonFooterElm).isDisplayed();
        }
        return flag;
    }


    public List<Boolean> brokenImagesTest() throws IOException {
        System.out.println(" Inside brokenImagesTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        URL url;
        HttpURLConnection connection = null;
        List<WebElement> images = driver.findElements(brokenImagesList);
        List <Boolean> result = new ArrayList<>();
        for(WebElement image : images){
            url = new URL(image.getAttribute("src"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            System.out.println(image.getAttribute("src") + " --> " + connection.getResponseCode());
            if(connection.getResponseCode() > 299)
                result.add(false);
            else
                result.add(true);
        }
        return result;
    }

    public boolean challengingDomTest(){
        System.out.println(" Inside challengingDomTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        String canvasAnswerOld = this.challengingDomCanvasElementGetText();
        String canvasAnswerNew = "";
        int i = 0;
        Boolean flag = true;
        while(i < 3){
            int j = 0;
            driver.findElement(challengingDomBtn1).click();
            getWebWait().until(ExpectedConditions.visibilityOfElementLocated(challengingDomCanvasElm));
            canvasAnswerNew = challengingDomCanvasElementGetText();
            List<WebElement> rows = driver.findElements(challengingDomTableRowsElm);
                for (WebElement row : rows) {
                    row.findElements(By.tagName("a")).get((j%2)).click();
                    flag = (j%2 == 0) ? driver.getCurrentUrl().contains("edit") : driver.getCurrentUrl().contains("delete");
                    j++;
                }
                if(!canvasAnswerOld.equalsIgnoreCase(canvasAnswerNew) && !flag)
                    return false;
            i++;
        }
        return flag;
    }

    /*
    *Note:
    *   Canvas elm are not ordinary page elements, we can't use getText() to extract.
    *       <canvas id="canvas" width="599" height="200" style="border:1px dotted;"/>
    *   Instead we capture the <script> elm that renders the canvas element value and extract using getAttributes("InnerHTML")
    *       <script>
    *           var canvas_el = document.getElementById('canvas');
    *           var canvas = canvas_el.getContext('2d');
    *           canvas.font = '60px Arial';
    *           canvas.strokeText('Answer: 54396',90,112);
    *       </script>
    */
    public String challengingDomCanvasElementGetText(){
        System.out.println(" Inside challengingDomCanvasElementGetText -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        List<WebElement> scritps = driver.findElements(By.tagName("script"));
        String answer = "";
        for(WebElement script: scritps){
            String innerHTML = script.getAttribute("innerHTML");
            if(innerHTML.contains("canvas.strokeText")) {
                answer = innerHTML.substring(innerHTML.indexOf(":")+1, innerHTML.indexOf("',")).trim();
                break;
            }
        }
        return answer;
    }

    public boolean dragAndDropDemoTest(){
        System.out.println(" Inside dragAndDropDemoTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        WebElement source = driver.findElement(dragAndDropSrcElm);
        WebElement destination = driver.findElement(dragAndDropDestElm);
        System.out.println(source.getText() + " " + destination.getText());
        String before = source.getText();
        before += destination.getText();
        getActions()
                .dragAndDrop(source,destination)
                .perform();
        String after = source.getText();
        after += destination.getText();
        System.out.println(before + " " + after);
        if(before.equalsIgnoreCase(after))
            return false;
        return true;
    }

    public String dynamicControlsCBtest(String option) {
        System.out.println(" Inside dynamicControlsCBtest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        WebElement button = driver.findElement(dynCtrlsAddRemoveBtn);
        if (option.equalsIgnoreCase("Remove")){
            driver.findElement(dynCtrlsAddRemoveCB).click();
            button.click();
        }else if(option.equalsIgnoreCase("Add"))
            button.click();
        waitForDOMLoadingToComplete();
        return driver.findElement(dynCtrlsAddRemoveResultElm).getText();
    }

    public String dynamicControlsIPTest(Boolean enable){
        System.out.println(" Inside dynamicControlsIPTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        WebElement ip = driver.findElement(dynCtrlsEnableDisableIp);
        WebElement btn = driver.findElement(dynCtrlsEnableDisableBtn);
        if(enable){
            btn.click();
            waitForDOMLoadingToComplete();
        }else{
            ip.sendKeys("The ip field is enabled, Disabling it now");
            btn.click();
            waitForDOMLoadingToComplete();
        }
        return driver.findElement(dynCtrlsEnableDisableResultElm).getText();
    }

    public String dynamicLoadTest(String type) {
        System.out.println(" Inside dynamicLoadTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        List<WebElement> menuList = driver.findElements(dynLoadMenuList);
        if (type.equalsIgnoreCase("hiddenElement")){
            menuList.get(0).click();
            dynamicLoadNextPage();
            return dynamicLoadHiddenElementTest();
        }else if (type.equalsIgnoreCase("renderedElement")){
            menuList.get(menuList.size()-1).click();
            dynamicLoadNextPage();
            return dynamicLoadRenderedElementTest();
        }
        return "Failed";
    }

    public void dynamicLoadNextPage(){
        System.out.println(" Inside dynamicLoadNextPage -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        getWebWait().until(driver -> {
            String url = driver.getCurrentUrl();
            return Character.isDigit(url.charAt(url.length()-1));
        });
    }

    public String dynamicLoadHiddenElementTest(){
        System.out.println(" Inside dynamicLoadHiddenElementTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        driver.findElement(dynLoadStartBtn).click();
        waitForDOMLoadingToComplete();
        return driver.findElement(dynLoadFinishElm).getText();
    }

    public String dynamicLoadRenderedElementTest(){
        System.out.println(" Inside dynamicLoadRenderedElementTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        driver.findElement(dynLoadStartBtn).click();
        waitForDOMLoadingToComplete();
        return driver.findElement(dynLoadFinishElm).getText();
    }

    public String fileUploadTest(String filepath){
        System.out.println(" Inside fileUploadTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        driver.findElement(fileUploadChooseFileIp).sendKeys(filepath);
        driver.findElement(fileUploadSubmitBtn).click();
        getWebWait().until(ExpectedConditions.visibilityOfElementLocated(fileUploadSuccessElm));
        return driver.findElement(fileUploadSuccessElm).getText();
    }

    public String horizontalScrollingTest(String yCoordinates){
        System.out.println(" Inside horizontalScrollingTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        WebElement slider = driver.findElement(horizantalScrollBtn);
        getActions()
                .clickAndHold(slider)
                .moveByOffset( 0 , Integer.parseInt(yCoordinates))
                .release()
                .perform();
        return driver.findElement(horizantalScrollRangeElm).getText();
    }

    public boolean hoverImagesTest(){
        System.out.println(" Inside hoverImagesTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        List<WebElement> images = driver.findElements(hoverImageList);
        int i = 1;
        System.out.println(driver.getTitle());
        for(WebElement image:images){
            getActions()
                    .moveToElement(image)
                    .perform();
            image.findElement(By.tagName("a")).click();
            getWebWait().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            System.out.println(driver.getTitle());
            if(!driver.findElement(By.tagName("h1")).getText().equalsIgnoreCase("Not Found"))
                return false;
            i++;
            driver.navigate().back();
            getWebWait().until(ExpectedConditions.visibilityOfElementLocated(commonFooterElm));
        }
        return true;
    }

    public void infiniteScrollTest(Integer count){
        System.out.println(" Inside infiniteScrollTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        int i = 0;
        while(i < count){
            System.out.println("i="+i);
            List<WebElement> paras = driver.findElements(infiniteScrollList);
            System.out.println("paras size:"+paras.size());
            int oldSize = paras.size();
            getJsExecutor().executeScript("window.scrollBy(0,1000)");
            getActions().moveByOffset(0, 10).perform();
            getActions().moveToElement(driver.findElement(By.cssSelector("div#page-footer"))).perform();
            getWebWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(infiniteScrollList, oldSize));
            i++;
        }
        System.out.println(driver.findElements(infiniteScrollList).size());
    }

    public void jQueryMenuListTest(String option){
        System.out.println(" Inside jQueryMenuListTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        driver.navigate().refresh();
        List<WebElement> menuList = driver.findElements(jQueryMenuList);
        boolean flag = false;
        for(int i=0; i<menuList.size(); i++) {
            WebElement menu = menuList.get(i);
            if(!flag) {
                if (menu.isDisplayed() && menu.isEnabled() && menu.getText().contains("Enabled")) {
                    System.out.println(menu.getText());
                    getActions().moveToElement(menu).build().perform();
                    getWebWait().until(ExpectedConditions.visibilityOf(menuList.get(i+1)));
                    flag = true;
                }
            }else{
                if (menu.isDisplayed() && menu.isEnabled() && menu.getText().contains("Downloads")) {
                    System.out.println(menu.getText());
                    getActions().moveToElement(menu).perform();
                    getWebWait().until(ExpectedConditions.visibilityOf(menuList.get(i+1)));
                }else if(menu.getText().equalsIgnoreCase(option)) {
                    System.out.println(menu.getText());
                    getActions().moveToElement(menu).click().perform();
                    break;
                }
            }
        }
    }

    public boolean keyPressEventTest(String key){
        System.out.println(" Inside keyPressEventTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        WebElement ip = driver.findElement(keyPressEventIp);
        ip.click();
        getActions().keyDown(Keys.CONTROL)
                    .sendKeys(key)
                    .keyUp(Keys.CONTROL)
                    .build()
                    .perform();
        return driver.findElement(keyPressEventResultElm).getText().split(" ")[2].equalsIgnoreCase(key.toUpperCase());
    }

    /*
    *   Note:
    *       ShadowDOM's are typically hidden inside an element, So we capture one element up:
    *           <elm> --------------> We capture this element and extract the shadowDOM out of it using getShadowRoot()
    *               <shadow-DOM rootNode>
    *           </elm>
    */
    public void shadowDOMtest(){
        System.out.println(" Inside shadowDOMtest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        // These elements are one level up to the shadowDOM root elements
        List<WebElement> elms = driver.findElements(shadowDOMlist);
        for(WebElement elm : elms){
            SearchContext shadowDOMrootNode = elm.getShadowRoot();
            System.out.println(shadowDOMrootNode.findElement(By.cssSelector("slot[name='my-text']")).getText());
        }
    }

    public String formAuthenticationTest(String uname, String pwd){
        System.out.println(" Inside formAuthenticationTest -> Thread : " + Thread.currentThread().getId() +" & Driver : " + driver.toString());
        WebElement unameElm = driver.findElement(formAuthUnameIp);
        WebElement pwdElm = driver.findElement(formAuthPwdIp);
        WebElement submitBtn = driver.findElement(formAuthSubmitBtn);
        List<WebElement> elmList = new ArrayList<>();
        elmList.add(unameElm);
        elmList.add(pwdElm);
        elmList.add(submitBtn);
        getWebWait().until(ExpectedConditions.visibilityOfAllElements(elmList));
        unameElm.sendKeys(uname);
        pwdElm.sendKeys(pwd);
        submitBtn.click();
        getWebWait().until(driver -> {
            return  driver.findElement(formAuthResponseElm).isDisplayed();
        });
        return driver.findElement(formAuthResponseElm).getText().trim();
    }

    public void waitForDOMLoadingToComplete(){
        getWebWait().until(driver -> {
            return !driver.findElement(DOMLoadingElm).isDisplayed();
        });
    }


/*
    getJsExecutor().executeScript("alert('Welcome To SoftwareTestingMaterial');");
    getJsExecutor().executeAsyncScript("window.scrollBy(0, document.body.scrollHeight)");
    getJsExecutor().executeScript("arguments[0].click();" , nextPage);
    WebElement hiddenNextParentElm = driver.findElement(infiniteScrollNextParentElm);
    getJsExecutor().executeScript("arguments[0].style.display='block';", hiddenNextParentElm);
    getWebWait().until(ExpectedConditions.visibilityOf(paras.get(paras.size()-1)));
    getJsExecutor().executeScript("window.scrollBy(0,1000)");
    getWebWait().until(driver -> {
        WebElement hiddenNextParentElm = driver.findElement(infiniteScrollNextParentElm);
        getJsExecutor().executeScript("arguments[0].style.display='block';", hiddenNextParentElm);
        return driver.findElement(infiniteScrollNextParentElm).isDisplayed();
        return hiddenNextParentElm.isDisplayed();
    });
*/
}