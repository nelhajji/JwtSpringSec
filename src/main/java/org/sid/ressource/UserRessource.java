package org.sid.ressource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sid.model.Role;
import org.sid.model.RoleName;
import org.sid.model.User;
import org.sid.service.roleservice.RoleService;
import org.sid.service.userservice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/*")
@CrossOrigin(origins="*", maxAge = 3600)
public class UserRessource {
	
	public static final Logger logger = LoggerFactory.getLogger(UserRessource.class);

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@PostMapping("/users")
	@Transactional
	public ResponseEntity<User> saveUser(@RequestBody User user){
		
		Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleAdmin);
		user.setRoles(roles);
		
		// Provisoire
		roles = extractRole(user.getRoles(),roleService.getAllRolesStream());
		user.setRoles(roles);
		
		logger.debug("Save user");
		User userSave = userService.saveOrUpdateUser(user);
		
		return new ResponseEntity<User>(userSave, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> findUserById(@PathVariable Long userId){
		Optional<User> user = userService.getUserById(userId);
		return new ResponseEntity<User>(user.get(), HttpStatus.FOUND);
	}
	
	@GetMapping("/users")
	public ResponseEntity<Collection<User>> getAllUsers(){
		Collection<User> users = userService.getAllUsers();
		return new ResponseEntity<Collection<User>>(users, HttpStatus.FOUND);
		
	}
	
	/*
	 * Extract Roles
	 * */
	private Set<Role> extractRole(Set<Role> roleSetFromUser, Stream<Role> roleSetFromDB){
		Collection<RoleName> rolesName = roleSetFromUser.stream().map(Role::getRoleName).collect(Collectors.toSet());
		Set<Role> rolesSet= roleSetFromDB.filter(
				role -> rolesName.contains(role.getRoleName()))
				.collect(Collectors.toSet());
		return rolesSet;
	}
}
