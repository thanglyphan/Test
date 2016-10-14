import businesslayer.MeetingEJB;
import businesslayer.UserEJB;
import datalayer.Address;
import datalayer.Meeting;
import datalayer.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static junit.framework.TestCase.*;

/**
 * Created by thang on 21.09.2016.
 */
@RunWith(Arquillian.class)
public class UserEJBTest {

    @Deployment
    public static JavaArchive createTestArchive() throws UnsupportedEncodingException {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage("businesslayer")
                .addPackage("datalayer")
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private UserEJB userEJB;

    @EJB
    private MeetingEJB meetingEJB;

    @After @Before
    public void clear(){
        userEJB.deleteAllUsers();
        meetingEJB.deleteAllMeetings();
    }

    public Address getValidAddress(){
        Address adr = new Address();
        adr.setCity("Oslo");
        adr.setCountry("Norway");
        adr.setPostCode(0372);
        adr.setGateAddress("Trimveien 8");
        return adr;
    }
    @Test
    public void testUserIsCreated(){
        Address adr = getValidAddress();

        assertTrue(userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr));
        assertFalse(userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr));

        assertEquals(1, userEJB.getUserList().size());
        assertEquals(1, userEJB.getAddressList().size());
    }


    @Test
    public void testUserAreAdmin(){
        Address adr = getValidAddress();
        Address adr2 = getValidAddress();

        assertTrue(userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr));
        assertTrue(userEJB.createUser("admin@admin.no", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr2));

        assertFalse(userEJB.getUser("Lyern52@gmail.com").isAdmin());
        assertTrue(userEJB.getUser("admin@admin.no").isAdmin());
        assertEquals(2, userEJB.getAddressList().size());
    }
    @Test
    public void testDeleteUser(){
        int expected = userEJB.getAddressList().size();
        assertEquals(0, expected);
        Address adr = getValidAddress();
        assertTrue(userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));
        assertEquals(1, userEJB.getAddressList().size());

        userEJB.deleteUser(userEJB.getUserList().get(0).getEmail());
        assertEquals(expected, userEJB.getAddressList().size());
    }

    @Test
    public void testFindUser(){
        assertFalse(userEJB.findUser("Lyern52@gmail.com"));

        Address adr = getValidAddress();
        assertTrue(userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        assertTrue(userEJB.findUser("Lyern52@gmail.com"));
    }

    @Test
    public void testFindSingleAddress(){
        Address adr = getValidAddress();
        assertTrue(userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        Address found = userEJB.getThisAddress(userEJB.getUserList().get(0).getAddress().getId());

        assertEquals(adr.getId(), found.getId());
    }

    @Test
    public void testCheckLogin(){
        Address adr = getValidAddress();
        assertTrue(userEJB.createUser("Lyern521@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        assertEquals("home", userEJB.checkLogin("Lyern521@gmail.com", "Mazda323123")); //Home means i got redirected, and success.

        assertEquals("login", userEJB.checkLogin("d", "d"));
    }
    @Test
    public void testDeleteAllUsers(){
        int expected = userEJB.getUserList().size();
        assertEquals(expected, userEJB.deleteAllUsers());
    }


    @Test
    public void testUserCreateMeeting(){
        //Regular user
        Address adr = getValidAddress();
        assertTrue(userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        //Lets create a meeting for above user.
        User found = userEJB.getUser("Lyern52@gmail.com");

        Meeting meeting = meetingEJB.createMeeting("My place", found.getAddress().getCountry(), "Today", found.getEmail());
        assertNotNull(meeting);

        //Lyern52@gmail.com is the creator here
        assertEquals(found.getEmail(), meetingEJB.getMeetingList().get(0).getCreator());

        //Lets check for meetings created by "Lyern52@gmail.com"
        assertEquals(1, meetingEJB.getMeetingListFromCreator(found.getEmail()).size());

        //Add the user into the meeting. First, get the meeting.
        userEJB.addMeeting(meeting, found.getEmail());
        assertTrue(meetingEJB.addUserToMeeting(meeting, found));

        //Lets check the meeting list for that created meeting
        Meeting foundMeeting = meetingEJB.getMeeting(meeting.getId());
        assertEquals(1, foundMeeting.getUsers().size());
    }

    @Test(expected = EJBException.class)
    public void testCreateUserWithWrongEmail(){
        Address adr = getValidAddress();
        userEJB.createUser("thisIsNotAnEmail", "Mazda323123", "Thang", "Ly", "Phan", adr);
    }

    @Test(expected = EJBException.class)
    public void testCreateUserWithWrongFirstname(){
        Address adr = getValidAddress();
        userEJB.createUser("Lyern52@gmail.com", "Mazda323123", " ", "Ly", "Phan", adr);
    }

    @Test(expected = EJBException.class)
    public void testCreateUserWithWrongLastname(){
        Address adr = getValidAddress();
        userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", " ", adr);
    }
    @Test(expected = EJBException.class)
    public void testCreateUserWithWrongMiddlename(){
        Address adr = getValidAddress();
        userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", " ", "Phan", adr);
    }
    @Test(expected = EJBException.class)
    public void testCreateUserWrongGateAddress(){
        Address adr = getValidAddress();
        adr.setGateAddress(" ");
        userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr);
    }
    @Test(expected = EJBException.class)
    public void testCreateUserNullPostCode(){
        Address adr = new Address();
        adr.setCity("A");
        adr.setGateAddress("B");
        adr.setCountry("Norway");
        userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr);
    }
    @Test(expected = EJBException.class)
    public void testCreateUserWithoutAddress(){
        userEJB.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", null);
    }
}
