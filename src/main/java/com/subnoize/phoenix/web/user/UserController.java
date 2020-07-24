package com.subnoize.phoenix.web.user;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subnoize.phoenix.aws.dynamodb.User;
import com.subnoize.phoenix.aws.dynamodb.UserDAO;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder passwdenc;

	// https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html

	@PostMapping(path = "/changePassword")
	public ModelAndView changePassword(final Principal principal, String password, String currentPassword,
			String confirmPassword) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		if (principal.equals(null)) {
			mav.setViewName("/login.html");
		}

		else {
			if (StringUtils.isBlank(confirmPassword) || StringUtils.isBlank(password)
					|| StringUtils.isBlank(currentPassword)) {
				mav.setViewName("/error.html");
				mav.addObject("error_message", "Form field not filled out.");
			} else if (!password.equals(confirmPassword)) {
				mav.setViewName("/error.html");
				mav.addObject("error_message", "Password and repeat password do not match.");
			} else {
				mav.setViewName("/services/home.html");
				User user = userDAO.retrieve(principal.getName());
				if (passwdenc.matches(currentPassword, user.getPassword())) {
					
					userDAO.updatePassword(user,confirmPassword);
					mav.setViewName("/services/home.html");
					
					logger.info("{} changed their password.", principal.getName());
				} else {
					mav.setViewName("/error.html");
					mav.addObject("error_message", "Incorrect Password.");
					
					logger.info("{} entered the incorrect password.", principal.getName());
				}
			}

		}
		return mav;
	}

}