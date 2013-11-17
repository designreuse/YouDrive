package com.youdrive.tests;

import static org.junit.Assert.*;

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
		fail("Not yet implemented");
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
	public void testDeleteMembership() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthenticateUser() {
		User p = ium.authenticateUser("jane", "demo");
		assertNotNull(p);
	}

}
