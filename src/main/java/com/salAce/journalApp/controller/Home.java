package com.salAce.journalApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestControllerx
@Controller
@RequestMapping("/")
public class Home {


    @GetMapping
    public String enter(){
        return "journal_homepage.html" ;
    }

}
