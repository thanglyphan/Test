package datalayer;



import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Created by thang on 20.09.2016.
 */
@Entity
@NamedQueries(value = {
        @NamedQuery(name = User.FIND_ALL, query = "SELECT a FROM User a"),
        @NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT a FROM User a WHERE a.email = ?1"),
        @NamedQuery(name = User.DELETE_ALL_USERS, query = "DELETE FROM User a")
})
public class User {

    /*-------------------------------QUERIES-------------------------------*/
    public static final String FIND_ALL = "User.find_all";
    public static final String FIND_BY_EMAIL = "User.find_by_email";
    public static final String DELETE_ALL_USERS = "User.delete_all_users";
    /*-------------------------------FIELDS-------------------------------*/
    @Id
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull
    private String hash;

    @NotNull
    @Size(max = 26)
    private String salt;


    @NotNull
    @Size(min = 2 , max = 100) @Pattern(regexp = "^[a-zA-Z ]*$")
    private String firstname;

    @NotNull
    @Size(min = 2 , max = 100) @Pattern(regexp = "^[a-zA-Z ]*$")
    private String middlename;

    @NotNull
    @Size(min = 2 , max = 100) @Pattern(regexp = "^[a-zA-Z ]*$")
    private String lastname;

    @NotNull
    @OneToOne(orphanRemoval = true)
    private Address address;

    public User(){}










    /*-------------------------------GETTER AND SETTER-------------------------------*/

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
