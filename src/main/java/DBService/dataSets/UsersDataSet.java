package DBService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Users")
public class UsersDataSet implements Serializable {

    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "pass")
    private String pass;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id")
    private SessionsDataSet session;

    public UsersDataSet(String login, String pass, String email) {
        this.id = 0;
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public UsersDataSet(String login) {
        this.id = 1;
        this.login = login;
        this.pass = login;
        this.email = login;
    }

    public long getId() { return id; }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }
}
