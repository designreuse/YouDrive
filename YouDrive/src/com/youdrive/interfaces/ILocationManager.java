package com.youdrive.interfaces;

import java.util.ArrayList;
import com.youdrive.models.Location;

public interface ILocationManager {
	
	/**
	 * Retrieve a list of all locations in the system
	 * @return ArrayList<Location>
	 */
	public ArrayList<Location> getAllLocations();
	/**
	 * Get a Location by its id
	 * @param id
	 * @return Location
	 */
	public Location getLocationById(int id);
	/**
	 * Get a Location by its unique name
	 * @param name
	 * @return
	 */
	public Location getLocationByName(String name);
	/**
	 * Adds a location and returns the location id
	 * If location id is zero, there was a problem.
	 * @param name
	 * @param address
	 * @param capacity
	 * @return
	 */
	public int addLocation(String name, String address, int capacity);
	/**
	 * Same as addLocation(name,address,capacity) but with an attempt to be able to pass the errorcode forward
	 * @param name
	 * @param address
	 * @param capacity
	 * @param errorCode
	 * @return
	 */
	public int addLocation(String name, String address, int capacity, StringBuilder errorCode);
	/**
	 * Delete a location by its id
	 * @param id
	 * @return
	 */
	public String deleteLocationById(int id);
	/**
	 * Delete a location by its name
	 * @param name
	 * @return
	 */
	public String deleteLocationByName(String name);
	public boolean isLocationInUse(int locationID);
	public boolean updateLocation(int locationID, String name, String address, int capacity);
}
