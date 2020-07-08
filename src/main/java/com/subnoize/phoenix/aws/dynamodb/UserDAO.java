package com.subnoize.phoenix.aws.dynamodb;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * 
 * @author youca
 *
 */
@Repository
@Lazy
public class UserDAO {

	@Autowired
	private DynamoDBMapper mapper;

	@Autowired
	private PasswordEncoder passwdenc;

	public void create(User user) {
		String enc = passwdenc.encode(user.getPassword());
		user.setPassword(enc);
		user.setExpired(false);
		user.setDateCreated(LocalDateTime.now());
		mapper.save(user);

	}
	
	public void updatePassword(User user, String password) {
		String enc = passwdenc.encode(password);
		user.setPassword(enc);
		mapper.save(user);
	}

	public User retrieve(String username) {
		if (StringUtils.isEmpty(username))
			return null;
		return mapper.load(User.class, username.toLowerCase());
	}

	public UserRoles retrieveRoles(User user) {
		return mapper.load(UserRoles.class, user.getUsername());
	}

	public void addUserRole(UserRoles userRole) {
		mapper.save(userRole);
	}

	public PaginatedScanList<User> scanUserTable() {
		return mapper.scan(User.class, new DynamoDBScanExpression());
	}

	public void update(User user) {
		mapper.save(user);
	}

	public void updateNonNull(User user) {
		mapper.save(user,
				new DynamoDBMapperConfig.Builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build());
	}

	public void delete(User user) {
		mapper.delete(user);
	}

	public PaginatedScanList<User> listUsersByUsername(String username) {

		Map<String, AttributeValue> eav = new HashMap<>();
		eav.put(":username", new AttributeValue().withS(username));
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("begins_with(username, :username)").withExpressionAttributeValues(eav);

		PaginatedScanList<User> paginatedList = mapper.scan(User.class, scanExpression);

		paginatedList.forEach(u -> System.out.println(u.getUsername()));

		return paginatedList;
	}

}