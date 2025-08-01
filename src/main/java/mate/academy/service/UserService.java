package mate.academy.service;

import mate.academy.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    Optional<User> findByEmail(String email); // we will use this `Optional` later

    boolean emailExists(String email);
}
