package DBService.dataSets;

import javax.persistence.*;

@Entity
@Table(name = "Sessions")
public class SessionsDataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "session_id", updatable = false)
    private String sessionId;

    @OneToOne(optional = false, mappedBy = "session")
    private UsersDataSet owner;

    public SessionsDataSet() {
    }

    public SessionsDataSet(String sessionId, UsersDataSet usersDataSet) {
        this.sessionId = sessionId;
        this.owner = usersDataSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSession() {
        return sessionId;
    }

    public void setSession(String sessionId) {
        this.sessionId = sessionId;
    }
}
