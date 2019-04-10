package org.sid.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sid.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
//@ComponentScan(basePackages = { "org.sid" })
public class UserRepositoryTest {
	
	@Autowired
	TestEntityManager testEntityManager;
	
	@Autowired
	UserRepository userRepository;
	User user =  new User("Alia", "Alia", "alia@gmail.com", "alia");
	
	@Before
	public void setUp() {
		
		// On sauvegarde user avant les tests.
		testEntityManager.persist(user);
		testEntityManager.flush();
	}
	
	@Test
	public void test_findAll_OK() {
		// Prepare Test
		Collection<User> users = userRepository.findAll();
		
		// Assert Test
		assertEquals(2, users.size());
	}
	
	@Test
	public void test_findByUserName_OK() {
		// Prepare User
		User user = userRepository.findByUserName("Alia").get();
		
		// Assert Test
		assertEquals("Alia",user.getUserName());
		assertThat("Alia", is(user.getUserName()));
	}
	
	@Test
	public void test_SaveUser_OK() {
		// Prepare Test
		User user =  new User("Jamal", "Jamal", "jamal@gmail.com", "jamal");
		userRepository.save(user);
		Long expect = (long) 3;
		
		//Assert Test
		assertEquals(expect, user.getId());
		
	}
	
	@Test
	public void test_deleteUser_Ok() {
		// Prepare Test
		User user = userRepository.findByUserName("Alia").get();
		userRepository.deleteById(user.getId());
		
		// Assert Test
		assertFalse(userRepository.findByUserName("Alia").isPresent());
	}
	
	
	
}
