package com.federicobonel.mapcoloring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {



    public static final String MAP_TAG = "Map Coloring Service API";
    public static final String MAP_DESC = "Provides solutions to map coloring problems following the four colour theorem";

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(getMetaData())
                .tags(new Tag(MAP_TAG, MAP_DESC));
    }

    private ApiInfo getMetaData() {

        Contact contact = new Contact("Federico Jorge Bonel Tozzi",
                "https://github.com/FedericoBonel",
                "Bonelfederico@gmail.com");

        return new ApiInfo(
                "Map RestAPI",
                "This RestAPI exposes greedy and backtracking algorithms to find the solution to the graph coloring "
                        + "problem applied to two dimensional maps.",
                "1.0.0",
                "https://smartbear.com/terms-of-use/",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
                );
    }
}
