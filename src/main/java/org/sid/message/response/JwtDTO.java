package org.sid.message.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtDTO {
	
	private String token;
	private String type="Bearer";
	private String userName;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public JwtDTO(String token, String userName, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.userName = userName;
		this.authorities = authorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
