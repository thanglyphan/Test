package essentials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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

    public void writeTextTo(String field, String theInput){
        WebElement text = getDriver().findElement(By.id("form:" + field));
        text.sendKeys(theInput);
        waitForPageToLoad();
    }

    public void clickCreate(){
        WebElement button = getDriver().findElement(By.id("form:createUserBtn"));
        button.click();
        waitForPageToLoad();
    }

    public int getNumberOfUsers(){
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='userTable']//tbody//tr[string-length(text()) > 0]"));
        return elements.size();
    }

    public void deleteUser(int position){
        String htmlPos = "" + (position + 1);// XPath starts from 1 and not 0

        WebElement button = getDriver().findElement(
                By.xpath("//table[@id='userTable']//tbody//tr["+htmlPos+"]/td[3]/form/input[@value = 'Delete']"));
        button.click();

        waitForPageToLoad();
    }

}
