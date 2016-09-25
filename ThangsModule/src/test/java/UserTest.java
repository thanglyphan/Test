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

    private User getUserWithoutAddress(){
        User user = new User();
        user.setFirstname("Thang");
        user.setMiddlename("Ly");
        user.setLastname("Phan");
        user.setEmail("lyern52@gmail.com");
        return user;
    }

    private Address getValidAddress(){
        Address adr = new Address();
        adr.setCountry("Norway");
        adr.setCity("Oslo");
        adr.setGateAddress("Trimveien 8");
        adr.setPostCode(0372);
        return adr;
    }

    @Test
    public void testCreateUserAndFindTheUser(){
        //Make a user and add an address to it.
        User user = getUserWithoutAddress();

        Address adr = getValidAddress();
        adr.setUsers(new ArrayList<>());
        adr.getUsers().add(user);

        user.setAdr(new ArrayList<>());
        user.getAdr().add(adr);

        //Persist to database.
        assertTrue(persistInATransaction(user, adr));

        //Check if transaction for User and Address is OK.
        User found = em.find(User.class, user.getEmail());
        assertEquals(user.getEmail(), found.getEmail());
        assertTrue(found.getAdr().stream().anyMatch(a -> a.getGateAddress().equals(adr.getGateAddress())));

        //Check if Address is OK.
        Address adrFound = em.find(Address.class, adr.getGateAddress());
        assertEquals(adr.getGateAddress(), adrFound.getGateAddress());

        //Add the same address to another user.
        User userTwo = getUserWithoutAddress();
        userTwo.setEmail("test@test.no");
        userTwo.setAdr(new ArrayList<>());
        userTwo.getAdr().add(adrFound);
        adr.getUsers().add(userTwo);
        assertTrue(persistInATransaction(userTwo));

        //UserTwo lives in the same address as User.
        assertTrue(userTwo.getAdr().stream().anyMatch(a -> a.getGateAddress().equals(adr.getGateAddress())));


        //Check if Trimveien 8 have to users living there.
        assertEquals(user, adrFound.getUsers().get(0));
        assertEquals(userTwo.getAdr().get(0).getGateAddress(), adrFound.getGateAddress());

    }

}