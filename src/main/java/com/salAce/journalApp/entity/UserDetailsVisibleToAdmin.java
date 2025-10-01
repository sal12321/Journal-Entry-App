package com.salAce.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;



import java.util.List;


@Data
@NoArgsConstructor

public class UserDetailsVisibleToAdmin {

    private String userName ;

    private String email;
    private boolean sentimentAnalysis ;
    private List<String> roles ;

}











