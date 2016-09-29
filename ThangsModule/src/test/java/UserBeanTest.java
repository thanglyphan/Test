import businesslayer.UserBean;
import datalayer.Address;
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
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

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

    @After @Before
    public void clear(){
        userBean.deleteAllUsers();
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
    public void testDeleteUser(){
        int expected = userBean.getAddressList().size();
        assertEquals(0, expected);
        Address adr = getValidAddress();
        assertTrue(userBean.createUser("Lyern52@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));

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
        //Not done
        Address adr = getValidAddress();
        assertTrue(userBean.createUser("Lyern521@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));
        assertFalse(userBean.createUser("Lyern521@gmail.com", "Mazda323123", "Thang", "Ly", "Phan", adr));



        List<User> users = userBean.getUserList();
        User one = users.get(0);

        assertEquals("overview", userBean.checkLogin("Lyern521@gmail.com", "Mazda323123"));
    }
}
