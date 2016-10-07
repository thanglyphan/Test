package businesslayer;

import datalayer.Address;
import datalayer.Meeting;
import datalayer.User;
import validation.NotEmpty;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thang on 07.10.2016.
 */
@Stateless
public class MeetingBean {

    @PersistenceContext(unitName = "MyDB")
    EntityManager em;

    public MeetingBean(){}

    private void persistInATransaction(Object... obj) {
        for(Object o : obj) {
            em.persist(o);
        }
    }


    public boolean createMeeting(String place, Date date){
        Meeting meeting = new Meeting();
        meeting.setPlace(place);
        meeting.setScheduledDate(date);
        meeting.setUsers(new ArrayList<>());

        persistInATransaction(meeting);

        return getMeeting(meeting.getId()) != null;
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
        Meeting meeting = em.find(Meeting.class, id);
        meeting.getUsers().removeAll(meeting.getUsers());
        em.remove(meeting);
    }

    public Meeting getMeeting(Long id){
        return em.find(Meeting.class, id);
    }

    public List<Meeting> getMeetingList(){
        return em.createNamedQuery(Meeting.FIND_ALL).getResultList();
    }


}
