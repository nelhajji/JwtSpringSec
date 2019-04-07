package org.sid.service.roleservice;

import java.util.Collection;
import java.util.stream.Stream;

import org.apache.commons.collections4.IteratorUtils;
import org.sid.model.Role;
import org.sid.model.RoleName;
import org.sid.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role findByRoleName(RoleName roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(roleName);
	}

	@Override
	public Collection<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return IteratorUtils.toList(roleRepository.findAll().iterator());
	}

	@Override
	public Stream<Role> getAllRolesStream() {
		// TODO Auto-generated method stub
		return roleRepository.getAllRolesStream();
	}

	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

}
