package com.pk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by pallav.kothari on 3/28/17.
 */
@PropertySource("classpath:swagger.properties")
@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {ApiQuickstartApplication.class})
public class SwaggerConfig {
    private static final String LOCALHOST = "localhost:8080";
    private static final String PROD_HOST = "example.com";

    @Bean
    public Docket api() {
        boolean runningTests = Boolean.parseBoolean(System.getProperty("running.tests"));
        String host = runningTests ? PROD_HOST : LOCALHOST;

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pk"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .host(host)
                .apiInfo(new ApiInfo(
                        "api quickstart",
                        "get started quickly",
                        "v1",
                        "https://github.com/pallavkothari",
                        new Contact("PK", "https://github.com/pallavkothari", "fake@spam.com"),
                        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0"
                        ));
    }
}
