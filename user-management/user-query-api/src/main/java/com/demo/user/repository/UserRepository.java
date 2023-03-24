package com.demo.user.repository;

import com.demo.user.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'$or': [{'firstname': {'$regex' : ?0, '$options': 'i'}},{'lastname': {'$regex' : ?0, '$options': 'i'}},{'emailaddress': {'$regex' : ?0, '$options': 'i'}},{'account.username': {'$regex' : ?0, '$options': 'i'}}]}")
    List<User> findByFilterRegex(String filter);

}
