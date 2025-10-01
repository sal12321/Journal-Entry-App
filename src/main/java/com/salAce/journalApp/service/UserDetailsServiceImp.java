//package com.salAce.journalApp.service;
//
//import com.salAce.journalApp.entity.User;
//import com.salAce.journalApp.repo.UserEntryRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserDetailsServiceImp implements UserDetailsService {
//
//
//    @Autowired
//    private UserEntryRepo userEntryRepo;
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//
//        User user = userEntryRepo.findByUserName(username) ;
//
//        if(user != null) {
//            return org.springframework.security.core.userdetails.User.builder()
//                        .username(user.getUserName())
//                        .password(user.getPassword())
//                        .roles(user.getRoles().toArray(new String[0]))
//                        .build();
//        }
//        throw new UsernameNotFoundException("UserName not found : " + username);
//        // this is provided by spring security ;
//    }
//}


package com.salAce.journalApp.service;

import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.repo.UserEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserEntryRepo userEntryRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userEntryRepo.findByUserName(username);

        if (user != null) {
            // the spring security is fetching the username and password from basic auth Header adn using here to find
            // the user verification is not done here, it is internally done when we call httpBasic() in spring security
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0])) // convert roles list to array
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
