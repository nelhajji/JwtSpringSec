package org.sid.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;
import lombok.Data;

@Entity
@Table(name="USERS", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"USER_NAME", "EMAIL"
		})
})
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NAME")
	@NotBlank
	@Size(min=3, max=50)
	private String name;
	
	@Column(name="USER_NAME")
	@NotBlank
	@Size(min=3, max=50)
	private String userName;
	
	@NaturalId
	@Column(name="EMAIL")
	@NotBlank
	@Size(min=3, max=50)
	private String email;
	
	@Column(name="PASSWORD")
	@NotBlank
	@Size(min=3, max=50)
	private String password;
	
	@ManyToMany(fetch= FetchType.LAZY)
	@JoinTable(name="USER_ROLES", 
	joinColumns= @JoinColumn(name="USER_ID"),
	inverseJoinColumns= @JoinColumn(name="ROLE_ID"))
	private Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public User(String name,String userName,String email, String password) {
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	
	

}
