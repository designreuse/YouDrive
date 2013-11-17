package com.youdrive.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.catalina.filters.CsrfPreventionFilter;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class Populator {

	private static ILocationManager ilm = new LocationDAO();
	private PreparedStatement truncateLocationStmt;
	private Connection conn;
	private Constants cs = new Constants();

	public Populator(){
		try{
			conn = ConnectionManager.getInstance();
			System.out.println("Instantiated Populator");
		}catch(Exception e){
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
	}

	public void populateVehicles(){
		
	}
	
	public void populateLocations(){
		StringBuilder errorCode = new StringBuilder();
		ilm.addLocation("Milledge Location", "1234 Bobsled Dr, Athens GA 30605", 100,errorCode);
		System.out.println(cs.getError(parseError(errorCode.toString())));
		errorCode = new StringBuilder();
		ilm.addLocation("Five Points Location", "3415 Ullah Circle, Athens GA 30606", 90,errorCode);
		System.out.println(cs.getError(parseError(errorCode.toString())));
		errorCode = new StringBuilder();
		ilm.addLocation("East Campus Location", "198 East Campus Rd, Athens GA 30602", 300,errorCode);
		System.out.println(cs.getError(parseError(errorCode.toString())));
		errorCode = new StringBuilder();
		ilm.addLocation("Locos Location", "457 Macarena Course Rd, Athens GA 30607", 150,errorCode);
		System.out.println(cs.getError(parseError(errorCode.toString())));
	}
	
	private int parseError(String code){
		try{
			return Integer.parseInt(code);
		}catch(Exception e){
			return -1;
		}
	}
	public void clearLocations() throws SQLException{
		truncateLocationStmt = conn.prepareStatement("truncate table " + Constants.LOCATIONS);
		truncateLocationStmt.executeUpdate();
		truncateLocationStmt.close();
	}
	public static void main(String[] args){		
		Populator p = new Populator();
		p.populateLocations();
	}
}
