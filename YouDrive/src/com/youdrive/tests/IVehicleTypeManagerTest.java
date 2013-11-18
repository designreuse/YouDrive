package com.youdrive.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.VehicleTypeDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IVehicleTypeManager;

public class IVehicleTypeManagerTest {
	private static IVehicleTypeManager ivtm = new VehicleTypeDAO();

	@Test
	public void testDeleteVehicleType() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddVehicleType() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllVehicleTypes() {
		assertEquals("isTypeInUse",5,ivtm.getAllVehicleTypes().size());
	}

	@Test
	public void testGetVehicleType() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateVehicleType() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsTypeInUse() {
		boolean type = ivtm.isTypeInUse("Regular");
		assertEquals("Regular is in use",true,type);
		type = ivtm.isTypeInUse("Regular Car");
		assertEquals("Regular Car is not use",false,type);
	}

}
