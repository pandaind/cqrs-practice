package com.demo.authserver.repository;

import com.demo.authserver.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClientRepository
 */
@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    /**
     * Find client by client id
     *
     * @param clientId client id
     * @return client
     */
    @Query("{'clientId': ?0}")
    Optional<Client> findByClientId(String clientId);
}
