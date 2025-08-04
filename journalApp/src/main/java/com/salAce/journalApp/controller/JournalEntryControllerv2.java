package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {


    @Autowired
    private JournalEntryService journalEntryService;



    @GetMapping() // localhost:8080/journal GET
    public List<JournalEntry> getAll(){

        return journalEntryService.getAll();
    }



    @PostMapping // localhost:8080/journal POST
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry ;

    }


    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId).orElse(null) ;

    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntry(@PathVariable ObjectId myId ){

        journalEntryService.deleteById(myId);


        return true;
    }
    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId myId ,  @RequestBody JournalEntry newEntry){
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
            return newEntry ;

        }
        else {
            return null;
        }



    }




}
