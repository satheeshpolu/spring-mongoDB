package com.tech_pro.journalApp.controller;

import com.tech_pro.journalApp.entity.JournalEntry;
import com.tech_pro.journalApp.entity.User;
import com.tech_pro.journalApp.service.JournalEntryService;
import com.tech_pro.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalByUser(@PathVariable String userName) {
        User userData = userService.findByUserName(userName);
        List<JournalEntry> all = userData.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(all, HttpStatus.NO_CONTENT);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String userName) {
        try {
//            User user = userService.findByUserName(userName);
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryByID(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId id, @PathVariable String userName) { // ? = Wildcard
        journalEntryService.deleteById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{userName}/{entryId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId entryId, @RequestBody JournalEntry updatedEntry) {
        JournalEntry oldEntry = journalEntryService.findById(entryId).orElse(null);
      if (oldEntry != null) {
          oldEntry.setTitle(updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("") ? updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("") ? updatedEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
          return new ResponseEntity<>(oldEntry, HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
