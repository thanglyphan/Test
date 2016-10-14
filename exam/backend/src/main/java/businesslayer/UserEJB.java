package businesslayer;

import datalayer.Address;
import datalayer.Meeting;
import datalayer.User;
import org.apache.commons.codec.digest.DigestUtils;
import validation.NotEmpty;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thang on 21.09.2016.
 */
@Stateless
public class UserEJB implements Serializable{

    @PersistenceContext(unitName = "MyDB")
    EntityManager em;

    public UserEJB(){}

    private void persistInATransaction(Object... obj) {
        for(Object o : obj) {
            em.persist(o);
        }
    }

    public boolean createUser(@NotEmpty String email, @NotEmpty String password, @NotEmpty String fName, @NotEmpty String lName, @NotEmpty String mName, @NotNull Address adr){
        if(findUser(email)) {
            return false;
        }else{
            User user = new User();
            user.setEmail(email);
            String salt = getSalt();
            user.setSalt(salt);
            String hash = computeHash(password, salt);
            user.setHash(hash);
            user.setFirstname(fName);
            user.setMiddlename(mName);
            user.setLastname(lName);
            user.setAddress(adr);
            if(user.getEmail().equals("admin@admin.no")){
                user.setAdmin(true);
            }else{
                user.setAdmin(false);
            }
            adr.setUsers(user);
            user.setMeetings(new ArrayList<>());
            persistInATransaction(adr, user);
            return true;
        }
    }

    public boolean checkAdmin(String email){
        return getUser(email).isAdmin();
    }

    public User getUser(String email){
        return em.find(User.class, email);
    }

    public boolean findUser(String email){
        return em.find(User.class, email) != null;
    }

    public List<User> getUserList(){
        return em.createNamedQuery(User.FIND_ALL).getResultList();
    }

    public List<Address> getAddressList(){
        return em.createNamedQuery(Address.FIND_ALL).getResultList();
    }

    public Address getThisAddress(Long id){
        Query q = em.createNamedQuery(Address.FIND_THIS_ADDRESS);
        q.setParameter(1, id);
        return (Address) q.getSingleResult();
    }

    public void deleteUser(String email){
        User user = em.find(User.class, email);
        em.remove(user);
    }

    public int deleteAllUsers(){
        int i = 0;
        for(User a: getUserList()){
            i++;
            deleteUser(a.getEmail());
        }
        return i;
    }

    public String checkLogin(String email, String password) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return "login";
        }
        User found = em.find(User.class, email);
        if(found == null){
            computeHash(password, getSalt());
            return "login";
        }

        String hash = computeHash(password, found.getSalt());

        if(hash.equals(found.getHash())){
            return "home";
        }
        return "login";
    }

    public boolean addMeeting(Meeting meeting, String email){

        User user = em.find(User.class, email);
        int size = user.getMeetings().size();

        user.getMeetings().add(meeting);
        if(user.getMeetings().size() > size){
            return true;
        }
        return false;
    }

    public void deleteMeetingFromAllUsers(Meeting meeting){
        List<User> list = getUserList();

        System.out.println("FUCKED");
        for(User a: list){
            if(a.getMeetings().size() > 0){
                System.out.println("Meeting to be removed: Country: " + meeting.getCountry() + " Loc: " + meeting.getLocation());
                for(Meeting b: a.getMeetings()){
                    System.out.println("User have these meetings registered: Country: " + b.getCountry() + " Loc: " + b.getLocation());
                }
            }else{
               System.out.println("List of meetings are not larger than zero");
            }
        }
    }

    @NotNull
    protected String getSalt(){
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }

    @NotNull
    protected String computeHash(String password, String salt){
        String combined = password + salt;
        return DigestUtils.sha256Hex(combined);
    }
}
