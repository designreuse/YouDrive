package com.youdrive.models;

import java.io.Serializable;
import java.util.Date;

public class ReservationStatus implements Serializable{

	private static final long serialVersionUID = 8762336573249759906L;
	private int id;
	private int reservationID;
	private Date dateAdded;
	private String reservationStatus;

	public ReservationStatus(int id, int reservationID, Date dateAdded, String reservationStatus) {
		this.id = id;
		this.reservationID = reservationID;
		this.dateAdded = dateAdded;
		this.reservationStatus = reservationStatus;
	}
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReservationID() {
		return reservationID;
	}
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	
}
