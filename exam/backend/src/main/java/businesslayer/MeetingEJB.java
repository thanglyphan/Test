package businesslayer;


import datalayer.Meeting;
import datalayer.User;
import validation.NotEmpty;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by thang on 07.10.2016.
 */
@Stateless
public class MeetingEJB implements Serializable{

    @PersistenceContext(unitName = "MyDB")
    EntityManager em;

    public MeetingEJB(){}

    private void persistInATransaction(Object... obj) {
        for(Object o : obj) {
            em.persist(o);
        }
    }


    public Meeting createMeeting(@NotEmpty String location, @NotEmpty String country, String date, String creator){
        Meeting meeting = new Meeting();
        meeting.setCountry(country);
        meeting.setLocation(location);
        meeting.setScheduledDate(date);
        meeting.setUsers(new ArrayList<>());
        meeting.setCreator(creator);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        meeting.setDate(reportDate);
        persistInATransaction(meeting);

        return meeting;
    }

    public boolean addUserToMeeting(Meeting meeting, User a){
        int count = meeting.getUsers().size();

        meeting.getUsers().add(a);
        List<User> users = meeting.getUsers();
        meeting.setUsers(users);
        if(count < meeting.getUsers().size()){
            return true;
        }
        return false;
    }


    public int deleteAllMeetings(){
        int i = 0;
        for(Meeting a: getMeetingList()){
            i++;
            deleteMeeting(a.getId());
        }
        return i;
    }

    public void deleteMeeting(Long id){
        System.out.println("Inside deleteMeeting");
        Meeting meeting = em.find(Meeting.class, id);
        System.out.println("Meeting to be removed: Country: " + meeting.getCountry() + " Loc: " + meeting.getLocation());
        //We have to remove the meeting from the users, or else we get failure. User cannot hold something, if its not there.
        if(meeting.getUsers().size() > 0){
            for(User a: meeting.getUsers()){
               System.out.println("ER DET SANT?!" + a.getMeetings().remove(meeting));
            }
        }
        //meeting.getUsers().removeAll(meeting.getUsers()); //Remove all users inside the meeting. NOT NEEDED.
        em.remove(meeting);
    }

    public Meeting getMeeting(Long id){
        return em.find(Meeting.class, id);
    }

    public List<Meeting> getMeetingList(){
        return em.createNamedQuery(Meeting.FIND_ALL).getResultList();
    }

    public List<Meeting> getMeetingListFromCreator(String creator){
        Query query = em.createNamedQuery(Meeting.GET_MEETING_FROM_CREATOR);
        query.setParameter("creator", creator);
        return query.getResultList();
    }
}
