package com.youdrive.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Vehicle implements Serializable{
	private static final long serialVersionUID = -2310402415976750455L;
	private int id;
	private String make;
	private String model;
	private int year;
	private String tag;
	private int mileage;
	private Date lastServiced;
	private int vehicleType;
	private int assignedLocation;
	private ArrayList<Comment> vehicleComments = new ArrayList<Comment>();
	
	public Vehicle() {  }
	
	public Vehicle(int id, String make, String model, int year, String tag, int mileage, Date lastServiced, int vehicleType, int assignedLocation){
		this.id = id;
		this.make = make;
		this.model = model;
		this.year = year;
		this.tag = tag;
		this.mileage = mileage;
		this.lastServiced = lastServiced;
		this.vehicleType = vehicleType;
		this.assignedLocation = assignedLocation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public Date getLastServiced() {
		return lastServiced;
	}

	public void setLastServiced(Date lastServiced) {
		this.lastServiced = lastServiced;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getAssignedLocation() {
		return assignedLocation;
	}

	public void setAssignedLocation(int assignedLocation) {
		this.assignedLocation = assignedLocation;
	}
	
	public ArrayList<Comment> getVehicleComments() {
		return vehicleComments;
	}

	public void setVehicleComments(ArrayList<Comment> vehicleComments) {
		this.vehicleComments = vehicleComments;
	}
}
