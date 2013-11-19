package com.youdrive.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.VehicleTypeDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IVehicleTypeManager;
import com.youdrive.models.Vehicle;
import com.youdrive.models.VehicleType;

public final class SortingUtil {
	private static IVehicleTypeManager ivtm  = new VehicleTypeDAO();
	private static ILocationManager ilm = new LocationDAO();

	public static String hello(String name) {
		return "Hiya, " + name + ".";
	}

	/**
	 * Sorting a list of Vehicles
	 * @param vehicles
	 * @param sortType
	 * @return
	 */
	public static ArrayList<Vehicle> vehicleSort(ArrayList<Vehicle> vehicles, int sortType){
		switch(sortType){
		case(0):
			//Sort by Make
			System.out.println("Sort By Make");
		Collections.sort(vehicles, new Comparator<Vehicle>() {
			public int compare(Vehicle o1, Vehicle o2){
				return o1.getMake().compareTo(o2.getMake()); // Compare by name, for example
			}
		});
		break;
		case(1):
			//Sort by Model
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					return o1.getModel().compareTo(o2.getModel()); // Compare by name, for example
				}
			});
		break;
		case(2):
			//Sort by Year
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					return Integer.compare(o1.getYear(),o2.getYear());
				}
			});				
		break;
		case(3):
			//Sort By Tag
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					return o1.getTag().compareTo(o2.getTag()); // Compare by name, for example
				}
			});
		break;
		case(4):
			//Sort By Mileage
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					return Integer.compare(o1.getMileage(),o2.getMileage());
				}
			});				
		break;
		case(5):
			//Sort by Last Serviced
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					return o1.getLastServiced().compareTo(o2.getLastServiced()); // Compare by name, for example
				}
			});				
		break;
		case(6):
			//Sort By Vehicle Type
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					String typeA = ivtm.getVehicleType(o1.getVehicleType()).getType();
					String typeB = ivtm.getVehicleType(o2.getVehicleType()).getType();
					return typeA.compareTo(typeB); // Compare by name, for example
				}
			});
		break;
		case(7):
			//Sort by Vehicle Location
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					String typeA = ilm.getLocationById(o1.getAssignedLocation()).getName();
					String typeB = ilm.getLocationById(o2.getAssignedLocation()).getName();
					return typeA.compareTo(typeB); // Compare by name, for example
				}
			});
		break;
		default:
			//Default is: Sort by Make
			System.out.println("Sort By Make");
			Collections.sort(vehicles, new Comparator<Vehicle>() {
				public int compare(Vehicle o1, Vehicle o2){
					return o1.getMake().compareTo(o2.getMake()); // Compare by name, for example
				}
			});
		}
		return vehicles;
	}

	/**
	 * Sorting a list of Vehicle types
	 * @param vehicleTypes
	 * @param sortType
	 * @return
	 */
	public static ArrayList<VehicleType> vehicleTypeSort(ArrayList<VehicleType> vehicleTypes, int sortType){
		switch(sortType){
		case(0):
			System.out.println("Sort By Type");
			Collections.sort(vehicleTypes, new Comparator<VehicleType>() {
				public int compare(VehicleType o1, VehicleType o2){
					return o1.getType().compareTo(o2.getType()); // Compare by name, for example
				}
			});
			break;
		case(1):
			System.out.println("Sort By Hourly Price");
			Collections.sort(vehicleTypes, new Comparator<VehicleType>() {
				public int compare(VehicleType o1, VehicleType o2){
					return Double.compare(o1.getHourlyPrice(), o2.getHourlyPrice());
				}
			});
			break;
		case(2):
			System.out.println("Sort By Hourly Price");
			Collections.sort(vehicleTypes, new Comparator<VehicleType>() {
				public int compare(VehicleType o1, VehicleType o2){
					return Double.compare(o1.getHourlyPrice(), o2.getHourlyPrice());
				}
			});
			break;
		default:
			System.out.println("Sort By Daily Price");
			Collections.sort(vehicleTypes, new Comparator<VehicleType>() {
				public int compare(VehicleType o1, VehicleType o2){
					return String.valueOf(o1.getDailyPrice()).compareTo(String.valueOf(o2.getDailyPrice())); // Compare by name, for example
				}
			});

		}
		return vehicleTypes;
	}
}
