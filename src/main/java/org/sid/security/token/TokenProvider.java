package org.sid.security.token;

import java.util.Date;

import org.sid.security.services.UserDetailsImpl;
import org.sid.security.util.JWTProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	
	@Autowired
	JWTProperties jWTProperties;
	
	
	public String generateToken(Authentication authentication) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return Jwts
				.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + Integer.parseInt(jWTProperties.getJwtExpiration())*1000))
                .signWith(SignatureAlgorithm.HS512, jWTProperties.getJwtSecret())
                .compact();
	}
	
	

}
