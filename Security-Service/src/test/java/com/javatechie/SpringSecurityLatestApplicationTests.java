package com.javatechie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.entity.UserInfo;
import com.exceptions.UserRoleNotFound;
import com.repository.UserInfoRepository;
import com.service.UserService;

@SpringBootTest
class SpringSecurityLatestApplicationTests {

	

	    @Mock
	    private UserInfoRepository repository;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    @InjectMocks
	    private UserService userService;

	    @Test
	     void testAddUser_NewUser() {
	    	UserInfo userInfo=new UserInfo("Kusuma","1234","kusuma@gmail.com","USER");
	        when(repository.findByName(userInfo.getName())).thenReturn(Optional.empty());
	        when(passwordEncoder.encode(userInfo.getPassword())).thenReturn("encodedPassword");

	        String result = userService.addUser(userInfo);

	        verify(repository).save(userInfo);
	        assertEquals("Registration Successfully ", result);
	    }

	    @Test
	     void testAddUser_ExistingUser() {
	    	UserInfo userInfo=new UserInfo("Kusuma","1234","kusuma@gmail.com","USER");
	        when(repository.findByName(userInfo.getName())).thenReturn(Optional.of(userInfo));

	        String result = userService.addUser(userInfo);

	        assertEquals("This UserName is Already Registered.", result);
	    }

	    @Test
	     void testGetRoles_UserExists() {
	    	UserInfo userInfo=new UserInfo("Kusuma","1234","kusuma@gmail.com","USER");
	        when(repository.findByName(userInfo.getName())).thenReturn(Optional.of(userInfo));
	        userInfo.setRoles("USER");

	        String result = userService.getRoles(userInfo.getName());

	        assertEquals("USER", result);
	    }

	    @Test
	     void testGetRoles_UserNotFound() {
	    	UserInfo userInfo=new UserInfo("Kusuma","1234","kusuma@gmail.com","USER");
	        when(repository.findByName(userInfo.getName())).thenReturn(Optional.empty());

	        String result = userService.getRoles(userInfo.getName());

	        assertEquals("Not Found", result);
	    }

	    @Test
	     void testRemoveUser() {
	    	int id=1;
	        Mockito.doNothing().when(repository).deleteById(id);

	        String result = userService.removeUser(id);
	        assertEquals("User Deleted!!!", result);
	    }

	    @Test
	     void testGetUserById_UserExists() throws UserRoleNotFound {
	    	int id=1;
	    	UserInfo userInfo=new UserInfo("Kusuma","1234","kusuma@gmail.com","USER");
	        when(repository.findById(id)).thenReturn(Optional.of(userInfo));

	        UserInfo result = userService.getUserById(id);

	        assertEquals(userInfo, result);
	    }

	    @Test
	     void testGetUserById_UserNotFound() {
	    	int id=1;
	        when(repository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(UserRoleNotFound.class, () -> {
	            userService.getUserById(id);
	        });
	    }

	    @Test
	     void testGetAllUsers() {
	    	List<UserInfo> users=Arrays.asList(new UserInfo("Kusuma","1234","kusuma@gmail.com","USER"),new UserInfo("Kusuma","1234","kusuma@gmail.com","USER"));
	        Mockito.when(repository.findAll()).thenReturn(users);

	        List<UserInfo> result = userService.getAllUsers();

	        assertEquals(users, result);
	    }
	}
