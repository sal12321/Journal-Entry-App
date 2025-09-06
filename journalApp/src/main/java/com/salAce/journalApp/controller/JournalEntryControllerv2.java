package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.JournalEntryService;

import com.salAce.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
    @RequestMapping("/journal")
public class JournalEntryControllerv2 {


    @Autowired
    private JournalEntryService journalEntryService;
    private Object ResponseEntity;

    @Autowired
    private UserEntryService userEntryService ;


    @GetMapping("{userName}") // localhost:8080/journal GET
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {


        User foundUser =   userEntryService.findByUserName(userName) ;
        List<JournalEntry> all = foundUser.getJournalEntries();

        if (all != null) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @PostMapping("{userName}") // localhost:8080/journal POST
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry , @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(myEntry , userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId , @PathVariable String userName) {

        journalEntryService.deleteById(myId , userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName  ) {

        JournalEntry old = journalEntryService.findById(myId).orElse(null);   // first get the entry
        if (old != null) {
            if (newEntry.getTitle() == null && newEntry.getTitle().equals("")) {
                newEntry.setTitle(old.getTitle());

            } else {
                old.setTitle(newEntry.getTitle());

            }

            if (newEntry.getContent() == null && newEntry.getContent().equals("")) {
                newEntry.setContent(old.getContent());


            } else {
                old.setContent(newEntry.getTitle());
            }



            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }


}
