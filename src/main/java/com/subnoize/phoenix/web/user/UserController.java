package com.subnoize.phoenix.web.user;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder passwdenc;
	
	// https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html

	@PostMapping(path = "/changePassword")
	public ModelAndView changePassword(final Principal principal, String password, String currentPassword,
			String confirmPassword) throws Exception {
		ModelAndView mav = new ModelAndView();

		principal.getName();

		if (principal.equals(null)) {
			mav.setViewName("/login.html");
		}

		else {

			if (StringUtils.isBlank(confirmPassword) || StringUtils.isBlank(password) || StringUtils.isBlank(currentPassword)) {
				mav.setViewName("/error.html");
				mav.addObject("error_message", "Form field not filled out.");
			} else if (!password.equals(confirmPassword)) {
				mav.setViewName("/error.html");
				mav.addObject("error_message", "Password and repeat password do not match.");
			} else {
				mav.setViewName("/services/home.html");
				User user = userDAO.retrieve(principal.getName());
				if (user.getPassword().equals(passwdenc.encode(currentPassword))) {
					
				}
				else {
					mav.setViewName("/error.html");
					mav.addObject("error_message", "Incorrect Password.");
				}
				userDAO.updateNonNull(user);
			}

		}
		return mav;
	}

}