package com.youdrive.models;

import java.io.Serializable;

public class Membership implements Serializable{
	private static final long serialVersionUID = -6890011903343601105L;
	private int id;
	private String name;
	private double price;
	private int duration;
	
	public Membership(int id, String name, double price, int duration){
		this.id = id;
		this.name = name;
		this.price = price;
		this.duration = duration;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
