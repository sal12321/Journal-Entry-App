package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")


public class AdminController {

    @Autowired
  private  UserEntryService userEntryService ;


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all= userEntryService.getAll() ;
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK) ;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createAdmin(@RequestBody User user){

        userEntryService.saveAdmin(user);

        return new ResponseEntity<>(HttpStatus.OK) ;
    }

}
