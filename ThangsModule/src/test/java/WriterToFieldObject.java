import org.openqa.selenium.WebDriver;

/**
 * Created by thang on 24.09.2016.
 */
public class WriterToFieldObject extends PageObject{

    public WriterToFieldObject(WebDriver driver) {
        super(driver);
    }

    public void toStartingPage(){
        getDriver().get(getBaseUrl()+"/thang");
        waitForPageToLoad();
    }


    @Override
    public boolean isOnPage(){
        return getDriver().getTitle().contains(" ");
    }

    /*
        When we can modify the HTML directly (eg access to source code), then
        the easiest approach for XPath is to give ids to each element we want
        to use.
        Note: some IDs might get modified by JSF, eg for forms, it concatenates
        the id of the form to the ids of the buttons / inside elements
     */



}
