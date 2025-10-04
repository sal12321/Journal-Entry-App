package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.JournalEntryService;

import com.salAce.journalApp.service.UserEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.OidcLogoutDsl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
    @RequestMapping("/journal")

@Tag(name = "JournalEntry APIs", description = "Create, Read, Delete and update your Journals")
@SecurityRequirement(name = "bearerAuth")
public class JournalEntryControllerv2 {


    @Autowired
    private JournalEntryService journalEntryService;
    private Object ResponseEntity;

    @Autowired
    private UserEntryService userEntryService;


    @GetMapping() // localhost:8080/journal GET
    @Operation(summary = "Get all journal entries of a user" )
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
    @Operation(summary = "Post a new journal entry of a user" )

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
    @Operation(summary = "Get journal entry using entry id of a user" )

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
    @Operation(summary = "Delete a journal entry using entry id of a user" )

    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // got the suerName from authentication part

        boolean removed = journalEntryService.deleteById(myId, userName);

        if (removed) {
            return new ResponseEntity<>(HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("id/{myId}")
    @Operation(summary = "Update a journal entry of a user" )

    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {

        try{



            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName(); // got the suerName from authentication part

            User user = userEntryService.findByUserName(userName);


            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId); // get the journal entry by id
            if(journalEntry.isPresent()){
            JournalEntry old = journalEntry.get(); // get the journalEntry if found using id...as old


            old.setSentiment(newEntry.getSentiment());
            old.setContent(newEntry.getContent());
            old.setTitle(newEntry.getTitle());
            journalEntryService.saveEntry(old); // changes were put in old one
            return new ResponseEntity<>(HttpStatus.OK); ///

        }else{

                log.error("No Journal Entry was found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); ///
            }





        }catch (Exception e) {
            log.error(e.toString()) ;
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); ///
        }



    }


        }


