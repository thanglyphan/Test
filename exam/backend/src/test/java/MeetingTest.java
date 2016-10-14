import datalayer.Meeting;
import datalayer.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by thang on 12.10.2016.
 */
public class MeetingTest {
    private EntityManagerFactory factory;
    private EntityManager em;

    @Before
    public void init() {
        factory = Persistence.createEntityManagerFactory("DB");
        em = factory.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
        factory.close();
    }


    private boolean persistInATransaction(Object... obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for(Object o : obj) {
                em.persist(o);
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println("FAILED TRANSACTION: " + e.toString());
            tx.rollback();
            return false;
        }
        return true;
    }

    private Meeting getValidMeeting(){
        Meeting m = new Meeting();
        m.setCountry("Norway");
        m.setLocation("Trimveien 8");
        m.setScheduledDate("Today");
        m.setCreator("Lyern52@gmail.com");
        m.setDate("lol");
        m.setUsers(new ArrayList<>());
        return m;
    }

    @Test
    public void testCreateUserAndFindTheUser(){
        //Creating a Meeting.
        Meeting meeting = getValidMeeting();

        assertTrue(persistInATransaction(meeting));
        Meeting found = em.find(Meeting.class, meeting.getId());
        assertEquals("Trimveien 8", found.getLocation());
    }

}
