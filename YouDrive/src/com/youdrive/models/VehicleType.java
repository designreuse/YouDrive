package com.youdrive.models;

public class VehicleType {
	private int id;
	private String type;
	private double hourlyPrice;
	private double dailyPrice;
	
	public VehicleType(int id, String type, double hourlyPrice, double dailyPrice){
		this.id = id;
		this.type = type;
		this.hourlyPrice = hourlyPrice;
		this.dailyPrice = dailyPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getHourlyPrice() {
		return hourlyPrice;
	}

	public void setHourlyPrice(double hourlyPrice) {
		this.hourlyPrice = hourlyPrice;
	}

	public double getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
}
