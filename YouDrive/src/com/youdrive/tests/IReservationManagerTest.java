package com.youdrive.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import org.junit.Test;

import com.youdrive.helpers.ReservationDAO;
import com.youdrive.interfaces.IReservationManager;
import com.youdrive.models.Reservation;

public class IReservationManagerTest {
	private static IReservationManager irm = new ReservationDAO();

	@Test
	public void testGetVehicleReservationRangeCount() {
		Calendar cal = Calendar.getInstance();
		Date badStart = cal.getTime();
		cal.add(Calendar.MONTH, -2);
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 5);
		Date end = cal.getTime();
		int count = irm.getVehicleReservationRangeCount(1, start,end);
		assertEquals("testGetVehicleReservationRangeCount",1,count);
	}

	@Test
	public void testGetLocationReservationRangeCount() {
		Calendar cal = Calendar.getInstance();
		Date badStart = cal.getTime();
		cal.add(Calendar.MONTH, -2);
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 5);
		Date end = cal.getTime();
		int count = irm.getLocationReservationRangeCount(1, start,end);
		assertEquals("testGetVehicleReservationRangeCount",1,count);
	}
	
	@Test
	public void vehicleByRange() {
		Calendar cal = Calendar.getInstance();
		Date badStart = cal.getTime();
		cal.add(Calendar.MONTH, -2);
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 5);
		Date end = cal.getTime();
		ArrayList<Reservation> res = irm.getReservationsInRangeByVehicle(1,start,end);
		assertEquals("testGetVehicleReservationRangeCount",1,res.size());
	}
	
	
	@Test
	public void locationByRange() {
		Calendar cal = Calendar.getInstance();
		Date badStart = cal.getTime();
		cal.add(Calendar.MONTH, -2);
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 5);
		Date end = cal.getTime();
		ArrayList<Reservation> res = irm.getReservationsInRangeByLocation(1,start,end);
		assertEquals("testGetVehicleReservationRangeCount",1,res.size());
	}
	
	@Test
	public void allByRange() {
		Calendar cal = Calendar.getInstance();
		Date badStart = cal.getTime();
		cal.add(Calendar.MONTH, -2);
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 5);
		Date end = cal.getTime();
		ArrayList<Reservation> res = irm.getAllReservationsInRange(start,end);
		assertEquals("testGetVehicleReservationRangeCount",1,res.size());
	}
	
	@Test
	public void getLocationAndVehicleByRange() {
		Calendar cal = Calendar.getInstance();
		Date badStart = cal.getTime();
		cal.add(Calendar.MONTH, -2);
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 5);
		Date end = cal.getTime();
		ArrayList<Reservation> res = irm.getReservationsByLocationAndVehicle(1, 1,start,end);
		assertEquals("testGetVehicleReservationRangeCount",1,res.size());
	}
	
	@Test
	public void getLocationAndVehicleByRangeCount() {
		Calendar cal = Calendar.getInstance();
		Date badStart = cal.getTime();
		cal.add(Calendar.MONTH, -2);
		Date start = cal.getTime();
		cal.add(Calendar.MONTH, 5);
		Date end = cal.getTime();
		int count = irm.getReservationsByLocationAndVehicleCount(1, 1,start,end);
		assertEquals("testGetVehicleReservationRangeCount",1,count);
	}
}
