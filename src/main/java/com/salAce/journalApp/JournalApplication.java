	package com.salAce.journalApp;

	import com.salAce.journalApp.filter.JwtFilter;
	import com.sun.source.doctree.SystemPropertyTree;
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.context.ConfigurableApplicationContext;
	import org.springframework.context.annotation.Bean;
	import org.springframework.core.env.ConfigurableEnvironment;
	import org.springframework.data.mongodb.MongoDatabaseFactory;
	import org.springframework.data.mongodb.MongoTransactionManager;
	import org.springframework.scheduling.annotation.EnableScheduling;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
	import org.springframework.transaction.annotation.EnableTransactionManagement;
	import org.springframework.web.client.RestTemplate;

	import java.io.IOException;
	import java.util.Arrays;

    @SpringBootApplication
	@EnableTransactionManagement
	@EnableScheduling
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
		@Bean
		RestTemplate restTemplate (){
			return new RestTemplate();
		}

		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
			return configuration.getAuthenticationManager();
		}

		@Bean
		public JwtFilter jwtFilter() {
			return new JwtFilter();
		}




	}

