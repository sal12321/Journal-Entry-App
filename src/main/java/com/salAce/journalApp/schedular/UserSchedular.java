package com.salAce.journalApp.schedular;

import com.salAce.journalApp.entity.JournalEntry;
import com.salAce.journalApp.entity.SAuser;
import com.salAce.journalApp.entity.User;
import com.salAce.journalApp.enums.Sentiment;
import com.salAce.journalApp.repo.UserEntryRepo;
import com.salAce.journalApp.service.EmailService;
import com.salAce.journalApp.service.SentimentAnalysisService;
import com.salAce.journalApp.service.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
@Slf4j
@Component
public class UserSchedular {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepositoryImpl ;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService ;

    @Autowired
    private UserEntryRepo userEntryRepo ;




@Scheduled(cron = "0 0 0 * * 0")
public void fetchUserAndSendSaMail(){
        List<SAuser> users  =  userRepositoryImpl.getUsersForSA();
        for(SAuser user : users){
            List<JournalEntry> journalEntries =  user.getJournalEntries() ;
            List<Sentiment> sentiments =  user.getJournalEntries().stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList()) ;
///  here we are having the list of sentiment
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>() ;
            for(Sentiment sentiment : sentiments) {
                if(sentiment != null) {
                    sentimentCounts.put(sentiment , sentimentCounts.getOrDefault(sentiment , 0) + 1)  ;
                }
            }
            Sentiment mostFreqSentiment = null ;
            int maxCount= 0 ;

            for(Map.Entry<Sentiment , Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue() >= maxCount){
                    maxCount = entry.getValue() ;
                    mostFreqSentiment = entry.getKey() ;
                }
            }
            if(mostFreqSentiment != null){
                emailService.sendEmail(user.getEmail() , "Sentiment of last week"  , mostFreqSentiment.toString()) ;
            }
            else{
                emailService.sendEmail(user.getEmail() , "Sentiment of last week"  , "you have opted no any sentiment") ;

            }



        }






    }

    public void getSentimentEmail(String username) {


        User user = userEntryRepo.findByUserName(username);
        if (Objects.equals(user.getEmail(), "")){
            log.error("Email was not found of this user");

            return ;
        }

        List<Sentiment> sentiments =user.getJournalEntries().stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList()) ;
///  here we are having the list of sentiment
        Map<Sentiment, Integer> sentimentCounts = new HashMap<>() ;
        for(Sentiment sentiment : sentiments) {
            if(sentiment != null) {
                sentimentCounts.put(sentiment , sentimentCounts.getOrDefault(sentiment , 0) + 1)  ;
            }
        }
        Sentiment mostFreqSentiment = null ;
        int maxCount= 0 ;

        for(Map.Entry<Sentiment , Integer> entry : sentimentCounts.entrySet()){
            if(entry.getValue() >= maxCount){
                maxCount = entry.getValue() ;
                mostFreqSentiment = entry.getKey() ;
            }
        }
        if(mostFreqSentiment != null){
            emailService.sendEmail(user.getEmail() , "Sentiment of last week"  , mostFreqSentiment.toString()) ;
            log.info("Email was sent");

        }
        else{
            emailService.sendEmail(user.getEmail() , "Sentiment of last week"  , "you have opted no any sentiment") ;
            log.info("no sentiment");


        }



    }


}

