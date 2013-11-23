package com.youdrive.interfaces;

import java.util.ArrayList;
import java.util.Date;

import com.youdrive.models.Comment;
import com.youdrive.models.Vehicle;
import com.youdrive.models.VehicleType;

public interface IVehicleManager {
	/**
	 * Returns a list of all vehicles in the database
	 * @return ArrayList<Vehicle>
	 */
	public ArrayList<Vehicle> getAllVehicles();
	/**
	 * Get a single vehicle by its id
	 * @param id
	 * @return Vehicle
	 */
	public Vehicle getVehicle(int id);
	/**
	 * Get the vehicle location name of a provided location id
	 * TODO maybe move to Location  interface
	 * @param locationID
	 * @return
	 */
	public String getVehicleLocation(int locationID);
	/**
	 * Get vehicles by providing the location  ID
	 * @param locationID
	 * @return ArrayList<Vehicle>
	 */
	public ArrayList<Vehicle> getVehiclesByLocationId(int locationID);
	/**
	 * Get vehicles by providing the location Name
	 * @param locationName
	 * @return ArrayList<Vehicle>
	 */
	public ArrayList<Vehicle> getVehiclesByLocationName(String locationName);
	/**
	 * Adds the vehicle to the database and returns the vehicle ID. 
	 * If vehicle ID is 0, there was a problem.
	 * @param make
	 * @param model
	 * @param year
	 * @param tag
	 * @param mileage
	 * @param lastServiced
	 * @param isAvailable
	 * @param vehicleType
	 * @param assignedLocation
	 * @return int
	 */
	public int addVehicle(String make, String model, int year, String tag, int mileage, String lastServiced, 
			int vehicleType, int assignedLocation);
	/**
	 * Remove a vehicle by the vehicle ID
	 * @param id
	 * @return
	 */
	public String deleteVehicle(int id);
	/**
	 * Get the vehicle Type by the vehicle type id of a vehicle
	 * @param vehicleTypeID
	 * @return String
	 */
	public String getVehicleType(int vehicleTypeID);
	/**
	 * Update the Vehicle details. Returns true if successful and false otherwise
	 * @param id
	 * @param make
	 * @param model
	 * @param year
	 * @param tag
	 * @param mileage
	 * @param lastServiced
	 * @param vehicleType
	 * @param assignedLocation
	 * @return boolean
	 */
	public boolean updateVehicle(int id, String make, String model, int year, String tag, int mileage, String lastServiced, 
			int vehicleType, int assignedLocation);
	/**
	 * Retrieves the list of comments associated with the vehicle object
	 * @param vehicleID
	 * @return
	 */
	public ArrayList<Comment> getVehicleComments(int vehicleID);
	public  int addVehicleComment(int vehicleID, String comment, int author);
	public ArrayList<Vehicle> getAllVehiclesByLocationAndType(int locationID, int vehicleTypeID);
}
