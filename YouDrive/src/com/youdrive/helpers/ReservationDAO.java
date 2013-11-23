package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private PreparedStatement getReservationsInRangeCountStmt;
	private PreparedStatement getReservationsByVehicleIDStmt;
	private PreparedStatement getReservationsByLocationIDStmt;
	private PreparedStatement getReservationsByLocationAndVehicleStmt;
	private PreparedStatement getVehiclesStatusStmt;
	private PreparedStatement getAllReservationsStmt;
	private PreparedStatement getAllReturnedReservationsStmt;
	private PreparedStatement getAllOpenReservationsStmt;
	private PreparedStatement getAllCancelledReservationsStmt;
	private PreparedStatement checkLocationsInFutureReservationsStmt;
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
			//getAllReservationsStmt = conn.prepareStatement("select * from Reservations");
			checkLocationsInFutureReservationsStmt = conn.prepareStatement("select count(*) from Reservations where locationID = ? AND reservationStart > NOW()");
			getVehiclesStatusStmt = conn.prepareStatement("select v.*, r.customerID,r.reservationStart,r.reservationEnd,vt.id as typeID,vt.type,vt.hourlyPrice,vt.dailyPrice from Vehicles v left outer join Reservations r on r.vehicleID = v.id left outer join VehicleTypes vt on vt.id = v.vehicleType;");
			getAllReturnedReservationsStmt = conn.prepareStatement("select v.*, r.id,r.customerID,r.reservationStart,r.reservationEnd,vt.type,vt.hourlyPrice,vt.dailyPrice,rs.id, rs.dateAdded,rs.reservationStatus from Reservations r left outer join Vehicles v on r.vehicleID = v.id left outer join VehicleTypes vt on vt.id = v.vehicleType left outer join ReservationStatus rs on rs.reservationID = r.id where rs.reservationStatus = \"Returned\"");
			getAllReservationsStmt = conn.prepareStatement("select v.*, r.id,r.customerID,r.reservationStart,r.reservationEnd,vt.type,vt.hourlyPrice,vt.dailyPrice,rs.id, rs.dateAdded,rs.reservationStatus from Reservations r left outer join Vehicles v on r.vehicleID = v.id left outer join VehicleTypes vt on vt.id = v.vehicleType left outer join ReservationStatus rs on rs.reservationID = r.id");
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
				java.util.Date sd = rs.getDate("reservationStart");
				java.util.Date ed = rs.getDate("reservationEnd");
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
				Date reservationStart = rs.getDate(13);
				Date reservationEnd = rs.getDate(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getDate(19);
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
				Date reservationStart = rs.getDate(13);
				Date reservationEnd = rs.getDate(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getDate(19);
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
				Date reservationStart = rs.getDate(13);
				Date reservationEnd = rs.getDate(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getDate(19);
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
				Date reservationStart = rs.getDate(13);
				Date reservationEnd = rs.getDate(14);
				String vType = rs.getString(15);
				double hourlyPrice = rs.getDouble(16);
				double dailyPrice = rs.getDouble(17);
				int reservationStatusID = rs.getInt(18);
				Date dateAdded = rs.getDate(19);
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
}
