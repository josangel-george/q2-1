package com.red;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

/**
 * Class to create beans needed for MongoDB.
 * 
 * @author josangel.george
 * @since initial
 * */
@Configuration
@PropertySource("classpath:application.properties")
@EnableMongoRepositories
@EnableConfigurationProperties(MongoProperties.class)
public class MongoConfig{
	
	@Autowired
	Environment env;
	
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception{
		
		String host = env.getProperty("spring.data.mongodb.host");
		String port = env.getProperty("spring.data.mongodb.port");
		String db   = env.getProperty("spring.data.mongodb.database");
		
		int portInt = Integer.parseInt(port);
		return new SimpleMongoDbFactory(new MongoClient(host, portInt), db);
	}
	
	@Bean
	public MongoTemplate mongoTemplate() throws Exception{
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
}
