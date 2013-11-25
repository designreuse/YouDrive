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
	private PreparedStatement getReservationsInRangeByVehicleStmt;
	private PreparedStatement getReservationsInRangeByLocationStmt;
	private PreparedStatement getReservationsInRangeByLocationAndVehicleStmt;
	private PreparedStatement getReservationsInRangeStmt;
	private PreparedStatement getReservationsInRangeByVehicleCountStmt;
	private PreparedStatement getReservationsInRangeByLocationCountStmt;
	private PreparedStatement getReservationsInRangeByLocationAndVehicleCountStmt;
	private PreparedStatement getAllReservationsStmt;
	private PreparedStatement getAllReturnedReservationsStmt;
	private PreparedStatement getCreatedReservationsStmt;
	private PreparedStatement checkLocationsInFutureReservationsStmt;
	private PreparedStatement getReservationsStmt;
	private PreparedStatement getReservationStatusStmt;
	private PreparedStatement getCancelledOrReturnedReservationStatusStmt;
	private PreparedStatement checkReservationRangeStmt;
	private PreparedStatement checkReservationRangeCountStmt;
	private PreparedStatement makeReservationStmt;
	private PreparedStatement addReservationStatusStmt;
	private PreparedStatement isVehicleInUseStmt;
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
			getReservationsInRangeByVehicleCountStmt = conn.prepareStatement("select count(*) from Reservations where vehicleID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByLocationCountStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			getReservationsInRangeByLocationAndVehicleCountStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? AND vehicleID = ? AND reservationStart >= ? AND reservationEnd <= ?");
			checkLocationsInFutureReservationsStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? AND reservationStart > NOW()");
			getAllReturnedReservationsStmt = conn.prepareStatement("select v.*, r.id,r.customerID,r.reservationStart,r.reservationEnd,vt.type,vt.hourlyPrice,vt.dailyPrice,rs.id, rs.dateAdded,rs.reservationStatus from Reservations r left outer join Vehicles v on r.vehicleID = v.id left outer join VehicleTypes vt on vt.id = v.vehicleType left outer join ReservationStatus rs on rs.reservationID = r.id where rs.reservationStatus = \"Returned\"");
			getAllReservationsStmt = conn.prepareStatement("select v.*, r.id,r.customerID,r.reservationStart,r.reservationEnd,vt.type,vt.hourlyPrice,vt.dailyPrice,rs.id, rs.dateAdded,rs.reservationStatus from Reservations r left outer join Vehicles v on r.vehicleID = v.id left outer join VehicleTypes vt on vt.id = v.vehicleType left outer join ReservationStatus rs on rs.reservationID = r.id");
			getCreatedReservationsStmt = conn.prepareStatement(" select * from Reservations r left outer join ReservationStatus rs on rs.reservationID = r.id where r.locationID = ? AND r.vehicleID = ? AND rs.reservationStatus = \"Created\"");
			getReservationsStmt = conn.prepareStatement("select * from Reservations where locationID = ? and vehicleID = ? order by vehicleID");
			getReservationStatusStmt = conn.prepareStatement("select * from ReservationStatus where reservationID = ?");
			getCancelledOrReturnedReservationStatusStmt = conn.prepareStatement("select reservationStatus from ReservationStatus where reservationID = ? and reservationStatus != \"Created\"");
			checkReservationRangeStmt = conn.prepareStatement("select * from Reservations where locationID = ? and vehicleID = ? and NOT reservationStart between ? and ? and NOT reservationEnd between ? and ?");
			checkReservationRangeCountStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? and vehicleID = ? and ((reservationStart between ? and ?) or (reservationEnd between ? and ?))");
			makeReservationStmt = conn.prepareStatement("insert into Reservations values (DEFAULT,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			addReservationStatusStmt = conn.prepareStatement("insert into ReservationStatus values (DEFAULT,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			isVehicleInUseStmt = conn.prepareStatement("select count(*) from Reservations where vehicleID = ?");
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println("Instantiated ReservationDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with ReservationDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
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
	public int getReservationCountsInRange(int locationID, int vehicleID, java.util.Date startDate, java.util.Date stopDate){
		try{
			checkReservationRangeCountStmt.setInt(1,locationID);
			checkReservationRangeCountStmt.setInt(2, vehicleID);
			checkReservationRangeCountStmt.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
			checkReservationRangeCountStmt.setTimestamp(4, new java.sql.Timestamp(stopDate.getTime()));
			checkReservationRangeCountStmt.setTimestamp(5, new java.sql.Timestamp(startDate.getTime()));
			checkReservationRangeCountStmt.setTimestamp(6, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = checkReservationRangeCountStmt.executeQuery();
			if (rs.next()){
				int ct = rs.getInt(1);
				System.out.println(ct);
				return ct;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationStatus method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return -1;
	}
	
	@Override
	public ArrayList<Reservation> getReservationsInRange(int locationID, int vehicleID, java.util.Date startDate, java.util.Date stopDate){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			checkReservationRangeStmt.setInt(1,locationID);
			checkReservationRangeStmt.setInt(2, vehicleID);
			checkReservationRangeStmt.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
			checkReservationRangeStmt.setTimestamp(4, new java.sql.Timestamp(stopDate.getTime()));
			checkReservationRangeStmt.setTimestamp(5, new java.sql.Timestamp(startDate.getTime()));
			checkReservationRangeStmt.setTimestamp(6, new java.sql.Timestamp(stopDate.getTime()));
			ResultSet rs = checkReservationRangeStmt.executeQuery();
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
		return -1;
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
		return -1;
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
				java.util.Date sd = rs.getTimestamp("reservationStart");
				java.util.Date ed = rs.getTimestamp("reservationEnd");
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
				java.util.Date sd = rs.getTimestamp("reservationStart");
				java.util.Date ed = rs.getTimestamp("reservationEnd");
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
				java.util.Date sd = rs.getTimestamp("reservationStart");
				java.util.Date ed = rs.getTimestamp("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationsInRangeByLocation method: " + e.getClass().getName() + ": " + e.getMessage());	
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
				java.util.Date sd = rs.getTimestamp("reservationStart");
				java.util.Date ed = rs.getTimestamp("reservationEnd");
				results.add(new Reservation(reservationID, customerID, locationID, vehicleID, sd, ed));
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getReservationsByLocationAndVehicle method: " + e.getClass().getName() + ": " + e.getMessage());	
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
			System.err.println("Problem with getReservationsByLocationAndVehicleCount method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return -1;
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
	public ArrayList<Reservation> getAllReturnedReservations(){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			ResultSet rs = getAllReturnedReservationsStmt.executeQuery();
			while (rs.next()){
				int vehicleID = rs.getInt(1);
				String make = rs.getString(2);
				String model = rs.getString(3);
				int year = rs.getInt(4);
				String tag = rs.getString(5);
				int mileage = rs.getInt(6);
				Date lastServiced = rs.getDate(7);
				boolean isAvailable = rs.getBoolean(8);
				int vehicleType = rs.getInt(9);
				int assignedLocation = rs.getInt(10);
				int reservationID = rs.getInt(11);
				int customerID = rs.getInt(12);
				Date reservationStart = rs.getTimestamp(13);
				Date reservationEnd = rs.getTimestamp(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getTimestamp(19);
				String reservationStatus = rs.getString(20);
				//Create Vehicle object
				Vehicle v = new Vehicle();
				v.setId(vehicleID);
				v.setMake(make);
				v.setModel(model);
				v.setYear(year);
				v.setTag(tag);
				v.setMileage(mileage);
				v.setLastServiced(lastServiced);
				v.setAvailable(isAvailable);
				v.setVehicleType(vehicleType);
				v.setAssignedLocation(assignedLocation);
				//Create VehicleType object
				VehicleType vt = new VehicleType(vehicleType,vType,hourlyPrice,dailyPrice);
				//Create ReservationStatus object
				ReservationStatus rStatus = new ReservationStatus(reservationStatusID,reservationID,dateAdded,reservationStatus);
				//Create Reservation object
				Reservation temp = new Reservation(reservationID, customerID, assignedLocation, vehicleID, reservationStart, reservationEnd);
				//Add the other objects to the Reservation object
				temp.setVehicle(v);
				temp.setVehicleType(vt);
				temp.setReservationStatus(rStatus);
				results.add(temp);
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with checkIfLocationInUse method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}

	@Override
	public ArrayList<Reservation> getAllReservations(){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			ResultSet rs = getAllReservationsStmt.executeQuery();
			while (rs.next()){
				int vehicleID = rs.getInt(1);
				String make = rs.getString(2);
				String model = rs.getString(3);
				int year = rs.getInt(4);
				String tag = rs.getString(5);
				int mileage = rs.getInt(6);
				Date lastServiced = rs.getDate(7);
				boolean isAvailable = rs.getBoolean(8);
				int vehicleType = rs.getInt(9);
				int assignedLocation = rs.getInt(10);
				int reservationID = rs.getInt(11);
				int customerID = rs.getInt(12);
				Date reservationStart = rs.getTimestamp(13);
				Date reservationEnd = rs.getTimestamp(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getTimestamp(19);
				String reservationStatus = rs.getString(20);
				//Create Vehicle object
				Vehicle v = new Vehicle();
				v.setId(vehicleID);
				v.setMake(make);
				v.setModel(model);
				v.setYear(year);
				v.setTag(tag);
				v.setMileage(mileage);
				v.setLastServiced(lastServiced);
				v.setAvailable(isAvailable);
				v.setVehicleType(vehicleType);
				v.setAssignedLocation(assignedLocation);
				//Create VehicleType object
				VehicleType vt = new VehicleType(vehicleType,vType,hourlyPrice,dailyPrice);
				//Create ReservationStatus object
				ReservationStatus rStatus = new ReservationStatus(reservationStatusID,reservationID,dateAdded,reservationStatus);
				//Create Reservation object
				Reservation temp = new Reservation(reservationID, customerID, assignedLocation, vehicleID, reservationStart, reservationEnd);
				//Add the other objects to the Reservation object
				temp.setVehicle(v);
				temp.setVehicleType(vt);
				temp.setReservationStatus(rStatus);
				results.add(temp);
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with checkIfLocationInUse method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}

	@Override
	public ArrayList<Reservation> getAllCancelledReservations() {
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			ResultSet rs = getAllReturnedReservationsStmt.executeQuery();
			while (rs.next()){
				int vehicleID = rs.getInt(1);
				String make = rs.getString(2);
				String model = rs.getString(3);
				int year = rs.getInt(4);
				String tag = rs.getString(5);
				int mileage = rs.getInt(6);
				Date lastServiced = rs.getDate(7);
				boolean isAvailable = rs.getBoolean(8);
				int vehicleType = rs.getInt(9);
				int assignedLocation = rs.getInt(10);
				int reservationID = rs.getInt(11);
				int customerID = rs.getInt(12);
				Date reservationStart = rs.getTimestamp(13);
				Date reservationEnd = rs.getTimestamp(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getTimestamp(19);
				String reservationStatus = rs.getString(20);
				//Create Vehicle object
				Vehicle v = new Vehicle();
				v.setId(vehicleID);
				v.setMake(make);
				v.setModel(model);
				v.setYear(year);
				v.setTag(tag);
				v.setMileage(mileage);
				v.setLastServiced(lastServiced);
				v.setAvailable(isAvailable);
				v.setVehicleType(vehicleType);
				v.setAssignedLocation(assignedLocation);
				//Create VehicleType object
				VehicleType vt = new VehicleType(vehicleType,vType,hourlyPrice,dailyPrice);
				//Create ReservationStatus object
				ReservationStatus rStatus = new ReservationStatus(reservationStatusID,reservationID,dateAdded,reservationStatus);
				//Create Reservation object
				Reservation temp = new Reservation(reservationID, customerID, assignedLocation, vehicleID, reservationStart, reservationEnd);
				//Add the other objects to the Reservation object
				temp.setVehicle(v);
				temp.setVehicleType(vt);
				temp.setReservationStatus(rStatus);
				results.add(temp);
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with checkIfLocationInUse method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
	}


	@Override
	public ArrayList<Reservation> getAllOpenReservations() {
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		try{
			ResultSet rs = getAllReturnedReservationsStmt.executeQuery();
			while (rs.next()){
				int vehicleID = rs.getInt(1);
				String make = rs.getString(2);
				String model = rs.getString(3);
				int year = rs.getInt(4);
				String tag = rs.getString(5);
				int mileage = rs.getInt(6);
				Date lastServiced = rs.getDate(7);
				boolean isAvailable = rs.getBoolean(8);
				int vehicleType = rs.getInt(9);
				int assignedLocation = rs.getInt(10);
				int reservationID = rs.getInt(11);
				int customerID = rs.getInt(12);
				Date reservationStart = rs.getTimestamp(13);
				Date reservationEnd = rs.getTimestamp(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getTimestamp(19);
				String reservationStatus = rs.getString(20);
				//Create Vehicle object
				Vehicle v = new Vehicle();
				v.setId(vehicleID);
				v.setMake(make);
				v.setModel(model);
				v.setYear(year);
				v.setTag(tag);
				v.setMileage(mileage);
				v.setLastServiced(lastServiced);
				v.setAvailable(isAvailable);
				v.setVehicleType(vehicleType);
				v.setAssignedLocation(assignedLocation);
				//Create VehicleType object
				VehicleType vt = new VehicleType(vehicleType,vType,hourlyPrice,dailyPrice);
				//Create ReservationStatus object
				ReservationStatus rStatus = new ReservationStatus(reservationStatusID,reservationID,dateAdded,reservationStatus);
				//Create Reservation object
				Reservation temp = new Reservation(reservationID, customerID, assignedLocation, vehicleID, reservationStart, reservationEnd);
				//Add the other objects to the Reservation object
				temp.setVehicle(v);
				temp.setVehicleType(vt);
				temp.setReservationStatus(rStatus);
				results.add(temp);
			}	
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with checkIfLocationInUse method: " + e.getClass().getName() + ": " + e.getMessage());	
		}
		return results;
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
	public int addReservationStatus(int reservationID, Date dateAdded, String reservationStatus) {
		int results = 0;
		try{
			addReservationStatusStmt.setInt(1, reservationID);
			addReservationStatusStmt.setTimestamp(2, new java.sql.Timestamp(dateAdded.getTime()));
			addReservationStatusStmt.setString(3, reservationStatus);
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
