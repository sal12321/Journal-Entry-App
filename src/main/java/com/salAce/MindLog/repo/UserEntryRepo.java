package com.salAce.MindLog.repo;
import com.salAce.MindLog.entity.User;
import com.salAce.MindLog.entity.UserDetailsVisibleToAdmin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;


public interface UserEntryRepo extends MongoRepository<User , ObjectId>{

    User findByUserName (String userName) ;

    @Query(value = "{}", fields = "{ 'userName' : 1, 'email' : 1, 'sentimentAnalysis' : 1, 'roles' : 1 }")
    List<UserDetailsVisibleToAdmin> findUserDetailsVisibleToAdmin();


    Long deleteByEmail(String email);


    @Query("{ 'email' : ?0 }")
    @Update("{ '$set' : { 'userName' : ?1, 'sentimentAnalysis' : ?2 } }")
    Long updateUserDetailsByEmail(String email, String userName, boolean sentimentAnalysis);





}
