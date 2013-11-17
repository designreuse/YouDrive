package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.youdrive.interfaces.IVehicleManager;
import com.youdrive.models.Vehicle;
import com.youdrive.models.VehicleType;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class VehicleDAO implements IVehicleManager {
	private PreparedStatement allVehiclesStmt;
	private PreparedStatement allVehiclesTypesStmt;
	private PreparedStatement getVehicleStmt;
	private PreparedStatement addVehicleStmt;
	private PreparedStatement deleteVehicleStmt;
	private PreparedStatement getVehiclesByLocationIdStmt;
	private PreparedStatement getVehiclesByLocationNameStmt;
	private PreparedStatement addVehicleTypeStmt;
	private PreparedStatement deleteVehicleTypeStmt;
	private SimpleDateFormat sdf;
	private Constants cs = new Constants();
	private Connection conn;

	public VehicleDAO(){
		try{
			conn = ConnectionManager.getInstance();
			allVehiclesStmt = conn.prepareStatement("select * from " + Constants.VEHICLES);
			allVehiclesTypesStmt = conn.prepareStatement("select * from " + Constants.VEHICLE_TYPES);
			getVehicleStmt = conn.prepareStatement("select * from " + Constants.VEHICLES + " where id = ?");
			getVehiclesByLocationIdStmt = conn.prepareStatement("select * from " + Constants.VEHICLES + " v left outer join " + Constants.LOCATIONS + " l on v.assignedLocation = l.id where l.id = ?");
			getVehiclesByLocationNameStmt = conn.prepareStatement("select * from " + Constants.VEHICLES + " v left outer join " + Constants.LOCATIONS + " l on v.assignedLocation = l.id where l.name = ?");
			addVehicleStmt = conn.prepareStatement("insert into " + Constants.VEHICLES + " values (DEFAULT,?,?,?,?,?,?,DEFAULT,?,?)",Statement.RETURN_GENERATED_KEYS);
			deleteVehicleStmt = conn.prepareStatement("delete from " + Constants.VEHICLES + " where id = ?");
			addVehicleTypeStmt = conn.prepareStatement("insert into " + Constants.VEHICLE_TYPES + " values (DEFAULT,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			deleteVehicleTypeStmt = conn.prepareStatement("delete from " + Constants.VEHICLE_TYPES + " where type = ?");
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println("Instantiated VehicleDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with VehicleDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public ArrayList<Vehicle> getAllVehicles(){
		ArrayList<Vehicle> results = new ArrayList<Vehicle>();
		try{
			ResultSet rs = allVehiclesStmt.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				String make = rs.getString("make");
				String model = rs.getString("model");
				int year = rs.getInt("year");
				String tag = rs.getString("tag");
				int mileage = rs.getInt("mileage");
				Date lastServiced = rs.getTimestamp("lastServiced");
				boolean isAvailable = rs.getBoolean("isAvailable");
				int vehicleType = rs.getInt("vehicleType");
				int assignedLocation = rs.getInt("assignedLocation");
				results.add(new Vehicle(id,make,model,year,tag,mileage,lastServiced,isAvailable,vehicleType,assignedLocation));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllVehicles method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}


	@Override
	public Vehicle getVehicle(int vehicleID) {
		Vehicle result = null;
		try{
			getVehicleStmt.setInt(1, vehicleID);
			ResultSet rs = getVehicleStmt.executeQuery();
			if (rs.next()){
				int id = rs.getInt("id");
				String make = rs.getString("make");
				String model = rs.getString("model");
				int year = rs.getInt("year");
				String tag = rs.getString("tag");
				int mileage = rs.getInt("mileage");
				Date lastServiced = rs.getTimestamp("lastServiced");
				boolean isAvailable = rs.getBoolean("isAvailable");
				int vehicleType = rs.getInt("vehicleType");
				int assignedLocation = rs.getInt("assignedLocation");
				result = new Vehicle(id,make,model,year,tag,mileage,lastServiced,isAvailable,vehicleType,assignedLocation);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllVehicles method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return result;
	}

	@Override
	public ArrayList<Vehicle> getVehiclesByLocationId(int locationID) {
		ArrayList<Vehicle> results = new ArrayList<Vehicle>();
		try{
			getVehiclesByLocationIdStmt.setInt(1, locationID);
			ResultSet rs = getVehiclesByLocationIdStmt.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				String make = rs.getString("make");
				String model = rs.getString("model");
				int year = rs.getInt("year");
				String tag = rs.getString("tag");
				int mileage = rs.getInt("mileage");
				Date lastServiced = rs.getTimestamp("lastServiced");
				boolean isAvailable = rs.getBoolean("isAvailable");
				int vehicleType = rs.getInt("vehicleType");
				int assignedLocation = rs.getInt("assignedLocation");
				results.add(new Vehicle(id,make,model,year,tag,mileage,lastServiced,isAvailable,vehicleType,assignedLocation));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllVehicles method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}
	
	@Override
	public ArrayList<Vehicle> getVehiclesByLocationName(String locationName) {
		ArrayList<Vehicle> results = new ArrayList<Vehicle>();
		try{
			getVehiclesByLocationNameStmt.setString(1, locationName);
			ResultSet rs = getVehiclesByLocationNameStmt.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				String make = rs.getString("make");
				String model = rs.getString("model");
				int year = rs.getInt("year");
				String tag = rs.getString("tag");
				int mileage = rs.getInt("mileage");
				Date lastServiced = rs.getDate("lastServiced");
				boolean isAvailable = rs.getBoolean("isAvailable");
				int vehicleType = rs.getInt("vehicleType");
				int assignedLocation = rs.getInt("assignedLocation");
				results.add(new Vehicle(id,make,model,year,tag,mileage,lastServiced,isAvailable,vehicleType,assignedLocation));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllVehicles method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}

	@Override
	public int addVehicle(String make, String model, int year,String tag, int mileage, String lastServiced, int vehicleType, int assignedLocation) {
		int vehicleID = 0;
		try{
			addVehicleStmt.setString(1, make);
			addVehicleStmt.setString(2, model);
			addVehicleStmt.setInt(3, year);
			addVehicleStmt.setString(4, tag);
			addVehicleStmt.setInt(5, mileage);
			java.util.Date d = sdf.parse(lastServiced);
			java.sql.Date sd = new java.sql.Date(d.getTime());
			addVehicleStmt.setDate(6,sd);
			addVehicleStmt.setInt(7, vehicleType);
			addVehicleStmt.setInt(8, assignedLocation);
			vehicleID = addVehicleStmt.executeUpdate();
			ResultSet rs = addVehicleStmt.getGeneratedKeys();
			if (rs.next()){
				vehicleID = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return vehicleID;
	}
	
	@Override
	public String deleteVehicle(int id) {
		String errorCode = "";
		try{
			deleteVehicleStmt.setInt(1, id);
			deleteVehicleStmt.executeUpdate();
			return errorCode;
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
	public String deleteVehicleType(String type) {
		String errorCode = "";
		try{
			deleteVehicleTypeStmt.setString(1, type);
			deleteVehicleTypeStmt.executeUpdate();
			return errorCode;
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
	public int addVehicleType(String type, double hourlyPrice, double dailyPrice) {
		int vehicleTypeID = 0;
		try{
			addVehicleTypeStmt.setString(1,type);
			addVehicleTypeStmt.setDouble(2, hourlyPrice);
			addVehicleTypeStmt.setDouble(3, dailyPrice);
			vehicleTypeID = addVehicleTypeStmt.executeUpdate();
			ResultSet rs = addVehicleTypeStmt.getGeneratedKeys();
			if (rs.next()){
				vehicleTypeID = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addVehicle method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return vehicleTypeID;
	}

	@Override
	public ArrayList<VehicleType> getAllVehicleTypes() {
		ArrayList<VehicleType> results = new ArrayList<VehicleType>();
		try{
			ResultSet rs = allVehiclesStmt.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				String make = rs.getString("make");
				String model = rs.getString("model");
				int year = rs.getInt("year");
				String tag = rs.getString("tag");
				int mileage = rs.getInt("mileage");
				Date lastServiced = rs.getTimestamp("lastServiced");
				boolean isAvailable = rs.getBoolean("isAvailable");
				int vehicleType = rs.getInt("vehicleType");
				int assignedLocation = rs.getInt("assignedLocation");
				results.add(new Vehicle(id,make,model,year,tag,mileage,lastServiced,isAvailable,vehicleType,assignedLocation));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllVehicles method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}
}