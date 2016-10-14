package essentials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;


/**
 * Created by thang on 12.10.2016.
 */
public class MeetingPageObject extends PageObject {

    public MeetingPageObject(WebDriver driver) {
        super(driver);
    }

    public void toStartingPage(){
        getDriver().get(getBaseUrl() + "/createmeeting.xhtml");
        waitForPageToLoad();
    }

    @Override
    public boolean isOnPage(){
        return getDriver().getTitle().contains("meeting");
    }

    public HomePageObject clickCancel(){
        getDriver().findElement(By.id("createMeetingForm:cancelBtn")).click();
        return new HomePageObject(getDriver());
    }

    public HomePageObject createMeeting(String country, String location, String date){
        try {
            new Select(getDriver().findElement(By.id("createMeetingForm:countryText"))).selectByVisibleText(country);
        } catch (Exception e){
            return null;
        }

        writeTextTo("createMeetingForm:", "locationText", location);
        writeTextTo("createMeetingForm:", "scheduledDateText", date);


        getDriver().findElement(By.id("createMeetingForm:createBtn")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        } else {
            return new HomePageObject(getDriver());
        }
    }
}
