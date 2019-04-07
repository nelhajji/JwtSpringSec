package org.sid.service.roleservice;

import java.util.Collection;
import java.util.stream.Stream;

import org.sid.model.Role;
import org.sid.model.RoleName;

public interface RoleService {
	
	Role findByRoleName(RoleName roleName);
	
	Collection<Role> getAllRoles();
	
	Stream<Role> getAllRolesStream();
	
	Role saveRole(Role role);
}
