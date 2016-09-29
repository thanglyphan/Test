import businesslayer.UserBean;
import essentials.SeleniumTestBase;
import essentials.CreatingAccountObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ejb.EJB;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by thang on 24.09.2016.
 */
public class CreatingAccIT extends SeleniumTestBase {
    private CreatingAccountObject accountObject;

    @Before
    public void startFromInitialPage() {

        accountObject = new CreatingAccountObject(getDriver());
        accountObject.toStartingPage();
        assertTrue(accountObject.isOnPage());
    }
    @After
    public void deleteUser(){
        accountObject.deleteUser(0);
    }

    @Test
    public void testCreateUser() {
        assertEquals(0, accountObject.getNumberOfUsers());
        accountObject.toStartingPage();
        accountObject.writeTextTo("emailText", "Lyern52@gmail.com");
        accountObject.writeTextTo("passwordText", "Mazda323123");
        accountObject.writeTextTo("firstNameText", "Thang");
        accountObject.writeTextTo("middleNameText", "Quoc Ly");
        accountObject.writeTextTo("lastNameText", "Phan");
        accountObject.writeTextTo("gateAdrText", "Trimveien 8");
        accountObject.writeTextTo("postCodeText", "0372");
        accountObject.writeTextTo("countryText", "Norway");
        accountObject.writeTextTo("cityText", "Oslo");

        accountObject.clickCreate();
        //One user has been created.
        assertEquals(1, accountObject.getNumberOfUsers());
    }

}
