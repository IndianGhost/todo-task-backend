package io.github.indianghost.todotask.repositories;

import io.github.indianghost.todotask.domain.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
}
