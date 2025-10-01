package com.salAce.journalApp.repo;
import com.salAce.journalApp.entity.CreateAdminDTO;
import com.salAce.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserEntryRepo extends MongoRepository<User , ObjectId>{

    User findByUserName (String userName) ;




}