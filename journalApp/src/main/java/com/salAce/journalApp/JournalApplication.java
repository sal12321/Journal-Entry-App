	package com.salAce.journalApp;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.context.annotation.Bean;
	import org.springframework.data.mongodb.MongoDatabaseFactory;
	import org.springframework.data.mongodb.MongoTransactionManager;
	import org.springframework.transaction.annotation.EnableTransactionManagement;

	@SpringBootApplication
	@EnableTransactionManagement
	public class JournalApplication {

		public static void main(String[] args) {
			SpringApplication.run(JournalApplication.class, args);
		}


		@Bean
		public MongoTransactionManager trans(MongoDatabaseFactory dbFactory){
	// we use the instance of this to do any work with db like getSessions etc
			return new MongoTransactionManager(dbFactory) ; // this helps to work with database... like connection, transaction and all other stuffs
		}

	}
