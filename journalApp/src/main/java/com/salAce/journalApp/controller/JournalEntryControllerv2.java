package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.service.JournalEntryService;

import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {


    @Autowired
    private JournalEntryService journalEntryService;
    private Object ResponseEntity;


    @GetMapping() // localhost:8080/journal GET
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryService.getAll();

        if(all != null) {
            return new ResponseEntity<>( all ,  HttpStatus.OK );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;


    }



    @PostMapping // localhost:8080/journal POST
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED) ;
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;

        }


    }


    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry =  journalEntryService.findById(myId) ;
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK) ;

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId ){

        journalEntryService.deleteById(myId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId ,  @RequestBody JournalEntry newEntry){
       JournalEntry old =  journalEntryService.findById(myId).orElse(null);   // first get the entry
        if (old != null) {


            if(newEntry.getTitle() == null && newEntry.getTitle().equals("")) {
                newEntry.setTitle(old.getTitle());

            }
            else {
                old.setTitle(newEntry.getTitle());

            }

            if(newEntry.getContent() == null && newEntry.getContent().equals("")) {
                newEntry.setContent(old.getContent());


            }
            else {
                old.setContent(newEntry.getTitle());
            }




            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



    }




}
