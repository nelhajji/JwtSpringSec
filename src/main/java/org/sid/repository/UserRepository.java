package org.sid.repository;

import java.util.Optional;

import org.sid.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByUserName(String userName);

}
