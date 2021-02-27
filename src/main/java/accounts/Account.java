package accounts;

import java.sql.SQLException;

public interface Account {

    void addNewUser(UserProfile userProfile) throws SQLException;

    UserProfile getUserByLogin(String login) throws SQLException;

    UserProfile getUserBySessionId(String sessionId) throws SQLException;

    void addSession(String sessionId, UserProfile userProfile) throws SQLException;

    void deleteSession(String sessionId) throws SQLException;

}
