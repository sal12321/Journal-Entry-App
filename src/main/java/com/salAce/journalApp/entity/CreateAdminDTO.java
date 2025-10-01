package com.salAce.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;



@Data
@NoArgsConstructor
// ADMIN / USER LOGIN DTO

public class CreateAdminDTO{

    @Indexed(unique = true)
    @NonNull
    private String userName ;
    @NonNull
    private String password ;



}













