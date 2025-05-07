package com.wimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
	  private int userId;
	  private String userName;
	  private String passwordHash;
	  private String email;
	  String role;

}
