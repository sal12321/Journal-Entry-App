package com.salAce.journalApp.service;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.repo.JournalEntryRepo;
import com.salAce.journalApp.repo.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {


        @Autowired
        private UserEntryRepo userEntryRepo;


        public void saveEntry(User user) {

            userEntryRepo.save(user);
        }

        public List<User> getAll() {
            return userEntryRepo.findAll();

        }

        public Optional<User> findById(ObjectId id) {
            return userEntryRepo.findById(id);

        }

        public void deleteById(ObjectId id) {
            userEntryRepo.deleteById(id);

        }
        public User findByUserName(String userName) {

            return userEntryRepo.findByUserName(userName) ;

        }

    }


