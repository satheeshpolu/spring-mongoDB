package com.tech_pro.journalApp.repository;

import com.tech_pro.journalApp.entity.CustomerEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerEntry, ObjectId> {
}
