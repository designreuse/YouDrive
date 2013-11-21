package com.youdrive.models;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable{
	private static final long serialVersionUID = 8015514732999869774L;
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String state;
	private String license;
	private String email;
	private String address;
	private String ccType;
	private String ccNumber;
	private int ccSecurityCode;
	private String ccExpirationDate;
	private boolean isAdmin;
	private Date memberExpiration;
	private Date dateCreated;
	private int membershipLevel;
	
	public User(){ 	}
	
	/**
	 * Constructor
	 * @param id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 */
	public User (int id, String username, String password, String firstName, String lastName, String email){
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName  = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	/**
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param state
	 * @param license
	 * @param email
	 * @param address
	 * @param ccType
	 * @param ccNumber
	 * @param ccSecurityCode
	 * @param ccExpirationDate
	 * @param isAdmin
	 * @param memberExpiration
	 * @param membershipLevel
	 */
	public User(int id, String username, String password, String firstName,
			String lastName, String state, String license, String email,
			String address, String ccType, String ccNumber, int ccSecurityCode,
			String ccExpirationDate,  boolean isAdmin,
			Date memberExpiration, int membershipLevel) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.state = state;
		this.license = license;
		this.email = email;
		this.address = address;
		this.ccType = ccType;
		this.ccNumber = ccNumber;
		this.ccSecurityCode = ccSecurityCode;
		this.ccExpirationDate = ccExpirationDate;
		this.isAdmin = isAdmin;
		this.memberExpiration = memberExpiration;
		this.membershipLevel = membershipLevel;
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
	public String getCcNumber() {
		return ccNumber;
	}
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	public int getCcSecurityCode() {
		return ccSecurityCode;
	}
	public void setCcSecurityCode(int ccSecurityCode) {
		this.ccSecurityCode = ccSecurityCode;
	}
	public String getCcExpirationDate() {
		return ccExpirationDate;
	}
	public void setCcExpirationDate(String ccExpirationDate) {
		this.ccExpirationDate = ccExpirationDate;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
