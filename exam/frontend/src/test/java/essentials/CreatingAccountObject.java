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
        WebElement text = getDriver().findElement(By.id("createUserForm:" + field));
        text.sendKeys(theInput);
        waitForPageToLoad();
    }

    public StatisticsPageObject clickCreate(){
        WebElement button = getDriver().findElement(By.id("createUserForm:createUserBtn"));
        button.click();
        waitForPageToLoad();

        StatisticsPageObject po = new StatisticsPageObject(getDriver());

        return po;
    }

}
