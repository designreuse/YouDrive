package com.youdrive.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.VehicleTypeDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IVehicleTypeManager;
import com.youdrive.models.Vehicle;

public final class VehicleSort {
	private static IVehicleTypeManager ivtm  = new VehicleTypeDAO();
	private static ILocationManager ilm = new LocationDAO();
	
	public static String hello(String name) {
        return "Hiya, " + name + ".";
	}
	
	public static ArrayList<Vehicle> masterSort(ArrayList<Vehicle> vehicles, int sortType){
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
				        return String.valueOf(o1.getLastServiced()).compareTo(String.valueOf(o2.getLastServiced())); // Compare by name, for example
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
				        return String.valueOf(o1.getMileage()).compareTo(String.valueOf(o2.getMileage())); // Compare by name, for example
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
				    	String typeA = ilm.getLocationById(o1.getVehicleType()).getName();
				    	String typeB = ilm.getLocationById(o2.getVehicleType()).getName();
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
	
	public static ArrayList<Vehicle> sortByMake(ArrayList<Vehicle> vehicles){
		Collections.sort(vehicles, new Comparator<Vehicle>() {
		    public int compare(Vehicle o1, Vehicle o2){
		        return o1.getMake().compareTo(o2.getMake()); // Compare by name, for example
		    }
		 });
		return vehicles;
	}
	
	public static ArrayList<Vehicle> sortByModel(ArrayList<Vehicle> vehicles){
		Collections.sort(vehicles, new Comparator<Vehicle>() {
		    public int compare(Vehicle o1, Vehicle o2){
		        return o1.getModel().compareTo(o2.getModel()); // Compare by name, for example
		    }
		 });
		return vehicles;
	}
	
	public static ArrayList<Vehicle> sortByTag(ArrayList<Vehicle> vehicles){
		Collections.sort(vehicles, new Comparator<Vehicle>() {
		    public int compare(Vehicle o1, Vehicle o2){
		        return o1.getTag().compareTo(o2.getTag()); // Compare by name, for example
		    }
		 });
		return vehicles;
	}
	
	public static ArrayList<Vehicle> sortByVehicleType(ArrayList<Vehicle> vehicles){
		Collections.sort(vehicles, new Comparator<Vehicle>() {
		    public int compare(Vehicle o1, Vehicle o2){
		    	String typeA = ivtm.getVehicleType(o1.getVehicleType()).getType();
		    	String typeB = ivtm.getVehicleType(o2.getVehicleType()).getType();
		        return typeA.compareTo(typeB); // Compare by name, for example
		    }
		 });
		return vehicles;
	}
	
	public static ArrayList<Vehicle> sortByVehicleLocation(ArrayList<Vehicle> vehicles){
		Collections.sort(vehicles, new Comparator<Vehicle>() {
		    public int compare(Vehicle o1, Vehicle o2){
		    	String typeA = ilm.getLocationById(o1.getVehicleType()).getName();
		    	String typeB = ilm.getLocationById(o2.getVehicleType()).getName();
		        return typeA.compareTo(typeB); // Compare by name, for example
		    }
		 });
		return vehicles;
	}
	
	public static ArrayList<Vehicle> sortByLastServiced(ArrayList<Vehicle> vehicles){
		Collections.sort(vehicles, new Comparator<Vehicle>() {
		    public int compare(Vehicle o1, Vehicle o2){
		        return o1.getLastServiced().compareTo(o2.getLastServiced()); // Compare by name, for example
		    }
		 });
		return vehicles;
	}
	
	public static ArrayList<Vehicle> sortByYear(ArrayList<Vehicle> vehicles){
		Collections.sort(vehicles, new Comparator<Vehicle>() {
		    public int compare(Vehicle o1, Vehicle o2){
		        return String.valueOf(o1.getLastServiced()).compareTo(String.valueOf(o2.getLastServiced())); // Compare by name, for example
		    }
		 });
		return vehicles;
	}
}
