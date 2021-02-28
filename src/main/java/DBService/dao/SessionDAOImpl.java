package DBService.dao;

import DBService.dataSets.UsersDataSet;

import java.sql.SQLException;

public interface SessionDAOImpl {

    long addSession(String sessionId, UsersDataSet usersDataSet) throws SQLException;

    void deleteSession(String sessionId) throws SQLException;

}
