package com.youdrive.interfaces;

import java.util.ArrayList;
import java.util.Date;

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
	String getVehicleType(int vehicleTypeID);
}
