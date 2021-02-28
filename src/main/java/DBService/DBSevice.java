package DBService;


import DBService.dao.SessionDAO;
import DBService.dao.SessionDAOImpl;
import DBService.dao.UserDAO;
import DBService.dao.UserDAOImpl;
import DBService.dataSets.UsersDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class DBSevice implements UserDAOImpl, SessionDAOImpl {

    private final String hibernate_show_sql = "true";
    private final String hibernate_hbm2ddl_auto = "create-drop";

    private final SessionFactory sessionFactory;

    public DBSevice() {
        Configuration configuration = getH2Configuration();
        this.sessionFactory = createSessionFactory(configuration);
    }

    @Override
    public long addNewUser(UsersDataSet usersDataSet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDAO userDAO = new UserDAO(session);
        long id = userDAO.addNewUser(usersDataSet);
        transaction.commit();
        session.close();
        return id;
    }

    private interface ResultHandlerUser<T> {
        T handler(UserDAO userDAO);
    }

    private UsersDataSet getUserBy(ResultHandlerUser<UsersDataSet> handler) {
        Session session = sessionFactory.openSession();
        UserDAO userDAO = new UserDAO(session);
        UsersDataSet usersDataSet = handler.handler(userDAO);
        session.close();
        return usersDataSet;
    }

    @Override
    public UsersDataSet getUserByLogin(String login) {
        return getUserBy((handle) -> handle.getUserByLogin(login));
    }

    @Override
    public UsersDataSet getUserBySessionId(String sessionId) {
        return getUserBy((handle) -> handle.getUserBySessionId(sessionId));
    }

    @Override
    public long addSession(String sessionId, UsersDataSet usersDataSet) {
        Session session = sessionFactory.openSession();
        SessionDAO sessionDAO = new SessionDAO(session);
        long id = sessionDAO.addSession(sessionId,usersDataSet);
        session.close();
        return id;
    }

    @Override
    public void deleteSession(String sessionId) {
        Session session = sessionFactory.openSession();
        SessionDAO sessionDAO = new SessionDAO(session);
        sessionDAO.deleteSession(sessionId);
        session.close();
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }
}