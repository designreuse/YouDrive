package com.youdrive.interfaces;

import java.util.ArrayList;
import java.util.Date;

import com.youdrive.models.Reservation;

public interface IReservationManager {

	/**
	 * Returns the # of active reservations that a given vehicle ID is involved in
	 * within the date range provided
	 * @param vehicleID
	 * @return
	 */
	int getVehicleReservationRangeCount(int vehicleID,java.util.Date startDate, java.util.Date stopDate);

	int getLocationReservationRangeCount(int vehicleID, Date startDate, Date stopDate);
	
	int getReservationsByLocationAndVehicleCount(int locationID, int vehicleID, Date startDate, Date stopDate);

	ArrayList<Reservation> getAllReservationsInRange(Date startDate, Date stopDate);

	ArrayList<Reservation> getReservationsInRangeByVehicle(int vehicleID,
			Date startDate, Date stopDate);

	ArrayList<Reservation> getReservationsInRangeByLocation(int locationID,
			Date startDate, Date stopDate);



	ArrayList<Reservation> getReservationsByLocationAndVehicle(int locationID,
			int vehicleID, Date startDate, Date stopDate);

	int checkIfLocationInUse(int locationID);
}
