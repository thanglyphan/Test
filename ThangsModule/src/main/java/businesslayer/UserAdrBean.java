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
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thang on 21.09.2016.
 */
@Named
@Stateless
public class UserAdrBean implements Serializable{
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;



    @PersistenceContext(unitName = "MyDB")
    EntityManager em;

    public UserAdrBean(){}

    private void persistInATransaction(Object... obj) {
        for(Object o : obj) {
            em.persist(o);
        }
    }

    public boolean createUser(@NotNull String email, @NotNull String fName, @NotNull String lName, @NotNull String mName, @NotNull Address adr){
        if(findUser(email)) {
            return false;
        }else{
            User user = new User();
            user.setEmail(email);
            user.setFirstname(fName);
            user.setMiddlename(mName);
            user.setLastname(lName);

            user.setAdr(new ArrayList<>());
            user.getAdr().add(adr);

            adr.setUsers(new ArrayList<>());
            adr.getUsers().add(user);

            persistInATransaction(user, adr);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
