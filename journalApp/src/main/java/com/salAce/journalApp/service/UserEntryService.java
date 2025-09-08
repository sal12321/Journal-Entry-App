package com.salAce.journalApp.service;

import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.repo.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

        private static final PasswordEncoder passEncoder = new BCryptPasswordEncoder() ;

        @Autowired
        private UserEntryRepo userEntryRepo;

    public void saveEntry(User user) {
            // before this, some changes were done in user and. then we are saving it after changement
        userEntryRepo.save(user);
    }
        // this is for newUser
        public void saveNewEntry(User user) {
            user.setPassword(passEncoder.encode(user.getPassword()));
            //get the pass ecode it and save the user in our database
            user.setRoles(Arrays.asList("USER"));
            userEntryRepo.save(user);
        }
        public void saveAdmin(User user) {
            user.setPassword(passEncoder.encode(user.getPassword()));
            //get the pass ecode it and save the user in our database
            user.setRoles(Arrays.asList("USER" , "ADMIN"));
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


