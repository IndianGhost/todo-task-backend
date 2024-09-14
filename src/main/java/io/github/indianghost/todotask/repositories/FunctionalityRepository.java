package io.github.indianghost.todotask.repositories;

import io.github.indianghost.todotask.domain.Functionality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionalityRepository extends JpaRepository<Functionality, Long> {
}
