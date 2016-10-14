package essentials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by thang on 10.10.2016.
 */
public class LoginPageObject extends PageObject {

    public LoginPageObject(WebDriver driver) {
        super(driver);
    }

    public void toStartingPage(){
        getDriver().get(getBaseUrl()+ "/login.xhtml");
        waitForPageToLoad();
    }

    @Override
    public boolean isOnPage(){
        return getDriver().getTitle().contains("Login");
    }

    public CreatingAccountObject clickCreateNewUser(){
        getDriver().findElement(By.id("loginForm:createBtn")).click();
        waitForPageToLoad();
        return new CreatingAccountObject(getDriver());
    }

    public HomePageObject clickLogin(String userName, String password){

        writeTextTo("loginForm:", "emailText", userName);
        writeTextTo("loginForm:", "passwordText", password);
        getDriver().findElement(By.id("loginForm:loginBtn")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        } else {
            return new HomePageObject(getDriver());
        }
    }

    public HomePageObject clickCancel(){
        getDriver().findElement(By.id("loginForm:cancelBtn")).click();
        waitForPageToLoad();
        return new HomePageObject(getDriver());
    }



}
