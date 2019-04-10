package org.sid.resource;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sid.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations="classpath:application.properties")
public class UserResourceTestIntegration {
	
	private final static Logger logger = LoggerFactory.getLogger(UserResourceTestIntegration.class);
	
	@Autowired
	TestRestTemplate restTemplate;
	
	//Permet d'utiliser le port local de serveur
	@LocalServerPort
	private int port;
	
	private static final String url = "http://localhost:";
	
	private String getUriWithPort(String uri) {
		String uriWithPort = url + port + uri;
		return uriWithPort;
	}
	
	@Test
	public void test_findAllUser_OK() {
		ResponseEntity<Object> response = restTemplate.getForEntity(getUriWithPort("/jwt-spring-sec/user/users"), Object.class);
		Collection<User> users = (Collection<User>) response.getBody();
		
		assertEquals(2, users.size());
	}
	

}
