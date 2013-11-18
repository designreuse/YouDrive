package com.youdrive.interfaces;

import java.util.ArrayList;

import com.youdrive.models.VehicleType;

public interface IVehicleTypeManager {
	/**
	 * Remove the vehicle type from the system by the type name
	 * @param type
	 * @return
	 */
	public String deleteVehicleType(String type);
	/**
	 * Adding a vehicle type to the system and return the type id. 
	 * Returns 0 if unsuccessful
	 * @param type
	 * @param hourlyPrice
	 * @param dailyPrice
	 * @return
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
	public boolean isTypeInUse(String type);

}
