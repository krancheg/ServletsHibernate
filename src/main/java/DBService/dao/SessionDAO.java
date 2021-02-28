package DBService.dao;

import DBService.dataSets.SessionsDataSet;
import DBService.dataSets.UsersDataSet;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;

public class SessionDAO implements SessionDAOImpl {

    private Session session;

    public SessionDAO(Session session) {
        this.session = session;
    }

    @Override
    public long addSession(String sessionId, UsersDataSet usersDataSet) {
        return (Long) session.save(new SessionsDataSet(sessionId, usersDataSet));
    }

    @Override
    public void deleteSession(String sessionId) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SessionsDataSet> criteriaQuery = criteriaBuilder.createQuery(SessionsDataSet.class);
        Root<SessionsDataSet> root = criteriaQuery.from(SessionsDataSet.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("sessionId"),sessionId));
        SessionsDataSet s = session.createQuery(criteriaQuery).getSingleResult();
        session.delete(s);
    }

}
