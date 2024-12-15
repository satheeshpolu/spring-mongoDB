package com.tech_pro.journalApp.controller;

import com.tech_pro.journalApp.entity.CustomerEntry;
import com.tech_pro.journalApp.entity.JournalEntry;
import com.tech_pro.journalApp.service.CustomerService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerEntry> addCustomer(@RequestBody CustomerEntry customerEntry) {
        customerEntry.setDate(LocalDateTime.now());
        customerService.saveCustomer(customerEntry);
        return new ResponseEntity<>(customerEntry, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<CustomerEntry> all = customerService.getAllCustomers();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerEntry> getCustomerById(@PathVariable ObjectId id) {
        Optional<CustomerEntry> journalEntry = customerService.findById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/id/{entryId}")
    public ResponseEntity<?> updateCustomerById(@PathVariable ObjectId entryId, @RequestBody CustomerEntry newCustomerEntry) {
        CustomerEntry oldEntry = customerService.findById(entryId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setFirstName(newCustomerEntry.getFirstName() != null && !newCustomerEntry.getFirstName().equals("") ? newCustomerEntry.getFirstName() : oldEntry.getFirstName());
            //oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldEntry.getContent());
            //customerService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<CustomerEntry> deleteCustomerById(@PathVariable ObjectId id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<CustomerEntry> deleteAll() {
        customerService.deleteAllCustomer();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
