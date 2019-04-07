package org.sid.service.userservice;

import java.util.Collection;
import java.util.Optional;

import org.sid.model.User;

public interface UserService {

	Collection<User> getAllUsers();
	
	Optional<User> getUserById(long id);
	
	Optional<User> getUserByUserName(String userName);
	
	User saveOrUpdateUser(User user);
	
	void deletUser(User user);
}
