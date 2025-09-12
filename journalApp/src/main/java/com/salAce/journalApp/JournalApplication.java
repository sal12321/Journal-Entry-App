	package com.salAce.journalApp;

	import com.sun.source.doctree.SystemPropertyTree;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.context.ConfigurableApplicationContext;
	import org.springframework.context.annotation.Bean;
	import org.springframework.core.env.ConfigurableEnvironment;
	import org.springframework.data.mongodb.MongoDatabaseFactory;
	import org.springframework.data.mongodb.MongoTransactionManager;
	import org.springframework.transaction.annotation.EnableTransactionManagement;

    import java.util.Arrays;

    @SpringBootApplication
	@EnableTransactionManagement
	public class JournalApplication {

		public static void main(String[] args) {


			ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
//			ConfigurableEnvironment environment = context.getEnvironment();
//			System.out.println((environment.getActiveProfiles()[0])) ;


		}

		@Bean
		public MongoTransactionManager trans(MongoDatabaseFactory dbFactory){

			return new MongoTransactionManager(dbFactory) ;
		}

	}
