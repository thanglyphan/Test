import essentials.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 * Created by thang on 24.09.2016.
 */
public class WebPageIT extends SeleniumTestBase {


    @Before
    public void startFromInitialPage() {
        assumeTrue(JBossUtil.isJBossUpAndRunning());

        homePageObject = new HomePageObject(getDriver());
        homePageObject.toStartingPage();

        assertTrue(homePageObject.isOnPage());
    }

    @Test
    public void testCreateValidUser() {
        LoginPageObject loginPageObject = homePageObject.toLogin();
        CreatingAccountObject accountObject = loginPageObject.clickCreateNewUser();

        homePageObject = accountObject.createUser("admin@admin.no", "123", "123", "Thang", "Quoc Ly", "Phan", "Trimveien 8", "0372", "Oslo", "Norway");
        assertNotNull(homePageObject);
        assertTrue(homePageObject.isOnPage());
        //One user has been created. Lets delete that user.
        StatisticsPageObject statisticsPageObject = homePageObject.clickStats();

        statisticsPageObject.toStartingPage();
        assertEquals(1, statisticsPageObject.getNumberOfUsers());
        statisticsPageObject.deleteUser(0);
        assertEquals(0, statisticsPageObject.getNumberOfUsers());

    }


    @Test
    public void testCreateUserFailDueToPasswordMismatch(){

        LoginPageObject loginPageObject = homePageObject.toLogin();
        CreatingAccountObject accountObject = loginPageObject.clickCreateNewUser();

        assertTrue(accountObject.isOnPage());

        HomePageObject home = accountObject.createUser("admin@admin.no", "123", "321", "Thang", "Quoc Ly", "Phan", "Trimveien 8", "0372", "Oslo", "Norway");

        //This didnt get created.
        assertNull(home);
        assertTrue(accountObject.isOnPage());

    }


    @Test
    public void testLoginLink(){

        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

    }

    @Test
    public void testLoginWrongUser(){

        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

        loginPageObject.writeTextTo("loginForm:", "emailText", "admin@admin.com");
        loginPageObject.writeTextTo("loginForm:", "passwordText", "123");
        //Try to login with non-existing user.
        assertTrue(loginPageObject.isOnPage());

    }

    @Test
    public void testValidLogin(){
        //Create user and redirect to "Home"
        LoginPageObject loginPageObject = homePageObject.toLogin();
        CreatingAccountObject accountObject = loginPageObject.clickCreateNewUser();

        homePageObject = accountObject.createUser("admin@admin.no", "123", "123", "Thang", "Quoc Ly", "Phan", "Trimveien 8", "0372", "Oslo", "Norway");
        assertNotNull(homePageObject);
        assertTrue(homePageObject.isOnPage());

        //Now in home, lets logout this user.
        homePageObject.logout();

        //Confirming logout.
        assertFalse(homePageObject.isLoggedIn());

        //Lets login. Testing if the user is logged in.
        loginPageObject = homePageObject.toLogin();
        homePageObject = loginPageObject.clickLogin("admin@admin.no", "123");
        assertTrue(homePageObject.isLoggedIn());
        assertTrue(homePageObject.isOnPage());

        //Delete the user.
        StatisticsPageObject statisticsPageObject = homePageObject.clickStats();
        statisticsPageObject.toStartingPage();
        assertEquals(1, statisticsPageObject.getNumberOfUsers());
        statisticsPageObject.deleteUser(0);
        assertEquals(0, statisticsPageObject.getNumberOfUsers());

    }
    @Test
    public void testClickCancel(){
        LoginPageObject loginPageObject = homePageObject.toLogin();
        assertTrue(loginPageObject.isOnPage());

        homePageObject = loginPageObject.clickCancel();
        assertFalse(loginPageObject.isOnPage());
        assertTrue(homePageObject.isOnPage());
    }


    @Test
    public void testGoToCreateMeetingPage(){
        //Create user and redirect to "Home"
        LoginPageObject loginPageObject = homePageObject.toLogin();
        CreatingAccountObject accountObject = loginPageObject.clickCreateNewUser();
        assertTrue(accountObject.isOnPage());

        homePageObject = accountObject.createUser("admin@admin.no", "123", "123", "Thang", "Quoc Ly", "Phan", "Trimveien 8", "0372", "Oslo", "Norway");
        assertNotNull(homePageObject);
        assertTrue(homePageObject.isOnPage());

        //Go to create meeting
        MeetingPageObject meetingPageObject = homePageObject.clickCreateMeeting();
        meetingPageObject.toStartingPage();
        assertTrue(meetingPageObject.isOnPage());
        //Lets go back
        homePageObject = meetingPageObject.clickCancel();
        //Lets go to stats and delete user.
        StatisticsPageObject statisticsPageObject = homePageObject.clickStats();
        statisticsPageObject.toStartingPage();
        assertEquals(1, statisticsPageObject.getNumberOfUsers());
        statisticsPageObject.deleteUser(0);
        assertEquals(0, statisticsPageObject.getNumberOfUsers());
    }

    @Test
    public void testCreateAMeetingAndVerify(){
        //Create user and redirect to "Home"
        LoginPageObject loginPageObject = homePageObject.toLogin();
        CreatingAccountObject accountObject = loginPageObject.clickCreateNewUser();
        assertTrue(accountObject.isOnPage());

        homePageObject = accountObject.createUser("admin@admin.no", "123", "123", "Thang", "Quoc Ly", "Phan", "Trimveien 8", "0372", "Oslo", "Norway");
        assertNotNull(homePageObject);
        assertTrue(homePageObject.isOnPage());

        //Go to create meeting
        MeetingPageObject meetingPageObject = homePageObject.clickCreateMeeting();
        meetingPageObject.toStartingPage();
        assertTrue(meetingPageObject.isOnPage());

        homePageObject = meetingPageObject.createMeeting("Norway", "Trimveien 8", "Today");
        assertNotNull(homePageObject);
        homePageObject.toStartingPage();
        assertTrue(homePageObject.isOnPage());

        //Lets go to stats and delete user.
        StatisticsPageObject statisticsPageObject = homePageObject.clickStats();
        statisticsPageObject.toStartingPage();
        assertEquals(1, statisticsPageObject.getNumberOfUsers());
        statisticsPageObject.deleteUser(0);
        assertEquals(0, statisticsPageObject.getNumberOfUsers());


    }


}
