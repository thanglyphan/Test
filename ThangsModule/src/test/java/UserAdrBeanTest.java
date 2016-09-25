import businesslayer.UserAdrBean;
import datalayer.Address;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by thang on 21.09.2016.
 */
@RunWith(Arquillian.class)
public class UserAdrBeanTest {

    @Deployment
    public static JavaArchive createTestArchive() throws UnsupportedEncodingException {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage("businesslayer")
                .addPackage("datalayer")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private UserAdrBean userAdrBean;

    @Test
    public void testUserIsCreated(){
        Address adr = new Address();
        adr.setCity("Oslo");
        adr.setCountry("Norway");
        adr.setPostCode(0372);
        adr.setGateAddress("Trimveien 8");
        /*
        assertTrue(userAdrBean.createUser("Lyern52@gmail.com", "Thang", "Ly", "Phan", adr));
        assertFalse(userAdrBean.createUser("Lyern52@gmail.com", "Thang", "Quoc Ly", "Phan", adr));

        assertEquals(1, userAdrBean.getUserList().size());
        assertEquals(1, userAdrBean.getAddressList().size());
        */
    }
}
