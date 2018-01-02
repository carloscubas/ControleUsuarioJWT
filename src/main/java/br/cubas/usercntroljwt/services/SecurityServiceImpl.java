package br.cubas.usercntroljwt.services;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public String login(String username, String password) throws AuthenticationException {
		
		String token = null;

		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, password, userDetails.getAuthorities());

			Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

			if (auth.isAuthenticated()) {
				
				token = TokenAuthenticationService.addAuthentication(auth);

			}else {
				throw new AuthenticationException();
			}

		} catch (BadCredentialsException e) {

			throw new AuthenticationException();
		}
		
		return token;
	}

	@Override
	public Authentication findLoggedInUser(String token) {
		Authentication autentication = TokenAuthenticationService.getByToken(token);
		return autentication;
	}

}