package com.salAce.journalApp.controller;

import com.salAce.journalApp.api.response.WeatherResponse;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.schedular.UserSchedular;
import com.salAce.journalApp.service.UserEntryService;
import com.salAce.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;// prettier

@Tag(name = "Users APIs")
@SecurityRequirement(name = "bearerAuth")

@RestController
@RequestMapping("/user")
@Slf4j

public class UserController {

        @Autowired
        private UserEntryService userEntryService ;
        @Autowired
        WeatherService weatherService ;
        @Autowired
        UserSchedular userSchedular ;


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

    @PostMapping("/send-me-mail")

    public ResponseEntity<?> mailBhejoReBaba(){
     try{
         userSchedular.fetchUserAndSendSaMail();
         return new ResponseEntity<>(HttpStatus.OK) ;
     }
     catch (Exception e) {
         log.error("error in sending the email"  +e) ;
         return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
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

                return new ResponseEntity<>("HI " + authentication.getName() + res.toString(), HttpStatus.OK);

            }

        }

        else{
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE) ;

            }

    }


    }


