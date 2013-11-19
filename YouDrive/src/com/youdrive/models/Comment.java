package com.youdrive.models;

import java.sql.Date;

public class Comment {
	private int id;
	private Date createdOn;
	private String comment;
	private int author;
	private int vehicleID;
	
	public Comment(int id, Date createdOn, String comment, int author,
			int vehicleID) {
		this.id = id;
		this.createdOn = createdOn;
		this.comment = comment;
		this.author = author;
		this.vehicleID = vehicleID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}
}
