package ru.jskills.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jskills.entities.User;

/**
 * Created by safin.v on 23.10.2016.
 */
@Repository
@Qualifier(value = "userRepository")
public interface UsersRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);
}
