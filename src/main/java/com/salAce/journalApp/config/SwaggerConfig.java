package com.salAce.journalApp.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class SwaggerConfig {

    private Object List;

    @Bean
    public OpenAPI customConfig(){

        return new OpenAPI()
                .info(
                new Info().title("Journal app APIs ")
                        .description("By Aaqib")
        )
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8081/").description("local"),
                        new Server().url("https://journal-entry-app-8.up.railway.app"),
                        new Server().url("http://localhost:8080/").description("live")))
//                .tags(Arrays.asList(
//                        new Tag().name("Public APIs"),
//                        new Tag().name("Users APIs"),
//                        new Tag().name("JournalEntry APIs"),
//                        new Tag().name("home APIs"),
//                        new Tag().name("Voice APIs"),
//                        new Tag().name("Admin APIs")
//                        )
//
//                ) not allowed in the latest version

                .components(new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .name("Authorization")
                                .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")) )
                ;
    }



}
