package com.youdrive.tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

import com.youdrive.helpers.UserDAO;
import com.youdrive.interfaces.IUserManager;
import com.youdrive.models.User;

public class IUserManagerTest {
	private static IUserManager ium = new UserDAO();

	@Test
	public void testAddAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUser() {
		/*
		 * int id, String username, String password, String firstName,
			String lastName, String state, String license, String email,
			String address, String ccType, String ccNumber, int ccSecurityCode,
			String ccExpirationDate,  boolean isAdmin,
			Date memberExpiration, int membershipLevel
		 */
		java.util.Date date = Calendar.getInstance().getTime();
		java.sql.Date d = new java.sql.Date(date.getTime());
		User user = new User(9,"test","demo","Test","User","GA","ABC134","demo@example.com","123 Broadway Lane, Athens GA 30602","MasterCard","1234123412341234", 256, "11/2014", false, d,1);
		int userID = ium.addUser(user);
	}

	@Test
	public void testAddAdminUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMembership() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUser() {
		User jane = ium.getUser(1);
		assertEquals("getUser","jane",jane.getUsername());
	}

	@Test
	public void testGetUserByUsername() {
		User jane = ium.getUserByUsername("jane");
		assertEquals("getUser","jane",jane.getUsername());
	}

	@Test
	public void testDeleteMembership() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthenticateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAdminUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsUsernameInUse() {
		boolean inUse = ium.isUsernameInUse("jane");
		assertEquals("Username in use",true,inUse);
		inUse = ium.isUsernameInUse("janoulle");
		assertEquals("Username not in use",false,inUse);
	}

	@Test
	public void testIsEmailInUse() {
		boolean inUse = ium.isEmailInUse("janeullah@gmail.com");
		assertEquals("Email in use",true,inUse);
		inUse = ium.isEmailInUse("jane@janeullah.com");
		assertEquals("Email not in use",false,inUse);
	}

}
