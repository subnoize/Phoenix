package com.subnoize.phoenix.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.subnoize.phoenix.aws.dynamodb.User;
import com.subnoize.phoenix.aws.dynamodb.UserDAO;

@Controller
@RequestMapping(value = "/administration")
public class AdministrationController {

	@Autowired
	private UserDAO userDAO;

	@PostMapping(path = "/createUser")
	public String createUser(SecurityContextHolderAwareRequestWrapper requestWrapper, User user) throws Exception {
		if (requestWrapper.isUserInRole("ADMIN")) {
			User tmp = userDAO.retrieve(user.getUsername());
			if (tmp != null) {
				throw new Exception("User already exists!");
			} else {
				userDAO.create(user);
			}
		}
		return "/administration/admins.html";
	}

}
