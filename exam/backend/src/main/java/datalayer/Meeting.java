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
        @NamedQuery(name = Meeting.FIND_ALL, query = "SELECT a FROM Meeting a")
})
public class Meeting {

    /*-------------------------------QUERIES-------------------------------*/
    public static final String FIND_ALL = "Meeting.find_all";
    /*-------------------------------FIELDS-------------------------------*/

    @Id
    @NotNull
    @GeneratedValue
    private long id;

    @NotEmpty
    @Size(min = 2, max = 40)
    private String place;

    @NotEmpty
    private Date scheduledDate;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy="meetings", cascade=CascadeType.ALL)
    private List<User> users;


    public Meeting(){}








    /*-------------------------------GETTER AND SETTER-------------------------------*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
