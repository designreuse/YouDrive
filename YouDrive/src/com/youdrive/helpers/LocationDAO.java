package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.youdrive.interfaces.ILocationManager;
import com.youdrive.models.Location;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class LocationDAO implements ILocationManager {
	private PreparedStatement getAllLocationsStmt;
	private PreparedStatement getLocationByIdStmt;
	private PreparedStatement getLocationByNameStmt;
	private PreparedStatement addLocationStmt;
	private PreparedStatement deleteLocationByNameStmt;
	private PreparedStatement deleteLocationByIdStmt;
	private PreparedStatement getVehiclesByLocationStmt;
	private PreparedStatement updateLocationStmt;
	private Constants cs = new Constants();
	private Connection conn = null;
	private static LocationDAO ldao = null;
		
	public LocationDAO(){
		try{
			conn = ConnectionManager.getInstance();
			getAllLocationsStmt = conn.prepareStatement("select * from " + Constants.LOCATIONS + " order by " + Constants.LOCATIONS_NAME);
			getLocationByIdStmt = conn.prepareStatement("select * from " + Constants.LOCATIONS + " where " + Constants.LOCATIONS_ID + " = ?");
			getLocationByNameStmt = conn.prepareStatement("select * from " + Constants.LOCATIONS + " where " + Constants.LOCATIONS_NAME + " = ?");
			addLocationStmt = conn.prepareStatement("insert into " + Constants.LOCATIONS + " values (DEFAULT,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			deleteLocationByNameStmt = conn.prepareStatement("select * from " + Constants.LOCATIONS + " where " + Constants.LOCATIONS_NAME + " = ?");
			deleteLocationByIdStmt = conn.prepareStatement("select * from " + Constants.LOCATIONS + " where " + Constants.LOCATIONS_ID + " = ?");
			getVehiclesByLocationStmt = conn.prepareStatement("select count(*) from " + Constants.VEHICLES + " as v left outer join " 
										+ Constants.LOCATIONS + " as l on l.id = v.assignedLocation where l.id = ?");
			updateLocationStmt = conn.prepareStatement("update " + Constants.LOCATIONS + " set " + Constants.LOCATIONS_NAME + " = ?, " + Constants.LOCATIONS_ADDRESS + " = ?, " + Constants.LOCATIONS_CAPACITY + " = ? where " + Constants.LOCATIONS_ID + " = ?");
			System.out.println("Instantiated LocationDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with LocationDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/*public static LocationDAO getInstance(){
		if (ldao == null){
			ldao = new LocationDAO();				
		}
		return ldao;
	}*/
	
	@Override
	public Location getLocationById(int id){
		Location result = null;
		try{
			getLocationByIdStmt.setInt(1, id);
			ResultSet rs = getLocationByIdStmt.executeQuery();
			if (rs.next()){
				int locID = rs.getInt("id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				int capacity = rs.getInt("capacity");
				result = new Location(locID,name,address,capacity);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getLocationById: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return result;
	}

	@Override
	public Location getLocationByName(String name){
		Location result = null;
		try{
			getLocationByNameStmt.setString(1, name);
			ResultSet rs = getLocationByNameStmt.executeQuery();
			if (rs.next()){
				int locID = rs.getInt("id");
				String locName = rs.getString("name");
				String address = rs.getString("address");
				int capacity = rs.getInt("capacity");
				result = new Location(locID,locName,address,capacity);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getLocationByName: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return result;
	}

	@Override
	public ArrayList<Location> getAllLocations(){
		ArrayList<Location> results = new ArrayList<Location>();
		try{
			ResultSet rs = getAllLocationsStmt.executeQuery();
			while (rs.next()){
				int locID = rs.getInt("id");
				String locName = rs.getString("name");
				String address = rs.getString("address");
				int capacity = rs.getInt("capacity");
				results.add(new Location(locID,locName,address,capacity));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllLocations: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}


	@Override
	public int addLocation(String name, String address, int capacity) {
		int locationID = 0;
		try{
			addLocationStmt.setString(1, name);
			addLocationStmt.setString(2, address);
			addLocationStmt.setInt(3, capacity);
			locationID = addLocationStmt.executeUpdate();
			ResultSet rs = addLocationStmt.getGeneratedKeys();
			if (rs.next()){
				locationID = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return locationID;
	}
	
	@Override
	public int addLocation(String name, String address, int capacity, StringBuilder errorCode) {
		int locationID = 0;
		try{
			addLocationStmt.setString(1, name);
			addLocationStmt.setString(2, address);
			addLocationStmt.setInt(3, capacity);
			locationID = addLocationStmt.executeUpdate();
			ResultSet rs = addLocationStmt.getGeneratedKeys();
			if (rs.next()){
				locationID = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return locationID;
	}

	@Override
	public String deleteLocationById(int id) {
		String errorCode = "";
		try{
			deleteLocationByIdStmt.setInt(1, id);
			deleteLocationByIdStmt.executeUpdate();
		}catch(SQLException e){
			errorCode = String.valueOf(e.getErrorCode());
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			errorCode = "Error";
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return errorCode;
	}

	@Override
	public String deleteLocationByName(String name) {
		String errorCode = "";
		try{
			deleteLocationByNameStmt.setString(1, name);
			deleteLocationByNameStmt.executeUpdate();
		}catch(SQLException e){
			errorCode = String.valueOf(e.getErrorCode());
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			errorCode = "Error";
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return errorCode;
	}

	@Override
	public boolean isLocationInUse(int locationID) {
		try{
			getVehiclesByLocationStmt.setInt(1, locationID);
			ResultSet rs = getVehiclesByLocationStmt.executeQuery();
			if (rs.next()){
				if (rs.getInt(1) > 0){
					return true;
				}
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return false;
	}

	@Override
	public boolean updateLocation(int locationID, String name, String address,int capacity) {
		try{
			updateLocationStmt.setString(1, name);
			updateLocationStmt.setString(2, address);
			updateLocationStmt.setInt(3, capacity);
			updateLocationStmt.setInt(4, locationID);			
			updateLocationStmt.executeUpdate();
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return false;
	}

}
