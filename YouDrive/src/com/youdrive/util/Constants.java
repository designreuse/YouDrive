package com.youdrive.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Constants {

	public static final String JDBC_URL = "jdbc:mysql://localhost/YouDrive?autoReconnect=true";
	public static  String username = "demo";
	public static  String password = "demo";
	
	public static final String USERS = "Users";
	public static final String MEMBERSHIP = "Membership";
	public static final String LOCATIONS = "Locations";
	public static final String COMMENTS = "Comments";
	public static final String RESERVATIONS = "Reservations";
	public static final String VEHICLE_TYPES = "VehicleTypes";
	public static final String VEHICLES = "Vehicles";
	
	//MySQL Database
	public static HashMap<Integer,String> errorCodes = new LinkedHashMap<Integer,String>();
	
	public Constants(){
		initializeErrors();
	}
	
	public void initializeErrors(){
		errorCodes.put(1062, "DUPLICATE_KEY_FOUND");
		errorCodes.put(1216, "CHILD_RECORD_FOUND");
		errorCodes.put(1217, "PARENT_RECORD_NOT_FOUND");
		errorCodes.put(1048, "NULL_VALUE_FOUND");
		errorCodes.put(1205, "RECORD_HAS_BEEN_LOCKED");
	}
	
	public String getError(int code){
		return errorCodes.get(code);
	}
}
