package mate.academy.service.impl;

import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        user.setSalt(HashUtil.generateSalt());
        user.setPassword(HashUtil.hashPassword(user.getPassword(), user.getSalt()));
        return userDao.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from User u where u.email = :param";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("param", email);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public boolean emailExists(String email) {
        return findByEmail(email).isPresent();
    }
}
