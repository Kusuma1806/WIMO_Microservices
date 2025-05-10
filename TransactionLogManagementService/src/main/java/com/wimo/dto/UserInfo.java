package com.wimo.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	@Id
	 private int id;
	  private String name;
	  private String email;
	  private String password;
	  private String roles;
	  
	  public UserInfo( String name,String email, String password, String roles) {
			super();
			this.name = name;
			this.email = email;
			this.password = password;
			this.roles = roles;
		}
		
}
