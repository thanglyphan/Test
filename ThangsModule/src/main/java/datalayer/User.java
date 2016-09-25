package datalayer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thang on 20.09.2016.
 */
@Entity
@NamedQueries(value = {
        @NamedQuery(name = User.FIND_ALL, query = "SELECT a FROM User a"),
        @NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT a FROM User a WHERE a.email = ?1")
})
public class User {

    /*-------------------------------QUERIES-------------------------------*/
    public static final String FIND_ALL = "User.find_all";
    public static final String FIND_BY_EMAIL = "User.find_by_email";
    /*-------------------------------FIELDS-------------------------------*/
    @Id
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

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
    @ManyToMany(mappedBy = "users")
    private List<Address> adr;

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

    public List<Address> getAdr() {
        return adr;
    }

    public void setAdr(List<Address> adr) {
        this.adr = adr;
    }
}
