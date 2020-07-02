package com.subnoize.phoenix.aws.dynamodb;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<Long, LocalDateTime> {

	@Override
	public Long convert(final LocalDateTime time) {

		return time.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
	}

	@Override
	public LocalDateTime unconvert(final Long longValue) {
		
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue), ZoneOffset.UTC);
	}
}
