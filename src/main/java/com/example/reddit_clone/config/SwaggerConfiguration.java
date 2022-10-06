package com.example.reddit_clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    
    /**
     * 
     * Docket helps configure a subset of the services to be documented and groups them
     * by name. Significant changes to this is the ability to provide an expressive predicate based for api 
     * selection
     * @return Docket
     */
    @Bean
    public Docket redditCloneApi() { 
        System.out.println("✅ SwaggerConfig.redditCloneApi()");
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(getApiInfo());
    }

    /**
     * Return the API Get info 
     * @return
     */
    private ApiInfo getApiInfo() {
        System.out.println("✅ SwaggerConfig.getApiInfo()");
        return new ApiInfoBuilder()
            .title("Community Blogging site in Spring")
            .version("1.2")
            .description("API documentation for Community Blogging Site in Spring")
            .contact(new Contact("Philip Daquin", "github.com/philipdaquin", ""))
            .license("MIT License")
            .build();
    }
}
