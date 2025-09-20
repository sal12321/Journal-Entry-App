package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.SAuser;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.EmailService;
import com.salAce.journalApp.service.UserRepositoryImpl;
import com.salAce.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController ///  so that these methods will return in json/text format and not any jsp or html....
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService ;

    @Autowired
    UserRepositoryImpl userRepositoryImpl;
    @Autowired
    EmailService emailService ;

    @GetMapping("/sa/users")
    public ResponseEntity<?> check(){

        List<SAuser> foundUserForSA = userRepositoryImpl.getUsersForSA();
        if(foundUserForSA != null){
            return new ResponseEntity<>(foundUserForSA , HttpStatus.OK) ;

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userEntryService.saveNewEntry(user);

    }
//    @PostMapping("/send-mail")
//    public void mailBhejo(){
//        emailService.sendEmail();
//
//
//
//    }

    @PostMapping("/send-mail")
    public void mailBhejo(){
//        emailService.sendEmail();



    }



}
