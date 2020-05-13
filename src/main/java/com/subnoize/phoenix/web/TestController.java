package com.subnoize.phoenix.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestController {
	
	/**
	 * Basic test if user is actually signed in
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/whoami", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Object>> whoami(final Principal principal) {
		Map<String,Object> response = new HashMap<>();
		response.put("username", principal.getName());
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	
	
}
