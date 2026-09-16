package com.salAce.MindLog.service;

import com.salAce.MindLog.entity.CreateAdminDTO;
import com.salAce.MindLog.entity.User;
import com.salAce.MindLog.entity.UserDetailsVisibleToAdmin;
import com.salAce.MindLog.repo.UserEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
            user.setEmail(dtoUser.getEmail());

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
        public Long deleteAdmin(String email){

            Long count = 0L;

            try{
                count = userEntryRepo.deleteByEmail(email);
                log.info(count + "users were deleted with email " + email);


            } catch (Exception e){
                log.info(e.getMessage());
                log.info("0 users deleted with email : " + email);
            }

                return count;


        }

        public Long updateAdmin(User user){
            Long count = 0L;
        try{
             count =  userEntryRepo.updateUserDetailsByEmail(user.getEmail(), user.getUserName(), user.isSentimentAnalysis());
            log.info(count + "Admin was updated with email " + user.getEmail());

            return count;
        }
        catch (Exception e){
            log.info(e.getMessage());


            }

        return count;

        }


    }


