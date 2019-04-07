package org.sid.service.userservice;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.sid.model.User;
import org.sid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Collection<User> getAllUsers() {
		// TODO Auto-generated method stub
		return IteratorUtils.toList(userRepository.findAll().iterator());
	}

	@Override
	public Optional<User> getUserById(long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}

	@Override
	@Transactional(readOnly=false)
	public User saveOrUpdateUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly=false)
	public void deletUser(User user) {
		// TODO Auto-generated method stub
		userRepository.delete(user);
		
	}

}
