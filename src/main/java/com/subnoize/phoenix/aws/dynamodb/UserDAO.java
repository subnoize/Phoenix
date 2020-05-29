package com.subnoize.phoenix.aws.dynamodb;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;


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
		user.setDateCreated(System.currentTimeMillis());
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

	public void delete(User user) {
		mapper.delete(user);
	}

}