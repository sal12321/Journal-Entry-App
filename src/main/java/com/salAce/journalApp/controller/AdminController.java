package com.salAce.journalApp.controller;

import com.salAce.journalApp.entity.CreateAdminDTO;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.UserEntryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j

@Tag(name = "Admin APIs" , description = "Create Admin and see all users")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
  private  UserEntryService userEntryService ;


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all= userEntryService.getAll() ;


        if(all != null &&  !all.isEmpty()){

            return new ResponseEntity<>(all, HttpStatus.OK) ;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminDTO user){
        try{
            userEntryService.saveAdmin(user);

            return new ResponseEntity<>(HttpStatus.CREATED) ;
        } catch (Exception e){
            log.error("Error in creating admin " + e) ;
        }
return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
    }

}
