package controller;


import businesslayer.MeetingEJB;
import businesslayer.UserEJB;
import datalayer.Address;
import datalayer.Meeting;
import datalayer.User;
import lists.Countries;

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
    private String formEmail;
    private String formFirstname;
    private String formMiddlename;
    private String formLastname;
    private String formGateAddress;
    private String formCountry;
    private String formPassword;
    private String formConfirmPassword;
    private int formPostCode;
    private String formCity;
    private Address adr;
    private String loggedInUser;

    @EJB
    private UserEJB userEJB;

    @EJB
    private MeetingEJB meetingEJB;

    public String createNewUser(){ //Navigating in the faces-config.xml
        adr = new Address();
        adr.setCity(formCity);
        adr.setGateAddress(formGateAddress);
        adr.setCountry(formCountry);
        adr.setPostCode(formPostCode);
        if(formPassword.isEmpty()){ //This password can be "". Need this here.
            return "failed";
        }
        if(!formPassword.equals(formConfirmPassword)){
            return "failed";
        }
        boolean check = userEJB.createUser(formEmail, formPassword ,formFirstname, formMiddlename, formLastname, adr);
        if(check){
            loggedInUser = formEmail;
            return "success";
        }
        return "";
    }

    public String login(){
        String valid = userEJB.checkLogin(formEmail, formPassword);
        if(valid.equals("home")){
            loggedInUser = formEmail;
        }
        return valid;
    }

    public void attendMeeting(Long id, String email){
        Meeting meeting = meetingEJB.getMeeting(id);
        meetingEJB.addUserToMeeting(meeting, userEJB.getUser(email)); //Adding user to meeting.
        userEJB.addMeeting(meeting, email); //Adding meeting to the user.
    }

    public String createNewUserBtn(){
        return "register"; //Navigating in the faces-config.xml
    }

    public String cancelBtn(){
        return "home"; //Navigating in the faces-config.xml
    }

    public String logOut(){
        loggedInUser = null;
        return "home"; //Navigating here bcuz this gets called in layout.xhtml, making the page buggy.
    }

    public boolean validAdmin(String id){
        return userEJB.checkAdmin(id);
    }

    public boolean isLoggedIn(){
        return loggedInUser != null;
    }

    public void deleteUser(String email){
        userEJB.deleteUser(email);
    }

    public List<User> getUsers(){
        return userEJB.getUserList();
    }



















    /*-------------------------------GETTER AND SETTER-------------------------------*/

    public List<String> getCountries(){
        return Countries.getCountries(); //Arrays.asList("Norway", "Sweden", "Denmark", "Iceland", "Finland", "United States", "Germany");
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

    public String getFormConfirmPassword() {
        return formConfirmPassword;
    }

    public void setFormConfirmPassword(String formConfirmPassword) {
        this.formConfirmPassword = formConfirmPassword;
    }
}
