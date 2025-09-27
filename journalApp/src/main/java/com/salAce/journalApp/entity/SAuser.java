package com.salAce.journalApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")

public class SAuser {

        private String userName ;

        private String email;
        private boolean sentimentAnalysis ; // sentiment means the mood of the
        private List<String> roles ;
        @DBRef
        private List<JournalEntry> journalEntries = new ArrayList<>() ;


    }



