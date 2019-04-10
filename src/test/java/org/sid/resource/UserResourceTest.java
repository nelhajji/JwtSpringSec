package org.sid.resource;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.sid.exception.BusinessResourceException;
import org.sid.model.Role;
import org.sid.model.RoleName;
import org.sid.model.User;
import org.sid.service.roleservice.RoleService;
import org.sid.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
public class UserResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@MockBean
	RoleService roleService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() {
		// Prepare Test
		User user = new User("Nouaman","nelhajji","nouamanelhajji@gmail.com","nouaelha");
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(RoleName.ROLE_USER));
		user.setRoles(roles);
		user.setId(1L);
		List<User> users = new ArrayList<User>();
		users.add(user);
		
		User mockUser = new User("John", "John", "john@gmail.com", "azedsq20");
		mockUser.setRoles(roles);
		mockUser.setId(2L);
		
		when(userService.getAllUsers()).thenReturn(users);
		when(userService.saveOrUpdateUser(any(User.class))).thenReturn(mockUser);
		when(userService.getUserById(any(Long.class))).thenReturn(Optional.of(mockUser));
		
	}
	
	
	@Test
	public void test_findAllUsers_OK() throws Exception {
		mockMvc.perform(get("/user/users")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$[0].id",is(1)))
				.andExpect(jsonPath("$[0].name",is("Nouaman")))
				.andReturn();
		// Verify appel of service getAllUsers
		verify(userService).getAllUsers();
		
	}
	
	@Test
	public void test_saveUser_Ok() throws JsonProcessingException, Exception {
		User userDTO = new User("John", "John", "john@gmail.com", "azedsq20");
		mockMvc.perform(post("/user/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id",is(2)))
				.andExpect(jsonPath("$.name",is("John")))
				.andReturn();
		
		// verify appel of service saveOrUpdateUser
		verify(userService).saveOrUpdateUser(any(User.class));
	}
	
	@Test
	public void test_findUserById_Exist() throws Exception {
		mockMvc.perform(get("/user/{userId}", new Long(1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(302));
	}
	
	@Test
	public void test_deleteUser_Ok() throws Exception {
		mockMvc.perform(delete("/user/{userId}", new Long(1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isGone());
		
		verify(userService).deletUser(any(User.class));
			
	}
	
	

}
