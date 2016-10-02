package controller;

import businesslayer.UserBean;
import datalayer.Address;
import datalayer.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by thang on 26.09.2016.
 */
@Named
@SessionScoped
public class UserController implements Serializable {
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private String gateAddress;
    private String country;
    private String password;
    private int postCode;
    private String city;
    private Address adr;
    private String loggedInUser;

    @EJB
    private UserBean userBean;

    public void createNewUser(){
        adr = new Address();
        adr.setCity(city);
        adr.setGateAddress(gateAddress);
        adr.setCountry(country);
        adr.setPostCode(postCode);
        boolean check = userBean.createUser(email, password ,firstname, middlename, lastname, adr);
        if(check){
            loggedInUser = email;
        }
    }

    public String login(){
        String valid = userBean.checkLogin(email, password);
        if(!valid.equals("login")){
            loggedInUser = email;
        }
        return valid;
    }

    public String logOut(){
        loggedInUser = null;
        return "login";
    }

    public List<User> getUsers(){
        return userBean.getUserList();
    }

    public void deleteUser(String email){
        userBean.deleteUser(email);
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

    public Address getAdr() {
        return adr;
    }

    public void setAdr(Address adr) {
        this.adr = adr;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUser(){
        return loggedInUser;
    }
}
