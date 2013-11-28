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
	 * @return Reservation
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
	 * @return int
	 */
	public int cancelReservation(int reservationID);

	/**
	 * Retrieves the count of Reservations that have been returned. 
	 * Count is 0 if the reservation hasn't been returned or cancelled
	 * @param vehicleID
	 * @return int
	 */
	public int getCancelledOrReturnedReservationCount(int vehicleID);

	/**
	 * Simply returns a count of the number of times a vehicleID shows up in Reservations tables
	 * A bit dumb because it doesn't take into account if the vehicle has been returned or cancelled. 
	 * TODO fix this.
	 * @param vehicleID
	 * @return int
	 */
	public int isVehicleInUse(int vehicleID);

	/**
	 * Retrieve the reservationStatus from ReservationStatus table based on the reservationID and
	 * where the reservationStatus != "Created"
	 * @param reservationID
	 * @return String
	 */
	public String getCancelledOrReturnedStatus(int reservationID);

	/**
	 * Get list of reservation status objects based on the reservation ID
	 * @param reservationID
	 * @return ArrayList<ReservationStatus>
	 */
	public ArrayList<ReservationStatus> getReservationStatus(int reservationID);

	/**
	 * Get a list of Reservations based on the locationID and vehicleID
	 * Reservations returned do not contain the reservation status objects.
	 * @param locationID
	 * @param vehicleID
	 * @return
	 */
	public ArrayList<Reservation> getAllReservations(int locationID, int vehicleID);

	/**
	 * Get list of Reservations that are active i.e. with status of Created
	 * @param locationID
	 * @param vehicleID
	 * @return ArrayList<Reservation>
	 */
	public ArrayList<Reservation> getVehiclesInUse(int locationID, int vehicleID);

	/**
	 * Returns the number of times this location is in use in active reservations
	 * Returns 0 if not in use.
	 * @param locationID
	 * @return int
	 */
	public int checkIfLocationInUse(int locationID);

	/**
	 * Create an entry into the Reservations table 
	 * Returns the reservation ID or 0 if there was a problem
	 * @param userID
	 * @param locationID
	 * @param vehicleID
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	public int makeReservation(int userID, int locationID, int vehicleID, Date startDate, Date stopDate);

	/**
	 * Creates an entry into the ReservationStatus table
	 * Returns the ReservationStatus ID or 0 if there was a problem
	 * @param reservationID
	 * @param reservationStatus
	 * @return
	 */
	public int addReservationStatus(int reservationID, String reservationStatus);
}
