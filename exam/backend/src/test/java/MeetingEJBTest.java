import businesslayer.MeetingEJB;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by thang on 11.10.2016.
 */
@RunWith(Arquillian.class)
public class MeetingEJBTest {

    @Deployment
    public static JavaArchive createTestArchive() throws UnsupportedEncodingException {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage("businesslayer")
                .addPackage("datalayer")
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private MeetingEJB meetingEJB;

    @After
    @Before
    public void clear() {
        meetingEJB.deleteAllMeetings();
    }

    @Test
    public void testCreateSingleMeeting(){
        //Lets create meeting for the boss.
        assertNotNull(meetingEJB.createMeeting("Trimveien 8", "Norway", "Today", "Lyern52@gmail.com"));
        assertEquals(1, meetingEJB.getMeetingList().size());
        meetingEJB.deleteMeeting(meetingEJB.getMeetingList().get(0).getId());
        assertEquals(0, meetingEJB.getMeetingList().size());
    }


}