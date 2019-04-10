package org.sid.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sid.model.Role;
import org.sid.model.RoleName;
import org.sid.model.User;
import org.sid.repository.UserRepository;
import org.sid.service.userservice.UserService;
import org.sid.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	@TestConfiguration
	static class UserServiceImplTestContextConfiguration{
		
		@Bean
		public UserService getUserService() {
			return new UserServiceImpl();
		}
		
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
	}
	
	@Autowired
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	User user;
	
	@Before
	public void setUp() {
		user = new User("Nouaman","nelhajji","nouamanelhajji@gmail.com","nouaelha");
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(RoleName.ROLE_USER));
		user.setRoles(roles);
	}
	
	@Test
	public void test_findAllUsers_OK() {
		// Prepare Test
		List<User> users = new ArrayList<User>();
		users.add(user);
		// Mock Repository
		when(userRepository.findAll()).thenReturn(users);
		
		//Then
		Collection<User> usersCollection = userService.getAllUsers();
		
		// Assert
		assertNotNull(usersCollection);
		assertEquals(users.size(), usersCollection.size());
		verify(userRepository).findAll();
	}
	
	@Test
	public void test_saveUser_OK() {
		// Prepare Test
		User userMock = new User("Nouaman","nelhajji","nouamanelhajji@gmail.com","nouaelha");
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(RoleName.ROLE_USER));
		userMock.setRoles(roles);
		userMock.setId((long)1);
		// Mock Repos
		when(userRepository.save((User) any(User.class))).thenReturn(userMock);
		
		User userSave = userService.saveOrUpdateUser(user);
		
		//Assert
		assertNotNull(userSave);
		assertEquals(userMock.getUserName(), userSave.getUserName());
		verify(userRepository).save(any(User.class));
		
	}
	
	@Test
	public void test_deleteUser_OK() {
		// Prepare Test
		User userMock = new User("Nouaman","nelhajji","nouamanelhajji@gmail.com","nouaelha");
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(RoleName.ROLE_USER));
		userMock.setRoles(roles);
		userMock.setId((long)1);
		
		// Mock Repos
		when(userRepository.save((User) any(User.class))).thenReturn(userMock);
		
		User userSave = userService.saveOrUpdateUser(user);
		
		userService.deletUser(userSave);
		
		//Verify: On s'assure que la méthode a été bien appelé
		verify(userRepository).delete((User) any(User.class));
		
	}
	
	
	

}
