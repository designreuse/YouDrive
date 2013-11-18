package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.youdrive.interfaces.IVehicleTypeManager;
import com.youdrive.models.VehicleType;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class VehicleTypeDAO implements IVehicleTypeManager{

	private PreparedStatement addVehicleTypeStmt;
	private PreparedStatement getVehicleTypeStmt;
	private PreparedStatement deleteVehicleTypeStmt;
	private PreparedStatement updateVehicleTypeStmt;
	private PreparedStatement allVehicleTypesStmt;
	private SimpleDateFormat sdf;
	private Constants cs = new Constants();
	private Connection conn;
	
	public VehicleTypeDAO(){
		try{
			conn = ConnectionManager.getInstance();
			allVehicleTypesStmt = conn.prepareStatement("select * from " + Constants.VEHICLE_TYPES);
			getVehicleTypeStmt = conn.prepareStatement("select * from " + Constants.VEHICLE_TYPES + " where id = ?");
			addVehicleTypeStmt = conn.prepareStatement("insert into " + Constants.VEHICLE_TYPES + " values (DEFAULT,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			deleteVehicleTypeStmt = conn.prepareStatement("delete from " + Constants.VEHICLE_TYPES + " where type = ?");			
			updateVehicleTypeStmt = conn.prepareStatement("update " + Constants.VEHICLE_TYPES + " set " + Constants.VEHICLE_TYPES_TYPE + " = ?, " + Constants.VEHICLE_TYPES_HOURLY_PRICE + " = ?, " + Constants.VEHICLE_TYPES_DAILY_PRICE + " = ? where " + Constants.VEHICLE_TYPES_ID + " = ?");
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println("Instantiated VehicleTypesDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with VehicleDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
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
			System.err.println("Problem with addVehicleType method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return vehicleTypeID;
	}

	@Override
	public ArrayList<VehicleType> getAllVehicleTypes() {
		ArrayList<VehicleType> results = new ArrayList<VehicleType>();
		try{
			ResultSet rs = allVehicleTypesStmt.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				String type = rs.getString("type");
				Double hourlyPrice = rs.getDouble("hourlyPrice");
				Double dailyPrice = rs.getDouble("dailyPrice");
				results.add(new VehicleType(id, type, hourlyPrice, dailyPrice));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllVehicleTypes method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}

	@Override
	public VehicleType getVehicleType(int vehicleTypeID) {
		VehicleType result = null;
		try{
			getVehicleTypeStmt.setInt(1, vehicleTypeID);
			ResultSet rs = getVehicleTypeStmt.executeQuery();
			if (rs.next()){
				int id = rs.getInt("id");
				String type = rs.getString("type");
				Double hourlyPrice = rs.getDouble("hourlyPrice");
				Double dailyPrice = rs.getDouble("dailyPrice");
				result = new VehicleType(id, type, hourlyPrice, dailyPrice);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getVehicleType method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return result;
	}

	@Override
	public boolean updateVehicleType(int vehicleTypeID, String type,Double hourlyPrice, Double dailyPrice) {
		try{
			updateVehicleTypeStmt.setString(1, type);
			updateVehicleTypeStmt.setDouble(2, hourlyPrice);
			updateVehicleTypeStmt.setDouble(3, dailyPrice);
			updateVehicleTypeStmt.setInt(4, vehicleTypeID);			
			updateVehicleTypeStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with updateVehicleType method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return false;
	}
}