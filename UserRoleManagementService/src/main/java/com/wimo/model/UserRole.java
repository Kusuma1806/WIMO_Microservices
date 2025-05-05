package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRole {
	  @Id
	  @Positive(message="Id shouldn't be zero or negative value")
	  private int userId;
	  @NotBlank
	  @Size(min = 3, max = 20,message="user name should be in the range of 3-20")
	  private String userName;
	  @NotBlank
	  @Size(min = 8,message="minimum length of the password should be 8")
	  private String passwordHash;
	  @NotBlank
	  @Email(message = "Enter a valid mail id")
	  private String email;
	  @NotBlank(message="role should be either  user or admin")
	  String role;



}

