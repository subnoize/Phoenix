package com.subnoize.phoenix.aws.dynamodb;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName = "snz-user-roles")
public class UserRoles {

	@JsonProperty("username")
	@DynamoDBHashKey(attributeName = "username")
	private String username;

	@JsonProperty("authorities")
	private Set<String> authorities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

}
