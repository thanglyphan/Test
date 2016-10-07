import businesslayer.MeetingBean;
import businesslayer.UserBean;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.*;

/**
 * Created by thang on 21.09.2016.
 */
@RunWith(Arquillian.class)
public class UserBeanTest {

    @Deployment
    public static JavaArchive createTestArchive() throws UnsupportedEncodingException {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage("businesslayer")
                .addPackage("datalayer")
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private UserBean userBean;


    @EJB
    private MeetingBean meetingBean;

    @After @Before
    public void clear(){
        userBean.deleteAllUsers();
        meetingBean.deleteAllMeetings();
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

        assertTrue(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr));
        assertFalse(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr));

        assertEquals(1, userBean.getUserList().size());
        assertEquals(1, userBean.getAddressList().size());
    }


    @Test
    public void testUserAreAdmin(){
        Address adr = getValidAddress();
        Address adr2 = getValidAddress();

        assertTrue(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr));
        assertTrue(userBean.createUser("admin@admin.no", "Mazda323123", "Thang", "Quoc Ly", "Phan", adr2));

        assertFalse(userBean.getUser("Lyern52@gmail.com").isAdmin());
        assertTrue(userBean.getUser("admin@admin.no").isAdmin());
        assertEquals(2, userBean.getAddressList().size());
    }
    @Test
    public void testDeleteUser(){
        int expected = userBean.getAddressList().size();
        assertEquals(0, expected);
        Address adr = getValidAddress();
        assertTrue(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));
        assertEquals(1, userBean.getAddressList().size());

        userBean.deleteUser(userBean.getUserList().get(0).getEmail());
        assertEquals(expected, userBean.getAddressList().size());
    }

    @Test
    public void testFindUser(){
        assertFalse(userBean.findUser("Lyern52@gmail.com"));

        Address adr = getValidAddress();
        assertTrue(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        assertTrue(userBean.findUser("Lyern52@gmail.com"));
    }

    @Test
    public void testFindSingleAddress(){
        Address adr = getValidAddress();
        assertTrue(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        Address found = userBean.getThisAddress(userBean.getUserList().get(0).getAddress().getId());

        assertEquals(adr.getId(), found.getId());
    }

    @Test
    public void testCheckLogin(){
        Address adr = getValidAddress();
        assertTrue(userBean.createUser("Lyern521@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        assertEquals("overview", userBean.checkLogin("Lyern521@gmail.com", "Mazda323123"));

        assertEquals("login", userBean.checkLogin("d", "d"));
    }
    @Test
    public void testDeleteAllUsers(){
        int expected = userBean.getUserList().size();
        assertEquals(expected, userBean.deleteAllUsers());
    }


    @Test
    public void testCreateSingleMeeting(){
        //Lets create meeting for the boss.
        assertTrue(meetingBean.createMeeting("My place", new Date()));
        assertEquals(1, meetingBean.getMeetingList().size());
        meetingBean.deleteMeeting(meetingBean.getMeetingList().get(0).getId());
        assertEquals(0, meetingBean.getMeetingList().size());
    }

    @Test
    public void testUserGoToMeeting(){
        //Regular user
        Address adr = getValidAddress();
        assertTrue(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

        //This user above goes attends to meeting
        assertTrue(meetingBean.createMeeting("My place", new Date()));

        Meeting one = meetingBean.getMeetingList().get(0);
        User found = userBean.getUser("Lyern52@gmail.com");
        assertTrue(userBean.addMeeting(one, found.getEmail()));

        assertEquals(1, userBean.getUser("Lyern52@gmail.com").getMeetings().size());
        assertEquals("My place", userBean.getUser("Lyern52@gmail.com").getMeetings().get(0).getPlace());
        assertEquals("Lyern52@gmail.com", meetingBean.getMeetingList().get(0).getUsers().get(0).getEmail());

    }

}
