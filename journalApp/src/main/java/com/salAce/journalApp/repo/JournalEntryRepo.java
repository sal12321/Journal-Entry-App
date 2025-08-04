package com.salAce.journalApp.repo;
import com.salAce.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalEntryRepo extends MongoRepository<JournalEntry , ObjectId>{



}