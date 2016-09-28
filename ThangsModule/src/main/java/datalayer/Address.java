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
        @NamedQuery(name = Address.FIND_ALL, query = "SELECT a FROM Address a"),
        @NamedQuery(name = Address.FIND_THIS_ADDRESS, query = "SELECT a FROM Address a WHERE a.id =?1"),
})
public class Address {
    /*-------------------------------QUERIES-------------------------------*/
    public static final String FIND_ALL = "Address.find_all";
    public static final String FIND_THIS_ADDRESS = "Address.find_this_address";
    /*-------------------------------DATA FIELDS-------------------------------*/

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Size(min = 2 , max = 100)
    private String gateAddress;

    @OneToOne
    private User user;

    @NotNull
    @Size(min = 2 , max = 100) @Pattern(regexp = "^[a-zA-Z ]*$")
    private String country;

    @NotNull
    private int postCode;

    @NotNull
    @Size(min = 2 , max = 100) @Pattern(regexp = "^[a-zA-Z ]*$")
    private String city;

    public Address(){}









    /*-------------------------------GETTER AND SETTER-------------------------------*/
    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    public String getGateAddress() {
        return gateAddress;
    }

    public void setGateAddress(String gateAddress) {
        this.gateAddress = gateAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
