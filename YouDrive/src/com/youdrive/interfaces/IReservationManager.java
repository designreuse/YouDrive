package com.youdrive.interfaces;

import java.util.ArrayList;
import java.util.Date;

import com.youdrive.models.Reservation;
import com.youdrive.models.ReservationStatus;

public interface IReservationManager {

	Reservation getReservation(int reservationID);

	ArrayList<Reservation> getUserReservations(int userID);

	int cancelReservation(int reservationID);

	int getCancelledOrReturnedReservationCount(int vehicleID);

	int isVehicleInUse(int vehicleID);

	String getStatus(int reservationID);

	ArrayList<ReservationStatus> getReservationStatus(int reservationID);

	ArrayList<Reservation> getAllReservations(int locationID, int vehicleID);

	ArrayList<Reservation> getVehiclesInUse(int locationID, int vehicleID);

	int checkIfLocationInUse(int locationID);

	int makeReservation(int userID, int locationID, int vehicleID,
			Date startDate, Date stopDate);

	int addReservationStatus(int reservationID, String reservationStatus);


}
