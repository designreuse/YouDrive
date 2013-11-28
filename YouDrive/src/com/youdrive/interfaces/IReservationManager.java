package com.youdrive.interfaces;

import java.util.ArrayList;
import java.util.Date;

import com.youdrive.models.Reservation;
import com.youdrive.models.ReservationStatus;

public interface IReservationManager {

	/**
	 * Retrieve a Reservation object from Reservations table 
	 * and populates it with the list of  ReservationStatus objects associated with this reservation from the
	 *  ReservationStatus table.
	 * Returns null if no reservation object exists and a Reservation object otherwise with at least 1 entry 
	 * in the list of ReservationStatus objects
	 * @param reservationID
	 * @return
	 */
	public Reservation getReservation(int reservationID);

	/**
	 * Retrieve all reservations made by the provided user
	 * Each reservation object will contain the list of reservation status objects within.
	 * @param userID
	 * @return ArrayList<Reservation>
	 */
	public ArrayList<Reservation> getUserReservations(int userID);

	/**
	 * Cancelling a reservation means inserting an entry into the ReservationStatus table of "Cancelled"
	 * Returns the id of the new entry if successful or 0/-1 if there were problems.
	 * @param reservationID
	 * @return
	 */
	public int cancelReservation(int reservationID);

	/**
	 * Retrieves the count of Reservations that have been returned. 
	 * Count is 0 if the reservation hasn't been returned or cancelled
	 * @param vehicleID
	 * @return
	 */
	public int getCancelledOrReturnedReservationCount(int vehicleID);

	/**
	 * Simply returns a count of the number of times a vehicleID shows up in Reservations tables
	 * A bit dumb because it doesn't take into account if the vehicle has been returned or cancelled. 
	 * TODO fix this.
	 * @param vehicleID
	 * @return
	 */
	public int isVehicleInUse(int vehicleID);

	public String getStatus(int reservationID);

	public ArrayList<ReservationStatus> getReservationStatus(int reservationID);

	public ArrayList<Reservation> getAllReservations(int locationID, int vehicleID);

	public ArrayList<Reservation> getVehiclesInUse(int locationID, int vehicleID);

	public int checkIfLocationInUse(int locationID);

	public int makeReservation(int userID, int locationID, int vehicleID,
			Date startDate, Date stopDate);

	public int addReservationStatus(int reservationID, String reservationStatus);


}
