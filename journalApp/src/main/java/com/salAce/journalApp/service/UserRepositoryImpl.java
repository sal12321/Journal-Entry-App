package com.salAce.journalApp.service;

import com.salAce.journalApp.entity.SAuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserRepositoryImpl{

    @Autowired
    private MongoTemplate mongoTemplate ;

    public List<SAuser> getUsersForSA(){
        Query query = new Query();

        query.addCriteria(Criteria.where("email").exists(true)) ;

        query.addCriteria(Criteria.where("sentimentAnalysis").is(true)) ;


        List<SAuser> foundSAuser =  mongoTemplate.find(query , SAuser.class) ;
//        I am using "orm" object relational mapping concept using the database document as an object.


        return foundSAuser;
    }
}
