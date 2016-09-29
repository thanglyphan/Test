import datalayer.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import static org.junit.Assert.*;

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
        String salt = getSalt();
        user.setSalt(salt);
        user.setHash(computeHash("Mazda323123", salt));
        user.setMiddlename("Ly");
        user.setLastname("Phan");
        user.setEmail("lyern52@gmail.com");
        user.setAddress(getValidAddress());


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

    @Test
    public void testConstraints(){
        User a = getValidUser();
        a.setEmail("thisDoesNoeWork");
        assertFalse(persistInATransaction(a));
    }


    @Test
    public void testDeleteUser(){
        //Creating a user.
        User user = getValidUser();

        assertTrue(persistInATransaction(user));
        User found = em.find(User.class, user.getEmail());
        assertEquals("Trimveien 8", found.getAddress().getGateAddress());
        em.remove(found);
        assertEquals(null, em.find(User.class, user.getEmail()));
    }

    @Test
    public void testDeletingAddressWhenDeletingUser(){
        User user = getValidUser();
        assertTrue(persistInATransaction(user));
        User found = em.find(User.class, user.getEmail());
        Address adrFound = em.find(Address.class, found.getAddress().getId());
        assertEquals("Trimveien 8", found.getAddress().getGateAddress());
        assertNotNull(adrFound);

        em.remove(found);

        Address adrFound2 = em.find(Address.class, found.getAddress().getId());
        assertNull(adrFound2);
    }

    @Test
    public void testCreateUserAndFindTheUser(){
        //Creating a user.
        User user = getValidUser();

        assertTrue(persistInATransaction(user));
        User found = em.find(User.class, user.getEmail());
        assertEquals("Trimveien 8", found.getAddress().getGateAddress());
    }

    protected String getSalt(){
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }

    protected String computeHash(String password, String salt){
        String combined = password + salt;
        String hash = DigestUtils.sha256Hex(combined);
        return hash;
    }
}