package com.salAce.journalApp.service;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.repo.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

        @Autowired
        private JournalEntryRepo journalEntryRepo;
        @Autowired
        private UserEntryService userEntryService ;


        public void saveEntry(JournalEntry journalEntry, String userName)
        {
            User foundUser = userEntryService.findByUserName(userName) ;
            journalEntry.setDate(LocalDateTime.now());

          JournalEntry saved =   journalEntryRepo.save(journalEntry) ;
          foundUser.getJournalEntries().add(saved) ;
          userEntryService.saveEntry(foundUser) ;
        }
        public void saveEntry(JournalEntry journalEntry)
        {
            journalEntryRepo.save(journalEntry) ;
        }

        public List<JournalEntry> getAll(){
            return journalEntryRepo.findAll() ;

        }


        public Optional<JournalEntry> findById(ObjectId id) {
            return journalEntryRepo.findById(id) ;

        }

        public void deleteById(ObjectId id, String userName) {

            User foundUser = userEntryService.findByUserName(userName) ;
            foundUser.getJournalEntries().removeIf(x -> x.getId().equals(id)) ;

            // user ko find kro fir uska journal entries nikaalo fir usme us id ko search kro if present then delete it

            journalEntryRepo.deleteById(id) ;

        }

    }


    
