package businesslayer;

import datalayer.Address;
import datalayer.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thang on 21.09.2016.
 */
@Stateless
public class UserBean implements Serializable{

    @PersistenceContext(unitName = "MyDB")
    EntityManager em;

    public UserBean(){}

    private void persistInATransaction(Object... obj) {
        for(Object o : obj) {
            em.persist(o);
        }
    }

    public boolean createUser(@NotNull String email, @NotNull String password, @NotNull String fName, @NotNull String lName, @NotNull String mName, @NotNull Address adr){
        if(findUser(email)) {
            return false;
        }else{
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstname(fName);
            user.setMiddlename(mName);
            user.setLastname(lName);
            user.setAdr(adr);
            adr.setUsers(user);

            persistInATransaction(adr, user);
            return true;
        }
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

    public void deleteAllUsers(){
        for(User a: getUserList()){
            deleteUser(a.getEmail());
        }
    }

    public String checkLogin(String email, String password) {
        User found = em.find(User.class, email);
        if(found.getPassword().equals(password)){
            return "overview";
        }
        return "login";
    }
}
