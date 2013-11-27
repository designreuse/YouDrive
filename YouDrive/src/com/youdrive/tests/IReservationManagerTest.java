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
	public void userReservations(){
		ArrayList<Reservation> r = irm.getUserReservations(5);
		assertEquals("userreservations",1,r.size());
	}
}
