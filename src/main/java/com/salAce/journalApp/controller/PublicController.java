package com.salAce.journalApp.controller;


import ch.qos.logback.classic.Logger;
import com.salAce.journalApp.entity.LoginDTO;
import com.salAce.journalApp.entity.SAuser;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.service.EmailService;
import com.salAce.journalApp.service.UserDetailsServiceImp;
import com.salAce.journalApp.service.UserRepositoryImpl;
import com.salAce.journalApp.service.UserEntryService;
import com.salAce.journalApp.utils.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Public APIs", description = "Login and Signup")

@RestController ///  so that these methods will return in json/text format and not any jsp or html....
@Slf4j
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService ;
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Autowired
    UserRepositoryImpl userRepositoryImpl;
    @Autowired
    EmailService emailService ;

    @Autowired
    private AuthenticationManager authenticationManager ;


    @Autowired
    private JwtUtil jwtUtils ;

    @GetMapping("/sa/users")
    public ResponseEntity<?> check(){

        List<SAuser> foundUserForSA = userRepositoryImpl.getUsersForSA();
        if(foundUserForSA != null){
            return new ResponseEntity<>(foundUserForSA , HttpStatus.OK) ;

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userEntryService.saveNewEntry(user);

     }

     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO user){



try{
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName() , user.getPassword())) ;
    UserDetails userDetails =
            userDetailsServiceImp .loadUserByUsername(user.getUserName()) ;

   String jwt =  jwtUtils.generateToken(user.getUserName()) ;

    return new ResponseEntity<>(jwt , HttpStatus.OK) ;

}catch (Exception e ){
    log.error(String.valueOf(e));
}
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
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
