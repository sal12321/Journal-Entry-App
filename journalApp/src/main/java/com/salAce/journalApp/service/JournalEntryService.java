package com.salAce.journalApp.service;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.repo.JournalEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {

        @Autowired
        private JournalEntryRepo journalEntryRepo;
        @Autowired
        private UserEntryService userEntryService ;

@Transactional
        public void saveEntry(JournalEntry journalEntry, String userName) throws Exception
        {
            try{
                User foundUser = userEntryService.findByUserName(userName) ;
                journalEntry.setDate(LocalDateTime.now());

                JournalEntry saved =   journalEntryRepo.save(journalEntry) ;
                foundUser.getJournalEntries().add(saved) ;

                userEntryService.saveEntry(foundUser) ;

            } catch(Exception e){

                throw new Exception("generated exception" , e) ;
            }

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
@Transactional
        public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false ;
    try {
        User foundUser = userEntryService.findByUserName(userName) ;  // removeIf returns boolean
        removed =   foundUser.getJournalEntries().removeIf(x -> x.getId().equals(id)) ;

        // user ko find kro fir uska journal entries nikaalo fir usme us id ko search kro if present then delete it
        if(removed){
            userEntryService.saveEntry(foundUser); // save the foundUser i.e remove the id of that journal....
            journalEntryRepo.deleteById(id) ;
            System.out.println("the journal was removed and user was updated");
            userEntryService.saveEntry(foundUser);
        }
    }catch(Exception e ) {

        System.out.println(e);
        throw new RuntimeException("an error has occured while deleting the entry " ,e );


    }
    return removed ;


        }
//        public List<JournalEntry> findByUserName(String userName) {
//
//
//        }

    }


    
