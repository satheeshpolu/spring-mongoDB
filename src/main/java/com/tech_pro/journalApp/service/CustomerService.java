package com.tech_pro.journalApp.service;

import com.tech_pro.journalApp.entity.CustomerEntry;
import com.tech_pro.journalApp.repository.CustomerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void saveCustomer(CustomerEntry customerEntry){
        customerRepository.save(customerEntry);
    }

    public List<CustomerEntry> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<CustomerEntry> findById(ObjectId id){
        return customerRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        customerRepository.deleteById(id);
    }

    public void deleteAllCustomer(){
        customerRepository.deleteAll();
    }
}
