package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.UserEntryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")

public class UserController {

        @Autowired
        private UserEntryService userEntryService ;





        @PutMapping()
        public ResponseEntity<?> updateUser(@RequestBody User user ) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
         String userName = authentication.getName();

         User userInDb =  userEntryService.findByUserName(userName) ; // here how do i get userName
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userEntryService.saveEntry(userInDb);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;

        }


    }


