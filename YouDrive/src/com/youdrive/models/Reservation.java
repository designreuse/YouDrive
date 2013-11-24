package com.youdrive.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Reservation  implements Serializable{

	private static final long serialVersionUID = -2727445271306115604L;
	private int id;
	private int customerID;
	private int locationID;
	private int vehicleID;
	/* Use getTimestamp*/
	private Date reservationStart;
	private Date reservationEnd;
	private ArrayList<ReservationStatus> reservationStatusList = new ArrayList<ReservationStatus>();
	private ReservationStatus reservationStatus;
	private Vehicle vehicle;
	private VehicleType vehicleType;
	
	
	public Reservation(int id, int customerID, int locationID, int vehicleID, Date reservationStart, Date reservationEnd) {
		this.id = id;
		this.customerID = customerID;
		this.locationID = locationID;
		this.vehicleID = vehicleID;
		this.reservationStart = reservationStart;
		this.reservationEnd = reservationEnd;
	}

	public void addReservationStatus(ReservationStatus rs){
		reservationStatusList.add(rs);
	}
	
	public ArrayList<ReservationStatus> getReservationStatusList(){
		return reservationStatusList;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public Date getReservationStart() {
		return reservationStart;
	}

	public void setReservationStart(Date reservationStart) {
		this.reservationStart = reservationStart;
	}

	public Date getReservationEnd() {
		return reservationEnd;
	}

	public void setReservationEnd(Date reservationEnd) {
		this.reservationEnd = reservationEnd;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
}
