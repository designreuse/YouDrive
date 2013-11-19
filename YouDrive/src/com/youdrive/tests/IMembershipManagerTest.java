package com.youdrive.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.youdrive.helpers.MembershipDAO;
import com.youdrive.interfaces.IMembershipManager;

public class IMembershipManagerTest {
	private IMembershipManager imm = new MembershipDAO();

	@Test
	public void testAddMembership() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteMembership() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllMemberships() {
		int count = imm.getAllMemberships().size();
		assertEquals("getAllMemberships",2,count);
	}

}
