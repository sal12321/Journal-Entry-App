package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.JournalEntryService;

import com.salAce.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
    @RequestMapping("/journal")
public class JournalEntryControllerv2 {


    @Autowired
    private JournalEntryService journalEntryService;
    private Object ResponseEntity;

    @Autowired
    private UserEntryService userEntryService;


    @GetMapping() // localhost:8080/journal GET
    public ResponseEntity<?> getAllJournalEntriesOfUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // get the userName from SecurityContextHolder
        // no need to fetch from pathParameter


        User foundUser = userEntryService.findByUserName(userName);
        List<JournalEntry> all = foundUser.getJournalEntries();

        if (all != null) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @PostMapping() // localhost:8080/journal POST
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            // get the username from SecurityContextHolder and not from requestParameter

            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userEntryService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);

            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // got the suerName from authentication part

        boolean removed = journalEntryService.deleteById(myId, userName);

        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // got the suerName from authentication part

        User user = userEntryService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);


            if (journalEntry.isPresent()) {

                JournalEntry old = journalEntry.get(); // get the journalEntry if found using id...as old

                if (!(newEntry.getTitle() == null && newEntry.getTitle().equals(""))) {
                    old.setTitle(newEntry.getTitle());
                }


                    if (!(newEntry.getContent() == null && newEntry.getContent().equals(""))) {
                        old.setContent(newEntry.getContent());
                }



                        journalEntryService.saveEntry(old);
                        return new ResponseEntity<>(old, HttpStatus.OK);


                    }
                }

                return new ResponseEntity<>(HttpStatus.NOT_FOUND); /// if user is not found then send NOT_FOUND

            }


        }
