package com.youdrive.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.youdrive.helpers.VehicleDAO;
import com.youdrive.interfaces.IVehicleManager;

public class IVehicleManagerTest {
	private IVehicleManager ivm = new VehicleDAO();
	
	@Test
	public void testGetAllVehicles() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVehicle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVehiclesByLocationId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVehiclesByLocationName() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddVehicle() {
		int vehicleID = 0;
		vehicleID = ivm.addVehicle("Lamborghini", "Aventador Coupe",2013, "ABC124356", 10000, "11/3/2013", 1, 1);
		assertEquals("addVehicle",1,vehicleID);
	}

	@Test
	public void testDeleteVehicle() {
		fail("Not yet implemented");
	}
}
