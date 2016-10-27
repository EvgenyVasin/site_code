package ru.jskills.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jskills.entities.UserRole;

/**
 * Created by safin.v on 24.10.2016.
 */
@Repository
@Qualifier(value = "userRoleRepository")
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    public UserRole findByUserRoleName(String userRoleName);
}