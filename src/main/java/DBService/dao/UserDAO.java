package DBService.dao;

import DBService.dataSets.SessionsDataSet;
import org.hibernate.Session;
import DBService.dataSets.UsersDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserDAO implements UserDAOImpl {

    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    @Override
    public long addNewUser(UsersDataSet usersDataSet) {
        return (Long) session.save(usersDataSet);
    }

    @Override
    public UsersDataSet getUserByLogin(String login) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> criteriaQuery = criteriaBuilder.createQuery(UsersDataSet.class);
        Root<UsersDataSet> root = criteriaQuery.from(UsersDataSet.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("login"),login));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public UsersDataSet getUserBySessionId(String sessionId) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UsersDataSet> criteriaQuery = criteriaBuilder.createQuery(UsersDataSet.class);
        Root<SessionsDataSet> root = criteriaQuery.from(SessionsDataSet.class);
        criteriaQuery.select(root.get("owner"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("owner"),sessionId));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

}
