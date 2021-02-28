package DBService.dao;

import DBService.dataSets.UsersDataSet;

import java.sql.SQLException;

public interface UserDAOImpl {

    long addNewUser(UsersDataSet usersDataSet) throws SQLException;

    UsersDataSet getUserByLogin(String login) throws SQLException;

    UsersDataSet getUserBySessionId(String sessionId) throws SQLException;

}
