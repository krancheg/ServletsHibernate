package DBService.dao;

import DBService.executor.Executor;
import accounts.Account;
import accounts.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO implements Account {

    private Executor executor;

    public UserDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    @Override
    public void addNewUser(UserProfile userProfile) throws SQLException {
        String query = String.format("insert into users (login, pass, email) values ('%s','%s','%s')",
                userProfile.getLogin(),
                userProfile.getPass(),
                userProfile.getEmail());
        executor.execUpdate(query);
    }

    @Override
    public UserProfile getUserByLogin(String login) throws SQLException {
        String query = String.format("select * from users where login='%s'",login);
        return executor.executeQuery(query,resultSet -> {
            if (resultSet.next()) {
                return new UserProfile(resultSet.getString("login"),
                        resultSet.getString("pass"), resultSet.getString("email"));
            } else return null;
        });
    }

    @Override
    public UserProfile getUserBySessionId(String sessionId) throws SQLException {
        String query = String.format("select * from sessions left join users " +
                "on sessions.user_id = users.id where sessions.id=%d",sessionId);
        return executor.executeQuery(query,resultSet -> {
            resultSet.next();
            return new UserProfile(resultSet.getString("login"),
                    resultSet.getString("pass"), resultSet.getString("email"));
        });
    }

    @Override
    public void addSession(String sessionId, UserProfile userProfile) throws SQLException {
        Long id = getIdByLogin(userProfile.getLogin());
        if (id == -1) {
            addNewUser(userProfile);
            id = getIdByLogin(userProfile.getLogin());
        }
        String query = String.format("insert into sessions (user_id) values (%d)",id);
        executor.execUpdate(query);
    }

    @Override
    public void deleteSession(String sessionId) throws SQLException {
        String query = String.format("delete from sessions where id=%d",sessionId);
        executor.execUpdate(query);
    }

    /*
    Return id of user by login,
    if id of user not found, return -1
     */
    private Long getIdByLogin(String login) throws SQLException {
        String query = String.format("select * from users where login='%s'",login);
        return (Long) executor.executeQuery(query, resultSet -> {
            if (resultSet.next()) {
                resultSet.next();
                return resultSet.getLong("id");
            } else return -1;
        });
    }

    public void createTables() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, " +
                "login varchar(256), pass varchar(256), email varchar(256), primary key (id))");
        executor.execUpdate("create table if not exists sessions (id bigint auto_increment, user_id bigint, primary key (id))");
    }

    public void dropTables() throws SQLException {
        executor.execUpdate("drop table if exists users");
        executor.execUpdate("drop table if exists sessions");
    }
}
