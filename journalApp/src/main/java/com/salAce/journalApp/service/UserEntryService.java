package com.salAce.journalApp.service;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.repo.JournalEntryRepo;
import com.salAce.journalApp.repo.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

        private static final PasswordEncoder passEncoder = new BCryptPasswordEncoder() ;

        @Autowired
        private UserEntryRepo userEntryRepo;

    public void updateJournalEntries(User user) {
        User existingUser = userEntryRepo.findByUserName(user.getUserName());
        existingUser.setJournalEntries(user.getJournalEntries()); // keep password intact
        userEntryRepo.save(existingUser);
    }

        public void saveEntry(User user) {
            user.setPassword(passEncoder.encode(user.getPassword())); // get the pass ecode it and save it back to the object
            user.setRoles(Arrays.asList("USER"));
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


