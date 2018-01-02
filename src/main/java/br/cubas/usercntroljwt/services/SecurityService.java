package br.cubas.usercntroljwt.services;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.core.Authentication;

public interface SecurityService {
	
	String login(String username, String password)  throws AuthenticationException;
	
	Authentication findLoggedInUser(String token);

}