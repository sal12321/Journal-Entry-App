package com.salAce.journalApp.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.salAce.journalApp.entity.CreateAdminDTO;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.entity.UserDetailsVisibleToAdmin;
import com.salAce.journalApp.repo.UserEntryRepo;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j  // this injects the instance at the compile time with name "log"
//  private static final org.slf4j.Logger log;
public class UserEntryService {

        private static final PasswordEncoder passEncoder = new BCryptPasswordEncoder() ;

        @Autowired
        private UserEntryRepo userEntryRepo;
        private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class) ;


    public void saveEntry(User user) {
            // before this, some changes were done in user and. then we are saving it after changement
        userEntryRepo.save(user);
    }
        // this is for newUser
        public boolean saveNewEntry(User user) {
        try{
            user.setPassword(passEncoder.encode(user.getPassword()));
            //get the pass ecode it and save the user in our database
            user.setRoles(Arrays.asList("USER"));
            userEntryRepo.save(user);
            return true ;

        }catch(Exception e ) {

            logger.info("Hey there! what the heck, this user is already there in our database, choose some other userName or go home ");
           logger.error("Error occurred for {}" , user.getUserName() , e);
            return false ;

        }

        }
        public void saveAdmin(CreateAdminDTO dtoUser) {
            dtoUser.setPassword(passEncoder.encode(dtoUser.getPassword()));
            //get the pass ecode it and save the user in our database


            User user = new User();
            user.setUserName(dtoUser.getUserName());
            user.setPassword(dtoUser.getPassword());

            user.setRoles(Arrays.asList("USER" , "ADMIN"));

            userEntryRepo.save(user);
        }

        public List<User> getAll() {
            return userEntryRepo.findAll();

        }
        public List<UserDetailsVisibleToAdmin> getAllUserToAdmin() {
            return userEntryRepo.findUserDetailsVisibleToAdmin();

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


