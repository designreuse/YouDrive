package com.youdrive.interfaces;

import java.util.ArrayList;

import com.youdrive.models.VehicleType;

public interface IVehicleTypeManager {

	String deleteVehicleType(String type);

	int addVehicleType(String type, double hourlyPrice, double dailyPrice);

	ArrayList<VehicleType> getAllVehicleTypes();

	VehicleType getVehicleType(int vehicleTypeID);

	boolean updateVehicleType(int vehicleTypeID, String type, Double hourlyPrice, Double dailyPrice);

}
