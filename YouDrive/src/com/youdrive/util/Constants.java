package com.youdrive.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Constants {

	public static final String JDBC_URL = "jdbc:mysql://localhost/YouDrive?autoReconnect=true";
	public static  String username = "demo";
	public static  String password = "demo";

	public static final String USERS = "Users";
	public static final String USERS_ID = "id";
	public static final String USERS_USERNAME = "username";
	public static final String USERS_PASSWORD = "password";
	public static final String USERS_FIRSTNAME = "firstName";
	public static final String USERS_LASTNAME = "lastName";
	public static final String USERS_STATE = "state";
	public static final String USERS_LICENSE = "license";
	public static final String USERS_EMAIL = "email";
	public static final String USERS_ADDRESS = "address";
	public static final String USERS_CCTYPE = "ccType";
	public static final String USERS_CCNUMBER = "ccNumber";
	public static final String USERS_CCSECURITYCODE = "ccSecurityCode";
	public static final String USERS_CCEXPIRATIONDATE = "ccExpirationDate";
	public static final String USERS_ISADMIN = "isAdmin";
	public static final String USERS_MEMBER_EXPIRATION_DATE = "memberExpiration";
	public static final String USERS_MEMBERSHIP_LEVEL = "membershipLevel";


	public static final String MEMBERSHIP = "Membership";
	public static final String MEMBERSHIP_ID = "id";
	public static final String MEMBERSHIP_NAME = "name";
	public static final String MEMBERSHIP_PRICE = "price";
	public static final String MEMBERSHIP_DURATION = "duration";
	
	public static final String LOCATIONS = "Locations";
	public static final String LOCATIONS_ID = "id";
	public static final String LOCATIONS_NAME = "name";
	public static final String LOCATIONS_ADDRESS = "address";
	public static final String LOCATIONS_CAPACITY = "capacity";
	
	public static final String COMMENTS = "Comments";
	public static final String COMMENTS_ID = "id";
	public static final String COMMENTS_CREATED_ON = "createdOn";
	public static final String COMMENTS_COMMENT = "comment";
	public static final String COMMENTS_AUTHOR = "author";
	
	public static final String RESERVATIONS = "Reservations";
	public static final String RESERVATIONS_ID = "id";
	public static final String RESERVATIONS_CUSTOMER_ID = "customerID";
	public static final String RESERVATIONS_LOCATION_ID = "locationID";
	public static final String RESERVATIONS_VEHICLE_ID = "vehicleID";
	public static final String RESERVATIONS_PICKUP_TIME = "timePickup";
	public static final String RESERVATIONS_START_TIME = "reservationStart";
	public static final String RESERVATIONS_END_TIME = "reservationEnd";
	
	
	public static final String VEHICLE_TYPES = "VehicleTypes";
	public static final String VEHICLE_TYPES_ID = "id";
	public static final String VEHICLE_TYPES_TYPE = "type";
	public static final String VEHICLE_TYPES_HOURLY_PRICE = "hourlyPrice";
	public static final String VEHICLE_TYPES_DAILY_PRICE = "dailyPrice";
	
	public static final String VEHICLES = "Vehicles";
	public static final String VEHICLES_ID = "id";
	public static final String VEHICLES_MAKE = "make";
	public static final String VEHICLES_MODEL = "model";
	public static final String VEHICLES_YEAR = "year";
	public static final String VEHICLES_TAG = "tag";
	public static final String VEHICLES_MILEAGE = "mileage";
	public static final String VEHICLES_LAST_SERVICED = "lastServiced";
	public static final String VEHICLES_IS_AVAILABLE = "isAvailable";
	public static final String VEHICLES_VEHICLE_TYPE = "vehicleType";
	public static final String VEHICLES_LOCATION = "assignedLocation";

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
		String errorCode = errorCodes.get(code);
		if (errorCode == null){
			errorCode = String.valueOf(code);
		}
		return errorCode;
	}
}
