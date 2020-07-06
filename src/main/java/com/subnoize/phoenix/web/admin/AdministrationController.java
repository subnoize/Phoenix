package com.subnoize.phoenix.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.subnoize.phoenix.aws.dynamodb.User;
import com.subnoize.phoenix.aws.dynamodb.UserDAO;
import com.subnoize.phoenix.aws.dynamodb.UserRoles;

@Controller
@RequestMapping(value = "/administration")
public class AdministrationController {

	@Autowired
	private UserDAO userDAO;

	// https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html

	@GetMapping(path = "/userList")
	public ModelAndView getUserTable(SecurityContextHolderAwareRequestWrapper requestWrapper) throws Exception {
		ModelAndView mav = new ModelAndView();
		if (requestWrapper.isUserInRole("ADMIN")) {
			mav.addObject("userList", userDAO.scanUserTable());
		}

		return mav;
	}

	@PostMapping(path = "/searchUserList")
	public ModelAndView searchUserTable(SecurityContextHolderAwareRequestWrapper requestWrapper, String username)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (requestWrapper.isUserInRole("ADMIN")) {
			mav.setViewName("/administration/searchUserEdit.html");
			mav.addObject("userList", userDAO.listUsersByUsername(username));
		}

		return mav;
	}

	@PostMapping(path = "/createUser")
	public ModelAndView createUser(SecurityContextHolderAwareRequestWrapper requestWrapper, User user)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (requestWrapper.isUserInRole("ADMIN")) {
			User tmp = userDAO.retrieve(user.getUsername());
			if (tmp != null) {
				mav.setViewName("/administration/badUser.html");
				user.setPassword("*****************");
				mav.addObject("user", user);
				mav.addObject("error_message", "User already exists.");
			} else {
				mav.setViewName("/administration/setRole.html");
				userDAO.create(user);
				user.setPassword("*****************");
				mav.addObject("user", user);
			}
		}
		return mav;
	}

	@PostMapping(path = "/createUserRole")
	public ModelAndView createUserRole(SecurityContextHolderAwareRequestWrapper requestWrapper, UserRoles userRole)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (requestWrapper.isUserInRole("ADMIN")) {
			mav.setViewName("/administration/home.html");
			userDAO.addUserRole(userRole);
			mav.addObject("user_roles", userRole);
		}
		return mav;
	}

	@PostMapping(path = "/editUserStartup")
	public ModelAndView editUserInit(SecurityContextHolderAwareRequestWrapper requestWrapper, String username)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (requestWrapper.isUserInRole("ADMIN")) {
			mav.setViewName("/administration/userEdit.html");
			User user = userDAO.retrieve(username);
			mav.addObject("user", user);
		}
		return mav;
	}

	@PostMapping(path = "/editUser")
	public ModelAndView editUser(SecurityContextHolderAwareRequestWrapper requestWrapper, User user) throws Exception {
		ModelAndView mav = new ModelAndView();
		if (requestWrapper.isUserInRole("ADMIN")) {
			mav.setViewName("/administration/home.html");
			user.setPassword(null);
			user.setDateCreated(null);
			userDAO.updateNonNull(user);
		}
		return mav;
	}
}
