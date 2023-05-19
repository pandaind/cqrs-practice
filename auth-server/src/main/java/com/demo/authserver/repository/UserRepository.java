package com.demo.authserver.repository;

import com.demo.user.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find user by username
     * @param username username
     * @return user
     */
    @Query("{'account.username': ?0}")
    Optional<User> findByUsername(String username);
}
