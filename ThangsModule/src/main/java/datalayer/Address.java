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
        @NamedQuery(name = Address.FIND_ALL, query = "SELECT a FROM Address a"),
})
public class Address {
    /*-------------------------------QUERIES-------------------------------*/
    public static final String FIND_ALL = "Address.find_all";
    /*-------------------------------DATA FIELDS-------------------------------*/

    @NotNull
    @Size(min = 2 , max = 100)
    @Id
    private String gateAddress;

    @NotNull
    @ManyToMany
    private List<User> users;

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
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
}
