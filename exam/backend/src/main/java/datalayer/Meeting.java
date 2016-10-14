package datalayer;

import validation.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by thang on 07.10.2016.
 */
@Entity
@NamedQueries(value = {
        @NamedQuery(name = Meeting.FIND_ALL, query = "SELECT a FROM Meeting a"),
        @NamedQuery(name = Meeting.GET_MEETING_FROM_CREATOR, query = "SELECT a FROM Meeting a where a.creator = :creator"),
})
public class Meeting {

    /*-------------------------------QUERIES-------------------------------*/
    public static final String FIND_ALL = "Meeting.find_all";
    public static final String GET_MEETING_FROM_CREATOR = "Meeting.get_meeting_from_creator";
    /*-------------------------------FIELDS-------------------------------*/

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 40)
    private String country;

    @NotEmpty
    @Size(min = 2, max = 120)
    private String location;

    @NotEmpty
    @Size(min = 2, max = 120) //TODO: Maybe make annotation "DATE"?
    private String scheduledDate;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String creator;

    @NotEmpty
    private String date;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="meetings", cascade=CascadeType.DETACH)
    private List<User> users;


    public Meeting(){}








    /*-------------------------------GETTER AND SETTER-------------------------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
