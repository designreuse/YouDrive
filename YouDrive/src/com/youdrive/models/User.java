package com.youdrive.models;

import java.sql.Date;

public class User {
	int id;
	String username;
	String password;
	String firstName;
	String lastName;
	String state;
	String license;
	String email;
	String address;
	String ccType;
	int ccNumber;
	int ccSecurityCode;
	Date ccExpirationDate;
	boolean memberStatus;
	boolean isAdmin;
	Date memberExpiration;
	int membershipLevel;
	
	public User(){ 	}
	
	/**
	 * Constructor
	 * @param id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 */
	public User (int id, String username, String password, String firstName, String lastName){
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName  = firstName;
		this.lastName = lastName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCcType() {
		return ccType;
	}
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	public int getCcNumber() {
		return ccNumber;
	}
	public void setCcNumber(int ccNumber) {
		this.ccNumber = ccNumber;
	}
	public int getCcSecurityCode() {
		return ccSecurityCode;
	}
	public void setCcSecurityCode(int ccSecurityCode) {
		this.ccSecurityCode = ccSecurityCode;
	}
	public Date getCcExpirationDate() {
		return ccExpirationDate;
	}
	public void setCcExpirationDate(Date ccExpirationDate) {
		this.ccExpirationDate = ccExpirationDate;
	}
	public boolean isMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(boolean memberStatus) {
		this.memberStatus = memberStatus;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Date getMemberExpiration() {
		return memberExpiration;
	}
	public void setMemberExpiration(Date memberExpiration) {
		this.memberExpiration = memberExpiration;
	}
	public int getMembershipLevel() {
		return membershipLevel;
	}
	public void setMembershipLevel(int membershipLevel) {
		this.membershipLevel = membershipLevel;
	}
}