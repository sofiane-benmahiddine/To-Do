package com.opensource.todo.task;

import com.opensource.todo.user.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByUser(User user);

    Optional<Task> findByUserAndId(User user, String id);

    @Transactional
    void deleteById(String id);

}
