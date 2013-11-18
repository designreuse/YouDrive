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
	 * @return
	 */
	public String deleteMembership(int id);
	ArrayList<Membership> getAllMemberships();
}
