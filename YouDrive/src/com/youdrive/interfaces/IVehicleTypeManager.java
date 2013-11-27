package com.youdrive.interfaces;

import java.util.ArrayList;

import com.youdrive.models.VehicleType;

public interface IVehicleTypeManager {
	/**
	 * Remove the vehicle type from the system by the type name
	 * @param type
	 * @return boolean
	 */
	public boolean deleteVehicleType(int typeID);
	/**
	 * Adding a vehicle type to the system and return the type id. 
	 * Returns 0 if unsuccessful
	 * @param type
	 * @param hourlyPrice
	 * @param dailyPrice
	 * @return int
	 */
	public int addVehicleType(String type, double hourlyPrice, double dailyPrice);
	/**
	 * Retrieves the ArrayList of all vehicle types in the system.
	 * ArrayList is empty if no types exist.
	 * @return ArrayList<VehicleType>
	 */
	public ArrayList<VehicleType> getAllVehicleTypes();
	/**
	 * Get the VehicleType object from the system with the provided Vehicle Type id
	 * @param vehicleTypeID
	 * @return VehicleType
	 */
	public VehicleType getVehicleType(int vehicleTypeID);
	/**
	 * Update the Vehicle type 
	 * true if successful and false otherwise
	 * @param vehicleTypeID
	 * @param type
	 * @param hourlyPrice
	 * @param dailyPrice
	 * @return boolean
	 */
	public boolean updateVehicleType(int vehicleTypeID, String type, Double hourlyPrice, Double dailyPrice);
	/**
	 * Returns true if the type name is already taken
	 * and false otherwise
	 * @param type
	 * @return
	 */
	public boolean isTypeInUse(String type);
	/**
	 * Returns the number of vehicles of the vehicle type requested
	 * Return 0 if none and -1 in case of an error
	 * @param typeID
	 * @return
	 */
	public int getCountOfVehicleType(int typeID);
	double getHourlyPrice(int vehicleTypeID);
	double getDailyPrice(int vehicleTypeID);

}
