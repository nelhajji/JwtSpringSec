package org.sid;

import org.sid.model.Role;
import org.sid.model.RoleName;
import org.sid.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtSpringSecApplication implements CommandLineRunner{
	
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Preparation of roles
		Role roleUser = new Role(RoleName.ROLE_USER);
		Role rolePM = new Role(RoleName.ROLE_PM);
		Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
		
		// Save roles
		roleRepository.save(roleUser);
		roleRepository.save(rolePM);
		roleRepository.save(roleAdmin);
		
	}

}
