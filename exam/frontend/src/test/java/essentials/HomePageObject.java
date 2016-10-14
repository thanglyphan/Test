package essentials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by thang on 12.10.2016.
 */
public class HomePageObject extends PageObject {

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    public void toStartingPage(){
        getDriver().get(getBaseUrl()+ "/home.xhtml");
        waitForPageToLoad();
    }

    @Override
    public boolean isOnPage(){
        return getDriver().getTitle().contains("Welcome");
    }


    public LoginPageObject toLogin() {
        if (isLoggedIn()) {
            logout();
        }

        getDriver().findElement(By.id("loginButton")).click();
        waitForPageToLoad();
        return new LoginPageObject(getDriver());
    }

    public StatisticsPageObject clickStats(){
        if(isLoggedIn()){
            getDriver().findElement(By.id("statsPage")).click();
            return new StatisticsPageObject(getDriver());
        }
        return null;
    }

    public MeetingPageObject clickCreateMeeting(){
        if(isLoggedIn()){
            getDriver().findElement(By.id("meetingForm:createMeetingBtn")).click();
            return new MeetingPageObject(getDriver());
        }
        return null;
    }

}

