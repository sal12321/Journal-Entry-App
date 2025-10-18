package com.salAce.journalApp.cache;

import com.salAce.journalApp.entity.ConfigJournalAppEntity;
import com.salAce.journalApp.repo.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

///  making this class as a component as this is made to store the config from the database and immediately as we are using the PostConstruct annotation
/// trying to cash the data to use in another class
@Slf4j
@Component
public class LabsAppCache {

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo ;
    public Map<String , String> APP_CACHE = new HashMap<>();


    @PostConstruct
    public void init(){
        List<ConfigJournalAppEntity> all = configJournalAppRepo.findAll() ;

        // find all the entity from db and store them in HashMap
        for(ConfigJournalAppEntity configJournalAppEntity : all ){
            // in the first round it will store the first item in db i.e {api} and then {api_key}
            if(configJournalAppEntity.getService().equals("elevenlabs")){
                APP_CACHE.put("key" , configJournalAppEntity.getKey()) ;
                APP_CACHE.put("url" , configJournalAppEntity.getUrl()) ;
                APP_CACHE.put("service" , configJournalAppEntity.getService()) ;
                log.info("SERVICE " + configJournalAppEntity.getService()) ;
                log.info("URL " + configJournalAppEntity.getUrl()) ;
                log.info("KEY " + configJournalAppEntity.getKey()) ;

                return;
            }


        }


    }

}
