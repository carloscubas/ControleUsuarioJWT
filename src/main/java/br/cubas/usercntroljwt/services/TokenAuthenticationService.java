package br.cubas.usercntroljwt.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenAuthenticationService {

    private static final long EXPIRATIONTIME = 864000000;
    private static final String SECRET = "MySecreteApp";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";
    
	public static String addAuthentication(Authentication auth) {
		Claims claims = Jwts.claims().setSubject(auth.getName());
		claims.put("roles", auth.getAuthorities());

		String JWT = Jwts.builder().setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String token = TOKEN_PREFIX + " " + JWT;
		return token;
	}

    public static void addAuthentication(HttpServletResponse res, Authentication auth) {
    	
   	 Claims claims = Jwts.claims().setSubject(auth.getName());
     claims.put("roles", auth.getAuthorities());
    	
        String JWT = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String token = TOKEN_PREFIX + " " + JWT;
        res.addHeader(HEADER_STRING, token);

        try {
            res.getOutputStream().print(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Authentication getByToken(String token) {
    	
    	Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    	
    	 Claims claims = Jwts.parser()
                 .setSigningKey(SECRET)
                 .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                 .getBody();
    	 
		List<String> roles = (ArrayList) claims.get("roles");
		for (Object role : roles) {
			HashMap<String, String> map = (HashMap<String, String>) role;
			List<String> list = new ArrayList<>();
			list.addAll(map.values());
			for (String item : list) {
				grantedAuthorities.add(new SimpleGrantedAuthority(item));
			}

		}
    	
        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return user != null ? new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities) : null;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            return getByToken(token);
        }
        return null;
    }
}