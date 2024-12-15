package com.tech_pro.journalApp.repository;

import com.tech_pro.journalApp.entity.JournalEntry;
import com.tech_pro.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);
}
