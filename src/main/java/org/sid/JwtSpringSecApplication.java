package org.sid;

import java.util.HashSet;
import java.util.Set;

import org.sid.model.Role;
import org.sid.model.RoleName;
import org.sid.model.User;
import org.sid.repository.RoleRepository;
import org.sid.service.roleservice.RoleService;
import org.sid.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JwtSpringSecApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JwtSpringSecApplication.class);
    }
	

	@Override
	public void run(String... args) throws Exception {
		
		// Preparation of roles
		Role roleUser = new Role(RoleName.ROLE_USER);
		Role rolePM = new Role(RoleName.ROLE_PM);
		Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
		
		// Save roles
		roleService.saveRole(roleUser);
		roleService.saveRole(rolePM);
		roleService.saveRole(roleAdmin);
		
		//Preparation of users
		User user = new User("Nouaman","nelhajji","nouamanelhajji@gmail.com","nouaelha");
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
		user.setRoles(roles);
		userService.saveOrUpdateUser(user);
		
	}

}
