package com.youdrive.interfaces;

import static org.junit.Assert.*;

import org.junit.Test;

import com.youdrive.helpers.VehicleTypeDAO;

public class IVehicleTypeManagerTest {
	private IVehicleTypeManager ivtm = new VehicleTypeDAO();

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddVehicleType(){
		int vehicleTypeID = 0;
		vehicleTypeID = ivtm.addVehicleType("Regular",100.00,500.00);
		assertEquals("addVehicle",1,vehicleTypeID);
	}
}
