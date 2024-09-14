package io.github.indianghost.todotask.repositories;

import io.github.indianghost.todotask.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by its nickname
     *
     * @param nickname The given nickname
     * @return an {@link Optional} of {@link User}
     */
    Optional<User> findByNickname(String nickname);
}
