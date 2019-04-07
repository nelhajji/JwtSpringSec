package org.sid.ressource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultRessource {
	
	public static final Logger logger = LoggerFactory.getLogger(DefaultRessource.class);
	
	@GetMapping(value = "/")
	public ResponseEntity<String> getProperties() {
		logger.info("Start service...");
		return new ResponseEntity<String>("Response of server"+HttpStatus.OK.name(), HttpStatus.OK);
	}


}
