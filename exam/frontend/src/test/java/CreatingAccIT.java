import essentials.CreatingAccountObject;
import essentials.SeleniumTestBase;
import essentials.StatisticsPageObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by thang on 24.09.2016.
 */
public class CreatingAccIT extends SeleniumTestBase {
    private CreatingAccountObject accountObject;
    private StatisticsPageObject statisticsObject;

    @Before
    public void startFromInitialPage() {
        accountObject = new CreatingAccountObject(getDriver());
        accountObject.toStartingPage();
        assertTrue(accountObject.isOnPage());
    }

    @Test
    public void testCreateUser() {
        accountObject.toStartingPage();
        accountObject.writeTextTo("emailText", "admin@admin.no");
        accountObject.writeTextTo("passwordText", "Mazda323123");
        accountObject.writeTextTo("firstNameText", "Thang");
        accountObject.writeTextTo("middleNameText", "Quoc Ly");
        accountObject.writeTextTo("lastNameText", "Phan");
        accountObject.writeTextTo("gateAdrText", "Trimveien 8");
        accountObject.writeTextTo("postCodeText", "0372");
        accountObject.writeTextTo("countryText", "Norway");
        accountObject.writeTextTo("cityText", "Oslo");

        statisticsObject = accountObject.clickCreate();
        //One user has been created.
        statisticsObject.toStartingPage();
        assertEquals(1, statisticsObject.getNumberOfUsers());
        statisticsObject.deleteUser(0);
        assertEquals(0, statisticsObject.getNumberOfUsers());

    }

}
