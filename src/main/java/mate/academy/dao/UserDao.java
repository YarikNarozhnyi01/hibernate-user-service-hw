package mate.academy.dao;

import mate.academy.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    User save(User user);

    Optional<User> get(Long id);

    List<User> getAll();

    Optional<User> findByEmail(String email);
}
