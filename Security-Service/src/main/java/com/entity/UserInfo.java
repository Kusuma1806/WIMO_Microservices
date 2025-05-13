package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	@NotBlank
	@Size(min = 3, max = 20, message = "user name should be in the range of 3-20")
	private String name;
	@Email(message="Give proper email")
	private String email;
	@NotBlank
	@Size(min = 8, message = "minimum length of the password should be 8")
	private String password;
	@NotBlank(message="role should be either  user or admin")
	@Pattern(regexp = "^(USER|ADMIN)$", message = "Value must be either 'USER' or 'ADMIN'")
	private String roles;
	public UserInfo( String name,String email, String password, String roles) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	

}
