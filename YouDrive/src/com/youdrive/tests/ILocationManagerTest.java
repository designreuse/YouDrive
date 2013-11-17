package com.youdrive.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.interfaces.ILocationManager;

public class ILocationManagerTest {
	private static ILocationManager ilm = new LocationDAO();
	private StringBuilder errorCode = new StringBuilder();
	
	@Test
	public void testGetAllLocations() {
		assertEquals("getAllLocations","3",String.valueOf(ilm.getAllLocations().size()));
	}

	@Test
	public void testGetLocationById() {
		assertEquals("getLocationByID","Milledge Location",ilm.getLocationByName("Milledge Location").getName());
	}

	@Test
	public void testGetLocationByName() {
		assertEquals("getLocationByID",1,ilm.getLocationById(1).getId());
	}

	@Test
	public void testAddLocation() {
		int locationID = ilm.addLocation("Locos Location","198 West Campus Rd, Athens GA 30602",250,errorCode);
		assertEquals("getLocationByID",4,locationID);
	}

	@Test
	public void testDeleteLocationById() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteLocationByName() {
		fail("Not yet implemented");
	}

}
