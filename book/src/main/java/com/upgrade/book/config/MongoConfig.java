package com.upgrade.book.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;


@Configuration
@PropertySource(value="classpath:application.properties")
public class MongoConfig{

	@Value( "${spring.data.mongodb.database}" )
	private String database;

	@Value( "${spring.data.mongodb.host}" )
	private String host;

	@Value( "${spring.data.mongodb.port}" )
	private Integer port;
	
	@SuppressWarnings("deprecation")
	@Bean(name = {"campsiteReserveTemplate"})
	public MongoTemplate campsiteReserveTemplate() throws Exception {
        MongoClient mongoClient = new MongoClient(host, port);
		return new MongoTemplate(new SimpleMongoDbFactory(mongoClient,
        		database));
	}	 	
}


