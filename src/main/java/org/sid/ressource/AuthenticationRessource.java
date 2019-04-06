package org.sid.ressource;

import javax.validation.Valid;

import org.sid.message.request.LoginDTO;
import org.sid.message.response.JwtDTO;
import org.sid.security.token.TokenProvider;
import org.sid.security.util.JWTProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins="*", maxAge=3600)
@Controller
@RequestMapping("/api/auth")
public class AuthenticationRessource {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTProperties jWTProperties;
	
	@Autowired
	TokenProvider  tokenProvider;
	
	@PostMapping("/signIn")
	public ResponseEntity<?> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		String token = tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JwtDTO(token, userDetails.getUsername(), userDetails.getAuthorities()));
		
	}
	
	@GetMapping("/getPro")
	public ResponseEntity<String> getProperties() {
		return ResponseEntity.ok(jWTProperties.getJwtExpiration() +  " ***** " + jWTProperties.getJwtSecret());
	}

}
