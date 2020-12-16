package com.subnoize.phoenix.aws.dynamodb;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import io.micrometer.core.instrument.util.StringUtils;

@Repository
public class ProductDAO {

	@Autowired
	private DynamoDBMapper mapper;

	public void create(Product product) {
		if (StringUtils.isNotBlank(product.getName())) {
			product.setDateCreated(LocalDateTime.now());
			mapper.save(product);
		}
	}

}
