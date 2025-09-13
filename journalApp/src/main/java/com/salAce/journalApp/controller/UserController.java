package com.salAce.journalApp.controller;

import com.salAce.journalApp.api.response.WeatherResponse;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.UserEntryService;
import com.salAce.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;// prettier

@RestController
@RequestMapping("/user")

public class UserController {

        @Autowired
        private UserEntryService userEntryService ;
        @Autowired
        WeatherService weatherService ;


        @PutMapping()
        public ResponseEntity<?> updateUser(@RequestBody User user ) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;
         String userName = authentication.getName();

         User userInDb =  userEntryService.findByUserName(userName) ; // here how do i get userName
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userEntryService.saveNewEntry(userInDb);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;

        }
    @PostMapping("/create-user")
    public ResponseEntity<?> createAdmin(@RequestBody User user){
     boolean created = userEntryService.saveNewEntry(user);

     if(created) {
         return new ResponseEntity<>(HttpStatus.CREATED) ;

     }
     else{
         return new ResponseEntity<>(HttpStatus.CONFLICT) ;

     }

    }

    @GetMapping
    public ResponseEntity<?> greeting(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            WeatherResponse res = weatherService.getWeather("Gumla");



        if(res != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print


                return new ResponseEntity<>("HI" + authentication.getName() + mapper.writeValueAsString(res), HttpStatus.OK);


            } catch (JsonProcessingException e) {
                e.printStackTrace();

                return new ResponseEntity<>("HI" + authentication.getName() + res.toString(), HttpStatus.OK);

            }

        }

        else{
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE) ;

            }

    }


    }


