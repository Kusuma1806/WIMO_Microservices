package com.wimo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.wimo.exceptions.UserRoleNotFound;
import com.wimo.model.UserRole;
import com.wimo.repository.UserRoleRepository;
import com.wimo.service.UserRoleServiceImpl;

@SpringBootTest
class UserRoleManagementServiceApplicationTests {

	@Mock
	UserRoleRepository repository;
 
	@InjectMocks
	UserRoleServiceImpl service;

	@Test
	void saveUserRoleTest() {
		UserRole userRole =new UserRole(2,"kusumaaaa","kussuu","raju@gmail.com","USER");
		Mockito.when(repository.save(userRole)).thenReturn(userRole);
		String response = service.saveUser(userRole);
		assertEquals("User Saved!!!", response);
	}
	@Test
	void updateUserRoleTest() {
	UserRole userRole = new UserRole(1,"Kusua","kusma","kusumaraju@gmail.com","admin");
	userRole.setUserId(1);

		Mockito.when(repository.save(userRole)).thenReturn(userRole);
 
		UserRole updatedUser = service.updateUser(userRole);
		assertEquals(userRole, updatedUser);
	}

	@Test
	void removeUserRoleTest() {
		int userId = 1;
 		Mockito.doNothing().when(repository).deleteById(userId);

		String response = service.removeUser(userId);
		assertEquals("User Deleted!!!", response);
	}

	@Test
	void getUserRoleTest() throws UserRoleNotFound {
		int userId = 1;
		UserRole userRole = new UserRole(1,"Kusuma","kusuma","kusumaraju@gmail.com","admin");
		userRole.setUserId(userId);
 
		Mockito.when(repository.findById(userId)).thenReturn(Optional.of(userRole));
 
		UserRole foundUser= service.getUserById(userId);
		assertEquals(userRole, foundUser);
	}

//	@Test
//	void getUserRoleNotFoundTest() {
//		int userId = 1;
//		Mockito.when(repository.findById(userId)).thenReturn(Optional.empty());
// 
//		assertThrows(UserRoleNotFound.class, () -> {
//			service.getUserById(userId);
//		});
//	}

	@Test
	void getAllUsersTest() {
		List<UserRole> userRoles = Arrays.asList(new UserRole(1,"Kusuma","kusuma","kusumaraju@gmail.com","admin"),
			new UserRole(1,"Kusuma","kusuma","kusumaraju@gmail.com","admin"));
 
		Mockito.when(repository.findAll()).thenReturn(userRoles);
 
		List<UserRole> allUserRoles = service.getAllUsers();
		assertEquals(userRoles, allUserRoles);
	}
 
 

}
