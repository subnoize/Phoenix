package com.subnoize.phoenix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * 
 * @author youca
 *
 */
@Configuration
public class AWSConfig {

	/**
	 * 
	 * @return
	 */
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.defaultClient();

	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	@Bean
	public DynamoDB dynamoDB(AmazonDynamoDB client) {
		return new DynamoDB(client);
	}

	/**
	 * 
	 * @param client
	 * @return
	 */
	@Bean
	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB client) {
		return new DynamoDBMapper(client);
	}
}
