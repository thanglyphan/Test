package controller;



import businesslayer.MeetingEJB;
import businesslayer.UserEJB;
import datalayer.Meeting;
import datalayer.User;
import lists.Countries;
import validation.NotEmpty;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by thang on 12.10.2016.
 */
@Named
@SessionScoped
public class MeetingController implements Serializable {


    private String formCountry;
    private String formLocation;
    private String formScheduledDate;
    private String loggedInUser;

    @EJB
    private MeetingEJB meetingEJB;
    @EJB
    private UserEJB userEJB;
    @Inject
    private UserController userController;


    public String goToCreateNewMeetingPage(){
        return "createmeeting";
    }

    public String createMeeting(){
        //Here, a user should always be logged in, no need for checking that bcuz we know.
        User creatorOfMeeting = userEJB.getUser(userController.getLoggedInUser());
        Meeting meeting = meetingEJB.createMeeting(formLocation, formCountry, formScheduledDate, creatorOfMeeting.getEmail());
        meetingEJB.addUserToMeeting(meeting, creatorOfMeeting); //TRUE
        userEJB.addMeeting(meeting, creatorOfMeeting.getEmail());//Add the creator of meeting into meeting rn.
        if(meeting != null){
            return "home"; //Success
        }
        return "createmeeting"; //Failed
    }

    public void deleteMeeting(Long id){
        Meeting meeting = meetingEJB.getMeeting(id);
        System.out.println("HOMO FAEN: " + meeting.getUsers().size());
        meetingEJB.deleteMeeting(id);
    }

    public boolean getUserAttending(String email, Long id){
        System.out.println("Plata o plomo: " + email + " are not attending anyways " + id);
        User attender = userEJB.getUser(email); //Email is never null or never invalid
        //Meeting theMeeting = meetingEJB.getMeeting(id);
        if(attender == null){
            return false;
        }

        return attender.getMeetings().stream().anyMatch(n -> n.getId().equals(id));
    }

    public String clickCancel(){
        return "home";
    }

    /*-------------------------------GETTER AND SETTER-------------------------------*/
    public List<String> getCountries(){
        return Countries.getCountries(); //Arrays.asList("Norway", "Sweden", "Denmark", "Iceland", "Finland", "United States", "Germany");
    }

    public List<Meeting> getMeetings(){
        return meetingEJB.getMeetingList();
    }

    public String getFormCountry() {
        return formCountry;
    }

    public void setFormCountry(String formCountry) {
        this.formCountry = formCountry;
    }

    public String getFormLocation() {
        return formLocation;
    }

    public void setFormLocation(String formLocation) {
        this.formLocation = formLocation;
    }

    public String getFormScheduledDate() {
        return formScheduledDate;
    }

    public void setFormScheduledDate(String formScheduledDate) {
        this.formScheduledDate = formScheduledDate;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
