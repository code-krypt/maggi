package com.drykode.maggi.client.api.configs;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value("#{'${maggi.configurations.swagger.definitions.api.produces:application/json}'.split(',')}")
  private Set<String> apiOutputFormats;

  @Value("#{'${maggi.configurations.swagger.definitions.api.consumes:application/json}'.split(',')}")
  private Set<String> apiInputFormats;

  @Value("#{'${maggi.configurations.swagger.definitions.api.protocols:http,https}'.split(',')}")
  private Set<String> apiProtocols;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.drykode.maggi.client"))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)
        .produces(apiOutputFormats)
        .consumes(apiInputFormats)
        .protocols(apiProtocols)
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Maggi")
        .description("Documentation for Maggi APIs")
        .version("1.0")
        .termsOfServiceUrl("https://www.arjunsk.com/maggi")
        .contact(new Contact("Arjun SK", "https://www.arjunsk.com/", "admin@arjunsk.com"))
        .build();
  }
}
