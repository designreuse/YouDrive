package com.youdrive.interfaces;

import java.util.ArrayList;

import com.youdrive.models.Membership;

public interface IMembershipManager {
	/**
	 * TODO move to Membership interface
	 * @param name
	 * @param price
	 * @param duration
	 * @return
	 */
	public int addMembership(String name, double price, int duration);
	/**
	 * TODO remove to separate interface
	 * @param id
	 * @return boolean
	 */
	public boolean deleteMembership(int id);
	public ArrayList<Membership> getAllMemberships();
	public Membership getMembership(int id);
	public boolean isNameInUse(String name);
	public boolean updateMembership(int id, String name, double price, int duration);
	int getUsersOnMembership(int membershipID);
}
