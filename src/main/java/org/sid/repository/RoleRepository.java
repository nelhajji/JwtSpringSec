package org.sid.repository;

import java.util.stream.Stream;

import org.sid.model.Role;
import org.sid.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	public Role findByRoleName(RoleName roleName);
	
	@Query("select role from Role role")
	Stream<Role> getAllRolesStream();

}
