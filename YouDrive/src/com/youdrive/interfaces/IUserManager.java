package com.youdrive.interfaces;

import java.util.ArrayList;

import com.youdrive.models.User;

public interface IUserManager {
	/**
	 * Adding a regular User and returns the user's id
	 * @param p
	 * @return
	 */
	public int addUser(User p);
	/**
	 * Adding an Admin user and returns the admin's id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return
	 */
	public int addAdminUser(String username, String password, String firstName, String lastName, String email);

	/**
	 * Get the User object by the user's id
	 * @param userID
	 * @return
	 */
	public User getUser(int userID);
	/**
	 * Get the User object by their username
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username);
	/**
	 * Returns the user by combination of username and password
	 * @param username
	 * @param password
	 * @return
	 */
	public User authenticateUser(String username, String password);
	/**
	 * Returns an arraylist of all the users in the system
	 * @return  ArrayList<User> 
	 */
	public ArrayList<User> getAllAdmins();
	/**
	 * Updates the admin user's information
	 * @param id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return
	 */
	public boolean updateAdminUser(int id, String username, String password, String firstName,
			String lastName, String email);
	/**
	 * Updates the regular user's information
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
	 * @return
	 */
	public boolean updateUser(int id,String username, String password, String firstName,
			String lastName, String state, String license, String email,
			String address, String ccType, String ccNumber, int ccSecurityCode,
			String ccExpirationDate);
	/**
	 * Returns true if the provided username is taken
	 * @param username
	 * @return
	 */
	public boolean isUsernameInUse(String username);
	/**
	 * Returns true if the provided email is taken
	 * @param email
	 * @return
	 */
	public boolean isEmailInUse(String email);
	/**
	 * Get a list of non-admin customers.
	 * @return
	 */
	public ArrayList<User> getAllCustomers();
	boolean deleteAdminUser(int userID);
	
}
