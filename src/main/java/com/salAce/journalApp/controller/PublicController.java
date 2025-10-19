package com.salAce.journalApp.controller;


import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salAce.journalApp.api.response.WeatherResponse;
import com.salAce.journalApp.entity.LoginDTO;
import com.salAce.journalApp.entity.SAuser;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.repo.UserEntryRepo;
import com.salAce.journalApp.service.*;
import com.salAce.journalApp.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Public APIs", description = "Login and Signup")

@RestController ///  so that these methods will return in json/text format and not any jsp or html....
@Slf4j
@RequestMapping("/public")

public class PublicController {
    @Autowired
    private UserEntryRepo userEntryRepo ;
    @Autowired
    private WeatherService weatherService ;
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

    @ResponseBody
    @GetMapping("/sa/users")
    @Operation(summary = "See users who have opted for sentimentAnalysis")
    public ResponseEntity<?> check(){

        List<SAuser> foundUserForSA = userRepositoryImpl.getUsersForSA();
        if(foundUserForSA != null){
            return new ResponseEntity<>(foundUserForSA , HttpStatus.OK) ;

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
      boolean flag =  userEntryService.saveNewEntry(user);

      Map<String, String> response = new HashMap<>();



        if (!flag) {

            response.put("status" , "conflict") ;
            response.put("message", "Username already exists");

            return new ResponseEntity<>(response , HttpStatus.CONFLICT); // 409 Conflict
        }

        response.put("status" , "created") ;
        response.put("message", "Account Created Successfully");
        return new ResponseEntity<>(response , HttpStatus.CREATED);
     }


     @ResponseBody
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO user){



try{
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName() , user.getPassword())) ;
    UserDetails userDetails =
            userDetailsServiceImp.loadUserByUsername(user.getUserName()) ;

    String jwt =  jwtUtils.generateToken(user.getUserName()) ;


    Map<String , String> jwtAndRole = new HashMap<>();

    try{
    String role = userEntryRepo.findByUserName(user.getUserName()).getRoles().get(1);
        jwtAndRole = Map.of("jwt" , jwt , "role" , role );


}catch (Exception e){
    jwtAndRole = Map.of("jwt" , jwt , "role" , "" );


}


    return new ResponseEntity<>(jwtAndRole , HttpStatus.OK) ;

}catch (Exception e ){
    log.error(String.valueOf(e));
}
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
     }




    @Operation(summary = "Check weather of your city" )
    @GetMapping()
    public ResponseEntity<?> greeting(@RequestParam("city") String city ){
        ;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse res = weatherService.getWeather(city);



        if(res != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print

                log.info("response is ok 1; HttpStatus.OK");
                return new ResponseEntity<>( mapper.writeValueAsString(res), HttpStatus.OK);



            } catch (JsonProcessingException e) {

                e.printStackTrace();
                log.info("response is ok 2; HttpStatus.OK");

                return new ResponseEntity<>(res.toString(), HttpStatus.OK);

            }

        }

        else{                log.info("response is ok 1; HttpStatus.NotAcceptable");

            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE) ;

        }

    }


}
