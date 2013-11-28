package com.youdrive.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import org.junit.Test;

import com.youdrive.helpers.ReservationDAO;
import com.youdrive.interfaces.IReservationManager;
import com.youdrive.models.Reservation;
import com.youdrive.models.ReservationStatus;

public class IReservationManagerTest {
	private static IReservationManager irm = new ReservationDAO();

	@Test
	public void userReservations(){
		ArrayList<Reservation> r = irm.getUserReservations(5);
		assertEquals("userreservations",1,r.size());
	}

	@Test
	public void testgetReservations(){
		Reservation r = irm.getReservation(13);
		assertEquals("getReservations",2,r.getReservationStatusList().size());
	}

	@Test
	public void testUserReservations(){
		ArrayList<Reservation> r = irm.getUserReservations(6);
		for (Reservation a : r){
			if (a.getId() == 14){
				for (ReservationStatus rs : a.getReservationStatusList()){
					System.out.println("Status: " + rs.getReservationStatus());
				}
			}
		}
		assertEquals("getReservations",14,r.size());
	}
	
	@Test 
	public void testisLocationInUser(){
		int count = irm.checkIfLocationInUse(4);
		assertEquals("testisLocationInUser",2,count);
	}

}
