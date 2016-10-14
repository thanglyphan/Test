package essentials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by thang on 24.09.2016.
 */
public class CreatingAccountObject extends PageObject {

    public CreatingAccountObject(WebDriver driver) {
        super(driver);
    }

    public void toStartingPage(){
        getDriver().get(getBaseUrl()+ "/register.xhtml");
        waitForPageToLoad();
    }

    @Override
    public boolean isOnPage(){
        return getDriver().getTitle().contains("Register");
    }

    /*
    public void writeTextTo(String field, String theInput){
        WebElement text = getDriver().findElement(By.id("createUserForm:" + field));
        text.sendKeys(theInput);
        waitForPageToLoad();
    }
    */

    public HomePageObject createUser(String email, String password, String confirmPassword,
                                     String firstName, String middleName, String lastName, String gateAdr, String postCode, String city, String country){

        writeTextTo("createUserForm:", "emailText", email);
        writeTextTo("createUserForm:", "passwordText", password);
        writeTextTo("createUserForm:", "confirmPasswordText", confirmPassword);
        writeTextTo("createUserForm:", "firstNameText", firstName);
        writeTextTo("createUserForm:", "middleNameText", middleName);
        writeTextTo("createUserForm:", "lastNameText", lastName);
        writeTextTo("createUserForm:", "gateAdrText", gateAdr);
        writeTextTo("createUserForm:", "postCodeText", postCode);
        writeTextTo("createUserForm:", "cityText", city);

        try {
            new Select(getDriver().findElement(By.id("createUserForm:countryText"))).selectByVisibleText(country);
        } catch (Exception e){
            return null;
        }

        getDriver().findElement(By.id("createUserForm:createUserBtn")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        } else {
            return new HomePageObject(getDriver());
        }
    }


}
