package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService ;


    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userEntryService.saveEntry(user);

    }
}
