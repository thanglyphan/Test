import datalayer.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by thang on 20.09.2016.
 */
public class UserTest {

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

    private User getValidUser(){
        User user = new User();
        user.setFirstname("Thang");
        user.setMiddlename("Ly");
        user.setPassword("Mazda323123");
        user.setLastname("Phan");
        user.setEmail("lyern52@gmail.com");
        user.setAdr(getValidAddress());
        return user;
    }

    private Address getValidAddress(){
        Address adr = new Address();
        adr.setCountry("Norway");
        adr.setCity("Oslo");
        adr.setGateAddress("Trimveien 8");
        adr.setPostCode(0372);
        persistInATransaction(adr);
        return adr;
    }

    //@Test
    public void testDeleteUser(){


    }

    @Test
    public void testCreateUserAndFindTheUser(){
        //Creating a user.
        User user = getValidUser();

        assertTrue(persistInATransaction(user));
        User found = em.find(User.class, user.getEmail());
        assertEquals("Trimveien 8", found.getAdr().getGateAddress());


    }

}