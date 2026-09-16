package com.salAce.MindLog.controller;

import com.salAce.MindLog.entity.CreateAdminDTO;
import com.salAce.MindLog.entity.User;
import com.salAce.MindLog.entity.UserDetailsVisibleToAdmin;
import com.salAce.MindLog.service.UserEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Slf4j

@Tag(name = "Admin APIs" , description = "Create Admin and see all users")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired

  private  UserEntryService userEntryService ;



    @GetMapping("/all-users")
    @Operation(summary = "See all users")
    public ResponseEntity<?> getAllUsers(){
        List<UserDetailsVisibleToAdmin> all= userEntryService.getAllUserToAdmin() ;


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

    @PutMapping("/edit")
    public ResponseEntity<?> updateAdmin(@RequestBody User user){
        Long count = 0L;
        try{
             count = userEntryService.updateAdmin(user);

            return new ResponseEntity<Long>(count, HttpStatus.ACCEPTED);
        } catch(Exception e){
            log.info(e.getMessage());
        }
        return new ResponseEntity<Long>(count, HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAdmin(@RequestBody User user){
        try {
            Long count = userEntryService.deleteAdmin(user.getEmail());
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch(Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }
    }


}
