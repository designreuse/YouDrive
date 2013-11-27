package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.youdrive.interfaces.IReservationManager;
import com.youdrive.models.Reservation;
import com.youdrive.models.ReservationStatus;
import com.youdrive.models.Vehicle;
import com.youdrive.models.VehicleType;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class ReservationDAO implements IReservationManager{
	private PreparedStatement getCreatedReservationsStmt;
	private PreparedStatement checkLocationsInFutureReservationsStmt;
	private PreparedStatement getReservationsStmt;
	private PreparedStatement getReservationStatusStmt;
	private PreparedStatement getCancelledOrReturnedReservationStatusStmt;
	private PreparedStatement makeReservationStmt;
	private PreparedStatement checkOpenReservationByVehicleStmt;
	private PreparedStatement addReservationStatusStmt;
	private PreparedStatement isVehicleInUseStmt;
	private PreparedStatement cancelReservationStmt;
	private PreparedStatement getReservationStmt;
	private PreparedStatement getUserReservationsStmt;
	private SimpleDateFormat sdf;
	private Constants cs = Constants.getInstance();
	private Connection conn;
	
	public ReservationDAO(){
		try{
			conn = ConnectionManager.getInstance();
			checkLocationsInFutureReservationsStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? AND reservationStart > NOW()");
			getCreatedReservationsStmt = conn.prepareStatement(" select * from Reservations r left outer join ReservationStatus rs on rs.reservationID = r.id where r.locationID = ? AND r.vehicleID = ? AND rs.reservationStatus = \"Created\"");
			getReservationsStmt = conn.prepareStatement("select * from Reservations where locationID = ? and vehicleID = ? order by vehicleID");
			getReservationStatusStmt = conn.prepareStatement("select * from ReservationStatus where reservationID = ?");
			getCancelledOrReturnedReservationStatusStmt = conn.prepareStatement("select reservationStatus from ReservationStatus where reservationID = ? and reservationStatus != \"Created\"");
			checkOpenReservationByVehicleStmt = conn.prepareCall("select count(*) from Reservations r left outer join ReservationStatus rs on rs.reservationID = r.id where r.vehicleID = ? and (rs.reservationStatus = \"Returned\" or rs.reservationStatus = \"Cancelled\")");
			makeReservationStmt = conn.prepareStatement("insert into Reservations values (DEFAULT,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			addReservationStatusStmt = conn.prepareStatement("insert into ReservationStatus values (DEFAULT,?,NOW(),?)",Statement.RETURN_GENERATED_KEYS);
			isVehicleInUseStmt = conn.prepareStatement("select count(*) from Reservations where vehicleID = ?");
			cancelReservationStmt = conn.prepareStatement("insert into ReservationStatus values(DEFAULT,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			getReservationStmt = conn.prepareStatement("select r.*,rs.id as reservationStatusID,rs.dateAdded,rs.reservationStatus from Reservations r left outer join ReservationStatus rs on rs.reservationID = r.id where r.id = ?");
			getUserReservationsStmt = conn.prepareCall("select r.*,rs.id as reservationStatusID,rs.dateAdded,rs.reservationStatus from Reservations r left outer join ReservationStatus rs on rs.reservationID = r.id where r.customerID = ?");
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println("Instantiated ReservationDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with ReservationDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public Reservation getReservation(int reservationID){
		Reservation temp = null;
		try{
			getReservationStmt.setInt(1,reservationID);
			ResultSet rs = getReservationStmt.executeQuery();
			int prevReservationID = 0;
			while (rs.next()){
				int customerID = rs.getInt("customerID");
				int locationID = rs.getInt("locationID");
				int vehicleID = rs.getInt("vehicleID");
				java.util.Date sd = rs.getTimestamp("reservationStart");
				java.util.Date ed = rs.getTimestamp("reservationEnd");
				int statusID = rs.getInt("reservationStatusID");
				java.util.Date da = rs.getTimestamp("dateAdded");
				String status = rs.getString("reservationStatus");
				if (prevReservationID != reservationID){
					temp = new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed);
				}else{
					temp.addReservationStatus(new ReservationStatus(statusID,reservationID,da,status));
				}
				prevReservationID = reservationID;
			}
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with getReservation constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return temp;
	}
	
	@Override
	public ArrayList<Reservation> getUserReservations(int userID){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			getUserReservationsStmt.setInt(1,userID);
			ResultSet rs = getUserReservationsStmt.executeQuery();
			Reservation temp = null;
			int prevReservationID = 0;
			while (rs.next()){
				int reservationID = rs.getInt("id");
				int customerID = rs.getInt("customerID");
				int locationID = rs.getInt("locationID");
				int vehicleID = rs.getInt("vehicleID");
				java.util.Date startDate = rs.getTimestamp("reservationStart");
				java.util.Date endDate = rs.getTimestamp("reservationEnd");
				int reservationStatusID = rs.getInt("reservationStatusID");
				java.util.Date dateAdded = rs.getTimestamp("dateAdded");
				String status = rs.getString("reservationStatus");
				if (prevReservationID != reservationID){
					temp = new Reservation(reservationID, customerID, locationID, vehicleID, startDate, endDate);
					temp.addReservationStatus(new ReservationStatus(reservationStatusID, reservationID, dateAdded, status));
					results.add(temp);
				}else{
					temp.addReservationStatus(new ReservationStatus(reservationStatusID, reservationID, dateAdded, status));
				}
				prevReservationID = reservationID;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getOpenReservationCount method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}		
	
	@Override
	public int cancelReservation(int reservationID){
		int results = -1;
		try{
			cancelReservationStmt.setInt(1,reservationID);
			ResultSet rs = cancelReservationStmt.executeQuery();
			if (rs.next()){
				results = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getOpenReservationCount method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}	
	
	@Override
	public int getCancelledOrReturnedReservationCount(int vehicleID){
		//Returns 0 if no cancelled/return reservations found
		int results = -1;
		try{
			checkOpenReservationByVehicleStmt.setInt(1,vehicleID);
			ResultSet rs = checkOpenReservationByVehicleStmt.executeQuery();
			if (rs.next()){
				results = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getOpenReservationCount method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override
	public int isVehicleInUse(int vehicleID){
		int results = -1;
		try{
			isVehicleInUseStmt.setInt(1,vehicleID);
			ResultSet rs = isVehicleInUseStmt.executeQuery();
			if (rs.next()){
				results = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationStatus method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override 
	public String getStatus(int reservationID){
		String results = "";
		try{
			getCancelledOrReturnedReservationStatusStmt.setInt(1,reservationID);
			ResultSet rs = getCancelledOrReturnedReservationStatusStmt.executeQuery();
			if (rs.next()){
				results = rs.getString(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationStatus method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	

	@Override
	public ArrayList<ReservationStatus> getReservationStatus(int reservationID){
		ArrayList<ReservationStatus> results = new ArrayList<ReservationStatus>();
		try{
			getReservationStatusStmt.setInt(1,reservationID);
			ResultSet rs = getReservationStatusStmt.executeQuery();
			ReservationStatus temp;
			while (rs.next()){
				int reservationStatusID = rs.getInt("id");
				java.util.Date rd = rs.getTimestamp("dateAdded");
				String status = rs.getString("reservationStatus");
				results.add(new ReservationStatus(reservationStatusID, reservationID, rd, status));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationStatus method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override
	public ArrayList<Reservation> getAllReservations(int locationID, int vehicleID){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			getReservationsStmt.setInt(1,locationID);
			getReservationsStmt.setInt(2,vehicleID);
			ResultSet rs = getReservationsStmt.executeQuery();
			Reservation temp;
			while (rs.next()){
				int reservationID = rs.getInt("id");
				int customerID = rs.getInt("customerID");
				java.util.Date sd = rs.getTimestamp("reservationStart");
				java.util.Date ed = rs.getTimestamp("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllReservations method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override
	public ArrayList<Reservation> getVehiclesInUse(int locationID, int vehicleID){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			getCreatedReservationsStmt.setInt(1,locationID);
			getCreatedReservationsStmt.setInt(2,vehicleID);
			ResultSet rs = getCreatedReservationsStmt.executeQuery();
			while (rs.next()){
				int reservationID = rs.getInt("id");
				int customerID = rs.getInt("customerID");
				java.util.Date sd = rs.getTimestamp("reservationStart");
				java.util.Date ed = rs.getTimestamp("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getVehiclesInUse method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
	
	@Override
	public int checkIfLocationInUse(int locationID){
		try{
			checkLocationsInFutureReservationsStmt.setInt(1, locationID);
			ResultSet rs = checkLocationsInFutureReservationsStmt.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with checkIfLocationInUse method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return -1;
	}


	@Override
	public int makeReservation(int userID, int locationID, int vehicleID, Date startDate, Date stopDate) {
		int results = 0;
		try{
			makeReservationStmt.setInt(1, userID);
			makeReservationStmt.setInt(2, locationID);
			makeReservationStmt.setInt(3, vehicleID);
			makeReservationStmt.setTimestamp(4, new java.sql.Timestamp(startDate.getTime()));
			makeReservationStmt.setTimestamp(5, new java.sql.Timestamp(stopDate.getTime()));
			makeReservationStmt.executeUpdate();
			ResultSet rs = makeReservationStmt.getGeneratedKeys();
			if (rs.next()){
				results = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with makeReservation method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}

	@Override
	public int addReservationStatus(int reservationID, String reservationStatus) {
		int results = -1;
		try{
			addReservationStatusStmt.setInt(1, reservationID);
			addReservationStatusStmt.setString(2, reservationStatus);
			addReservationStatusStmt.executeUpdate();
			ResultSet rs = addReservationStatusStmt.getGeneratedKeys();
			if (rs.next()){
				results = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with updateReservationStatus method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}
}
