package com.youdrive.interfaces;

import java.sql.Date;
import java.util.ArrayList;

import com.youdrive.models.Membership;

public interface IMembershipManager {
	/**
	 * Returns the id of the Membership object if successfully added to the database
	 * REturns 0 if the add failed.
	 * @param name
	 * @param price
	 * @param duration
	 * @return int
	 */
	public int addMembership(String name, double price, int duration);
	/**
	 * Delete a membership when provide
	 * @param id
	 * @return boolean
	 */
	public boolean deleteMembership(int id);
	/**
	 * Returns a list of the memberships in the system
	 * @return ArrayList<Membership> 
	 */
	public ArrayList<Membership> getAllMemberships();
	/**
	 * Returns the Membership object or null if it doesn't exist
	 * @param id
	 * @return Membership
	 */
	public Membership getMembership(int id);
	/**
	 * Check if the Membership name has been used already
	 * True if the name has been used and false otherwise.
	 * @param name
	 * @return boolean
	 */
	public boolean isNameInUse(String name);
	/**
	 * Update the membership plan. Returns true if successful 
	 * false otherwise.
	 * @param id
	 * @param name
	 * @param price
	 * @param duration
	 * @return boolean
	 */
	public boolean updateMembership(int id, String name, double price, int duration);
	/**
	 * Get the number of users on a particular membership plan
	 * @param membershipID
	 * @return int
	 */
	public int getUsersOnMembership(int membershipID);
	/**
	 * Update the expiration date of a membership plan
	 * TODO not sure if this is used
	 * @param expirationDate
	 * @param membershipLevel
	 * @return
	 */
	public boolean updateExpirationDate(Date expirationDate,int membershipLevel);
}
