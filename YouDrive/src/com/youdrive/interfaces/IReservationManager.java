package com.youdrive.interfaces;

import java.util.ArrayList;
import java.util.Date;

import com.youdrive.models.Reservation;
import com.youdrive.models.ReservationStatus;

public interface IReservationManager {

	/**
	 * Returns the # of active reservations that a given vehicle ID is involved in
	 * within the date range provided
	 * Returns -1 if an exception was thrown
	 * Return 0 if no reservations found but otherwise returns the number
	 * @param vehicleID
	 * @return
	 */
	public int getVehicleReservationRangeCount(int vehicleID,java.util.Date startDate, java.util.Date stopDate);

	/**
	 * Get the count of reservations by location id
	 * Returns -1 if an exception was thrown
	 * Return 0 if no reservations found but otherwise returns the number
	 * @param vehicleID
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	public int getLocationReservationRangeCount(int locationID, Date startDate, Date stopDate);
	
	/**
	 * Get the count of reservations by location and vehicle ids.
	 * Returns -1 if an exception was thrown
	 * Return 0 if no reservations found but otherwise returns the number
	 * @param locationID
	 * @param vehicleID
	 * @param startDate
	 * @param stopDate
	 * @return
	 */
	public int getReservationsByLocationAndVehicleCount(int locationID, int vehicleID, Date startDate, Date stopDate);

	/**
	 * Retrieve a list of reservations within the date range
	 * @param startDate
	 * @param stopDate
	 * @return ArrayList<Reservation>
	 */
	public ArrayList<Reservation> getAllReservationsInRange(Date startDate, Date stopDate);

	/**
	 * Retrieve a list of reservations by a specific vehicle and a date range
	 * List will be empty if nothing is present
	 * @param vehicleID
	 * @param startDate
	 * @param stopDate
	 * @return ArrayList<Reservation>
	 */
	public ArrayList<Reservation> getReservationsInRangeByVehicle(int vehicleID, Date startDate, Date stopDate);

	/**
	 * Retrieve a list of reservations by a specific location and a date range
	 * List will be empty if nothing is present
	 * @param locationID
	 * @param startDate
	 * @param stopDate
	 * @return ArrayList<Reservation>
	 */
	public ArrayList<Reservation> getReservationsInRangeByLocation(int locationID, Date startDate, Date stopDate);

	/**
	 * Retrieve a list of reservations by a specific location and vehicle  and a date range
	 * List will be empty if nothing is present
	 * @param locationID
	 * @param vehicleID
	 * @param startDate
	 * @param stopDate
	 * @return ArrayList<Reservation>
	 */
	public ArrayList<Reservation> getReservationsByLocationAndVehicle(int locationID, int vehicleID, Date startDate, Date stopDate);

	/**
	 * select count(*) from Reservations where locationID = ? AND reservationStart > NOW()
	 * Get count of vehicles with a particular location where reservation pickup time is in the future
	 * Returns -1 if an exception was thrown
	 * Return 0 if no reservations found but otherwise returns the number
	 * @param locationID
	 * @return int
	 */
	public int checkIfLocationInUse(int locationID);

	public ArrayList<Reservation> getAllReturnedReservations();

	public ArrayList<Reservation> getAllCancelledReservations();

	public ArrayList<Reservation> getAllOpenReservations();

	ArrayList<Reservation> getAllReservations();

	ArrayList<Reservation> getVehiclesInUse(int locationID, int vehicleID);

	ArrayList<Reservation> getAllReservations(int locationID, int vehicleID);

	ArrayList<ReservationStatus> getReservationStatus(int reservationID);

	ArrayList<Reservation> getReservationsInRange(int locationID,
			int vehicleID, Date startDate, Date stopDate);

	int getReservationCountsInRange(int locationID, int vehicleID,
			Date startDate, Date stopDate);

	String getStatus(int reservationID);

	public int makeReservation(int userID, int locationID, int vehicleID, Date startDate, Date stopDate);

	public int addReservationStatus(int reservationID, String reservationStatus);

	int isVehicleInUse(int vehicleID);

	int getOpenReservationCount(int vehicleID);

	int cancelReservation(int reservationID);

	ArrayList<Reservation> getOpenReservationsByUser(int userID);

	Reservation getReservation(int reservationID);
}
