package com.upgrade.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	@Bean
	public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2)  
				.apiInfo(userApiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())              
				.paths(PathSelectors.any())                          
				.build();                                           
	}

	private ApiInfo userApiInfo() {
		return new ApiInfoBuilder()
				.title("Campsite REST API")
				.description("\"Spring Boot REST API for Booking Campsite\"")
				.version("1.0.0")
				.contact(new Contact("Agustin Lacomba", "https://www.linkedin.com/in/agustin-lacomba-06396834/", "agustinunlp@gmail.com"))
				.build();
	}
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}


