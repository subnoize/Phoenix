package com.subnoize.phoenix.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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

	// https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html

	@PostMapping(path = "/changePassword")
	public ModelAndView changePassword(SecurityContextHolderAwareRequestWrapper requestWrapper, User user, String password, String currentPassword, String confirmPassword) throws Exception {
		ModelAndView mav = new ModelAndView();
		if (requestWrapper.isUserInRole("ADMIN") || requestWrapper.isUserInRole("USER")) {
			if (password == null || currentPassword == null || confirmPassword == null) {
				mav.setViewName("/error.html");
				user.setPassword("*****************");
				mav.addObject("user", user);
				mav.addObject("error_message", "Form field not filled out.");
			}
			else if (password == confirmPassword) {
				mav.setViewName("/error.html");
				user.setPassword("*****************");
				mav.addObject("user", user);
				mav.addObject("error_message", ".");
			}
			else {
				mav.setViewName("/services/home.html");
				user.setUsername(null);
				user.setDateCreated(null);
				user.setEnabled((Boolean) null);
				user.setExpired((Boolean) null);
				user.setLocked((Boolean) null);
				userDAO.updateNonNull(user);
			}
		}
		return mav;
	}

}