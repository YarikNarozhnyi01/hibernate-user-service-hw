package mate.academy.dao.impl;

import mate.academy.dao.UserDao;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User save(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not add user in add-method", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "from User u";
            Query<User> query = session.createQuery(hql, User.class);
            return query.getResultList();
        }
    }
}
