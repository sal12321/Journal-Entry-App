package com.salAce.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
@Data
@NoArgsConstructor
// ADMIN / USER LOGIN DTO
public class LoginDTO {


    @Indexed(unique = true)
    @NonNull
    private String userName ;
    @NonNull
    private String password ;


}








