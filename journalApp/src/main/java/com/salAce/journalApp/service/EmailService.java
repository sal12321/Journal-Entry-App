package com.salAce.journalApp.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private JavaMailSender javaMailSender ;



    public void sendEmail(String to , String subject , String text){
        try{
            SimpleMailMessage mail = new SimpleMailMessage() ;


            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(text);
            javaMailSender.send(mail) ;
        }
        catch(Exception e){
            log.error("Exception occured while sending the email : " + e) ;
        }

    }
}
