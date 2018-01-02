package br.cubas.usercntroljwt.controllers;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.cubas.usercntroljwt.dto.User;
import br.cubas.usercntroljwt.services.SecurityService;
import br.cubas.usercntroljwt.services.TokenAuthenticationService;
import br.cubas.usercntroljwt.services.UserService;

@RestController
public class UserControllers {
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
				
		String token;
		
		try {
			token = securityService.login(user.getUsername(), user.getPassword());
			
			if(token == null) {
				return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
			}
			
		}  catch (AuthenticationException e) {
			
			return new ResponseEntity<String>(HttpStatus.PRECONDITION_FAILED);

		}
		
		return new ResponseEntity<String>(token, HttpStatus.OK);
		
	}
	
	@GetMapping("/userinformation")
	public ResponseEntity<Authentication> getUserInformation(HttpServletRequest request) {
		
		Authentication authentication;
		
		try {
			
			authentication = TokenAuthenticationService.getAuthentication(request);
			
			if(authentication == null) {
				return new ResponseEntity<Authentication>(HttpStatus.PRECONDITION_FAILED);
			}
			
		}  catch (Exception e) {
			
			return new ResponseEntity<Authentication>(HttpStatus.PRECONDITION_FAILED);

		}
		
		return new ResponseEntity<Authentication>(authentication, HttpStatus.OK);
		
	}
	
	@PostMapping("/registration")
	public ResponseEntity<String> registration(@RequestBody User user) {
		
		String token;
		
		try {
			
			userService.save(user);
			
			token = securityService.login(user.getUsername(), user.getPassword());
			
			if(token == null) {
				return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
			}
			
		}  catch (AuthenticationException e) {
			
			return new ResponseEntity<String>(HttpStatus.PRECONDITION_FAILED);

		}
		
		return new ResponseEntity<String>(token, HttpStatus.OK);
		
	}
	
}
