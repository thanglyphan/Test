package essentials;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.List;

/**
 * Created by thang on 24.09.2016.
 */
public abstract class PageObject {

    private final WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
    }

    /*
        Is the browser currently on the page represented by this Page Object?
        We need a method to check if we are on the right page, eg to check we were
        not redirected to an error message page, or if we followed the wrong link
     */
    public abstract boolean isOnPage();

    protected WebDriver getDriver(){
        return driver;
    }

    protected String getBaseUrl(){
        return "http://localhost:8080/exam";
    }

    public void writeTextTo(String theForm, String field, String theInput){
        WebElement text = getDriver().findElement(By.id(theForm + field));
        text.clear();
        text.sendKeys(theInput);
    }


    public boolean isLoggedIn(){
        List<WebElement> logout = driver.findElements(By.id("logoutForm:logoutButton"));
        return !logout.isEmpty();
    }

    public void logout(){

        List<WebElement> logout = driver.findElements(By.id("logoutForm:logoutButton"));
        if(! logout.isEmpty()){
            logout.get(0).click();
            waitForPageToLoad();
        }
    }

    protected Boolean waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 10); //give up after 10 seconds

        //keep executing the given JS till it returns "true", when page is fully loaded and ready
        return wait.until((ExpectedCondition<Boolean>) input -> (
                (JavascriptExecutor) input).executeScript("return document.readyState").equals("complete"));
    }
}