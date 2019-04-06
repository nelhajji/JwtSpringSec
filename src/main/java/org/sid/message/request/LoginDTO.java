package org.sid.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDTO {
	
	@NotBlank
	@Size(min=3, max=60)
	private String userName;
	
	@NotBlank
	@Size(min=5, max=60)
	private String password;

}
