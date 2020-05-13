package com.subnoize.phoenix.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.subnoize.phoenix.aws.dynamodb.User;
import com.subnoize.phoenix.aws.dynamodb.UserDAO;
import com.subnoize.phoenix.aws.dynamodb.UserRoles;


/**
 * 
 * @author youca
 *
 */
@Service
public class AppUserDetailService implements UserDetailsService {

	@Autowired
    private UserDAO dao;
 
    @Override
    public UserDetails loadUserByUsername(String username) {
    	if(username == null) {
    		throw new UsernameNotFoundException("EMPTY USERNAME");
    	}
        User user = dao.retrieve(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else if(user.isExpired()) {
        	throw new UsernameNotFoundException(username+" is expired");
        } else if(user.isLocked()) {
        	throw new UsernameNotFoundException(username+" is locked");
        } else if(user.isEnabled()) {
        	UserRoles roles = dao.retrieveRoles(user);
        	List<GrantedAuthority> authorities = new ArrayList<>();
        	for (String role: roles.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        	return new AppUserPrinciple(user,authorities);
        } else {
        	throw new UsernameNotFoundException(username);
        }
    }

}
