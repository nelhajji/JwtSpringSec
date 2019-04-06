package org.sid.security.util;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class JWTProperties {
	
	private String jwtSecret;
	
	private String jwtExpiration;

}
