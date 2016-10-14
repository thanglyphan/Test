package essentials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by thang on 06.10.2016.
 */
public class StatisticsPageObject extends PageObject {

    public StatisticsPageObject(WebDriver driver) {
        super(driver);
    }

    public void toStartingPage(){
        getDriver().get(getBaseUrl() + "/overview.xhtml");
        waitForPageToLoad();
    }

    @Override
    public boolean isOnPage(){
        return getDriver().getTitle().contains("Statistics");
    }



    public int getNumberOfUsers(){
        List<WebElement> elements = getDriver().findElements(By.xpath("//table[@id='userTable']//tbody//tr[string-length(text()) > 0]"));
        return elements.size();
    }

    public void deleteUser(int position){
        String htmlPos = "" + (position + 1);// XPath starts from 1 and not 0

        WebElement button = getDriver().findElement(By.xpath("//table[@id='userTable']//tbody//tr["+htmlPos+"]/td[7]/form/input[@value = 'Delete']"));
        button.click();

        waitForPageToLoad();
    }
}
