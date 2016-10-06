package controller;

import businesslayer.UserBean;
import datalayer.Address;
import datalayer.User;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by thang on 26.09.2016.
 */
@Named
@SessionScoped
public class UserController implements Serializable {
    private String formEmail;
    private String formFirstname;
    private String formMiddlename;
    private String formLastname;
    private String formGateAddress;
    private String formCountry;
    private String formPassword;
    private int formPostCode;
    private String formCity;
    private Address adr;
    private String loggedInUser;

    @EJB
    private UserBean userBean;

    public void createNewUser(){
        adr = new Address();
        adr.setCity(formCity);
        adr.setGateAddress(formGateAddress);
        adr.setCountry(formCountry);
        adr.setPostCode(formPostCode);
        boolean check = userBean.createUser(formEmail, formPassword ,formFirstname, formMiddlename, formLastname, adr);
        if(check){
            loggedInUser = formEmail;
        }
    }

    public String login(){
        String valid = userBean.checkLogin(formEmail, formPassword);
        if(!valid.equals("login")){
            loggedInUser = formEmail;
        }
        return valid;
    }

    public String logOut(){
        loggedInUser = null;
        return "login";
    }

    public boolean validAdmin(String id){
        return userBean.checkAdmin(id);
    }

    public void deleteUser(String email){
        userBean.deleteUser(email);
    }

    public List<User> getUsers(){
        return userBean.getUserList();
    }

    public boolean isLoggedIn(){
        return loggedInUser != null;
    }

    public List<String> getCountries(){
        return Arrays.asList("Norway", "Sweden", "Denmark", "Iceland", "Finland", "United States", "Germany");
    }

    public String getFormEmail() {
        return formEmail;
    }

    public void setFormEmail(String formEmail) {
        this.formEmail = formEmail;
    }

    public String getFormFirstname() {
        return formFirstname;
    }

    public void setFormFirstname(String formFirstname) {
        this.formFirstname = formFirstname;
    }

    public String getFormMiddlename() {
        return formMiddlename;
    }

    public void setFormMiddlename(String formMiddlename) {
        this.formMiddlename = formMiddlename;
    }

    public String getFormLastname() {
        return formLastname;
    }

    public void setFormLastname(String formLastname) {
        this.formLastname = formLastname;
    }

    public String getFormGateAddress() {
        return formGateAddress;
    }

    public void setFormGateAddress(String formGateAddress) {
        this.formGateAddress = formGateAddress;
    }

    public String getFormCountry() {
        return formCountry;
    }

    public void setFormCountry(String formCountry) {
        this.formCountry = formCountry;
    }

    public String getFormPassword() {
        return formPassword;
    }

    public void setFormPassword(String formPassword) {
        this.formPassword = formPassword;
    }

    public int getFormPostCode() {
        return formPostCode;
    }

    public void setFormPostCode(int formPostCode) {
        this.formPostCode = formPostCode;
    }

    public String getFormCity() {
        return formCity;
    }

    public void setFormCity(String formCity) {
        this.formCity = formCity;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
