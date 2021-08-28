package com.opensource.todo.user.repositories;


import com.opensource.todo.user.models.ERole;
import com.opensource.todo.user.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Optional<Role> findByTitle(String title);
}