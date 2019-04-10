package org.sid.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sid.exception.BusinessResourceException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/*")
@CrossOrigin(origins="*", maxAge = 3600)
public class UserResource {
	
	public static final Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@PostMapping("/users")
	@Transactional
	public ResponseEntity<User> saveUser(@RequestBody User user){
		User userSave;
		try {
			Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
			Set<Role> roles = new HashSet<Role>();
			roles.add(roleAdmin);
			user.setRoles(roles);
			
			// Provisoire
			roles = extractRole(user.getRoles(),roleService.getAllRolesStream());
			user.setRoles(roles);
			
			logger.debug("Save user");
			userSave = userService.saveOrUpdateUser(user);
		
		}catch(Exception e) {
			throw new BusinessResourceException("Error save", "Error when we save user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<User>(userSave, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/users")
	@Transactional
	public ResponseEntity<User> updateUser(@RequestBody User user){
		
		Optional<User> userDB = userService.getUserById(user.getId());
 		
		if(!userDB.isPresent()) {
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		
		logger.debug("update user");
		User userUpdated = userService.saveOrUpdateUser(user);
		
		return new ResponseEntity<User>(userUpdated, HttpStatus.OK);
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<Collection<User>> getAllUsers(){
		Collection<User> users = userService.getAllUsers();
		return new ResponseEntity<Collection<User>>(users, HttpStatus.FOUND);
		
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> findUserById(@PathVariable Long userId) throws BusinessResourceException{
		Optional<User> user = userService.getUserById(userId);
		if(!user.isPresent()) {
			throw new BusinessResourceException("404", "User not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.FOUND);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws BusinessResourceException{
		try {
			userService.deletUser(userService.getUserById(userId).get());
		}catch(Exception e) {
			throw new BusinessResourceException("Erreur sup", "Error when we delete user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.GONE);
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
