package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.UserEntryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")

public class UserController {

        @Autowired
        private UserEntryService userEntryService ;

        @RequestMapping
        public List<User> getAllUsers(){
            return userEntryService.getAll() ;

        }
        @PostMapping
        public void createUser(@RequestBody User user){
            userEntryService.saveEntry(user);

        }
        @PutMapping("/{userName}")
        public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String userName) {
            User userInDb =  userEntryService.findByUserName(userName) ;

            if(userInDb != null) {
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userEntryService.saveEntry(user);

            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;

        }


    }


