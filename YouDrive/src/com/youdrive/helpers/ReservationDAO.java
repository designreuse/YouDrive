package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.youdrive.interfaces.IReservationManager;
import com.youdrive.models.Reservation;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class ReservationDAO implements IReservationManager{
	private PreparedStatement getReservationsInRangeByVehicleStmt;
	private PreparedStatement getReservationsInRangeByLocationStmt;
	private PreparedStatement getReservationsInRangeByLocationAndVehicleStmt;
	private PreparedStatement getReservationsInRangeStmt;
	private PreparedStatement getReservationsInRangeByVehicleCountStmt;
	private PreparedStatement getReservationsInRangeByLocationCountStmt;
	private PreparedStatement getReservationsInRangeByLocationAndVehicleCountStmt;
	private PreparedStatement getReservationsInRangeCountStmt;
	private PreparedStatement getReservationsByVehicleIDStmt;
	private PreparedStatement getReservationsByLocationIDStmt;
	private PreparedStatement getReservationsByLocationAndVehicleStmt;
	private PreparedStatement getAllReservationsStmt;
	private SimpleDateFormat sdf;
	private Constants cs = Constants.getInstance();
	private Connection conn;
	
	public ReservationDAO(){
		try{
			conn = ConnectionManager.getInstance();
			getReservationsInRangeStmt = conn.prepareStatement("select * from Reservations where reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByVehicleStmt = conn.prepareStatement("select * from Reservations where vehicleID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByLocationStmt = conn.prepareStatement("select * from Reservations where locationID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByLocationAndVehicleStmt = conn.prepareStatement("select * from Reservations where locationID = ? AND vehicleID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeCountStmt = conn.prepareStatement("select count(*) from Reservations where reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByVehicleCountStmt = conn.prepareStatement("select count(*) from Reservations where vehicleID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByLocationCountStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByLocationAndVehicleCountStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? AND vehicleID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsByVehicleIDStmt = conn.prepareStatement("select * from Reservations where vehicleID = ?");
			getReservationsByLocationIDStmt = conn.prepareStatement("select * from Reservations where locationID = ?");
			getReservationsByLocationAndVehicleStmt = conn.prepareStatement("select * from Reservations where locationID = ? AND vehicleID = ?");
			getAllReservationsStmt = conn.prepareStatement("select * from Reservations");
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println("Instantiated ReservationDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with ReservationDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}


	@Override
	public int getVehicleReservationRangeCount(int vehicleID, java.util.Date startDate, java.util.Date stopDate){
		try{
			getReservationsInRangeByVehicleCountStmt.setInt(1, vehicleID);
			getReservationsInRangeByVehicleCountStmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			getReservationsInRangeByVehicleCountStmt.setTimestamp(3, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = getReservationsInRangeByVehicleCountStmt.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getVehicleReservationCount method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return 0;
	}
	
	@Override
	public int getLocationReservationRangeCount(int locationID, java.util.Date startDate, java.util.Date stopDate){
		try{
			getReservationsInRangeByLocationCountStmt.setInt(1, locationID);
			getReservationsInRangeByLocationCountStmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			getReservationsInRangeByLocationCountStmt.setTimestamp(3, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = getReservationsInRangeByLocationCountStmt.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getLocationReservationCount method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return 0;
	}
	
	@Override
	public ArrayList<Reservation> getAllReservationsInRange(java.util.Date startDate, java.util.Date stopDate){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			getReservationsInRangeStmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
			getReservationsInRangeStmt.setTimestamp(2, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = getReservationsInRangeStmt.executeQuery();
			while (rs.next()){
				int reservationID = rs.getInt("id");
				int customerID = rs.getInt("customerID");
				int locationID = rs.getInt("locationID");
				int vehicleID = rs.getInt("vehicleID");
				java.util.Date sd = rs.getDate("reservationStart");
				java.util.Date ed = rs.getDate("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getVehicleReservationCount method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override
	public ArrayList<Reservation> getReservationsInRangeByVehicle(int vehicleID, java.util.Date startDate, java.util.Date stopDate){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			getReservationsInRangeByVehicleStmt.setInt(1, vehicleID);
			getReservationsInRangeByVehicleStmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			getReservationsInRangeByVehicleStmt.setTimestamp(3, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = getReservationsInRangeByVehicleStmt.executeQuery();
			while (rs.next()){
				int reservationID = rs.getInt("id");
				int customerID = rs.getInt("customerID");
				int locationID = rs.getInt("locationID");
				java.util.Date sd = rs.getDate("reservationStart");
				java.util.Date ed = rs.getDate("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationsInRangeByVehicle method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	
	@Override
	public ArrayList<Reservation> getReservationsInRangeByLocation(int locationID, java.util.Date startDate, java.util.Date stopDate){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			getReservationsInRangeByLocationStmt.setInt(1, locationID);
			getReservationsInRangeByLocationStmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			getReservationsInRangeByLocationStmt.setTimestamp(3, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = getReservationsInRangeByLocationStmt.executeQuery();
			while (rs.next()){
				int reservationID = rs.getInt("id");
				int customerID = rs.getInt("customerID");
				int vehicleID = rs.getInt("vehicleID");
				java.util.Date sd = rs.getDate("reservationStart");
				java.util.Date ed = rs.getDate("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationsInRangeByVehicle method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override
	public ArrayList<Reservation> getReservationsByLocationAndVehicle(int locationID, int vehicleID, java.util.Date startDate, java.util.Date stopDate){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			getReservationsInRangeByLocationAndVehicleStmt.setInt(1, locationID);
			getReservationsInRangeByLocationAndVehicleStmt.setInt(2, vehicleID);
			getReservationsInRangeByLocationAndVehicleStmt.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
			getReservationsInRangeByLocationAndVehicleStmt.setTimestamp(4, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = getReservationsInRangeByLocationAndVehicleStmt.executeQuery();
			while (rs.next()){
				int reservationID = rs.getInt("id");
				int customerID = rs.getInt("customerID");
				java.util.Date sd = rs.getDate("reservationStart");
				java.util.Date ed = rs.getDate("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationsInRangeByVehicle method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override
	public int getReservationsByLocationAndVehicleCount(int locationID, int vehicleID, java.util.Date startDate, java.util.Date stopDate){
		try{
			getReservationsInRangeByLocationAndVehicleCountStmt.setInt(1, locationID);
			getReservationsInRangeByLocationAndVehicleCountStmt.setInt(2, vehicleID);
			getReservationsInRangeByLocationAndVehicleCountStmt.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
			getReservationsInRangeByLocationAndVehicleCountStmt.setTimestamp(4, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = getReservationsInRangeByLocationAndVehicleCountStmt.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationsInRangeByVehicle method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return 0;
	}
}
