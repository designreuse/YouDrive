package com.youdrive.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.VehicleDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IVehicleManager;
import com.youdrive.models.Location;
import com.youdrive.models.Vehicle;
import com.youdrive.models.VehicleType;

/**
 * Servlet implementation class VehicleManagement
 */
@WebServlet("/VehicleManagement")
public class VehicleManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY",Locale.getDefault());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VehicleManagement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		IVehicleManager ivm = (VehicleDAO) ctx.getAttribute("vehicleMgr");
		if (ivm == null){
			ivm = new VehicleDAO();
			ctx.setAttribute("vehicleMgr", ivm);
		}	
		String vehicleID = request.getParameter("vehicleID");
		if (vehicleID != null && !vehicleID.isEmpty()){
			int vID = Integer.parseInt(vehicleID);
			Vehicle vehicle = ivm.getVehicle(vID);
			if (vehicle != null){
				ctx.setAttribute("vehicle", vehicle);
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/editvehicle.jsp");
			}else{
				request.setAttribute("errorMessage", "Unable to find Vehicle object.");
				dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
			}
		}else{
			dispatcher = ctx.getRequestDispatcher("/login.jsp");
		}
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		IVehicleManager ivm = (VehicleDAO) ctx.getAttribute("vehicleMgr");

		ILocationManager ilm = (LocationDAO) ctx.getAttribute("locationMgr");
		if (ivm == null){
			ivm = new VehicleDAO();
			ctx.setAttribute("vehicleMgr", ivm);
		}
		if (ilm == null){
			ilm = new LocationDAO();
			ctx.setAttribute("locationMgr", ilm);
		}
		String action = request.getParameter("action");
		//Adding a single vehicle
		if (action.equalsIgnoreCase("addVehicle")){
			int id = addVehicle(request,ivm,ilm);
			if (id == 0){
				System.err.println("Problem saving vehicle to db.");
				dispatcher = ctx.getRequestDispatcher("/addvehicle.jsp");
			}else{
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
			}
		}else if (action.equalsIgnoreCase("editVehicle")){
			String vehicleId = request.getParameter("vehicleID");
			if (vehicleId == null || vehicleId.isEmpty()){
				request.setAttribute("errorMessage", "Invalid vehicle ID.");
				dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
			}else{
				try{
					int vID = Integer.parseInt(vehicleId);
					Vehicle v = ivm.getVehicle(vID);
					//Ensure a vehicle exists in db
					if (v != null){
						if (editVehicle(request,ivm,ilm,v)){
							request.setAttribute("errorMessage", "");
							dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
						}else{
							request.setAttribute("vehicle", v);
							dispatcher = ctx.getRequestDispatcher("/editvehicle.jsp");
						}
					}else{
						request.setAttribute("errorMessage", "Unable to retrieve Vehicle object.");
						dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
					}
				}catch(NumberFormatException e){
					request.setAttribute("errorMessage","Invalid vehicle ID format.");
					dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
				}
			}
		}else{
			dispatcher = ctx.getRequestDispatcher("/login.jsp");
		}
		dispatcher.forward(request,response);
	}

	private int addVehicle(HttpServletRequest request, IVehicleManager ivm, ILocationManager ilm){
		String errorMessage = "";
		int vehicleID = 0;
		try{
			String make = request.getParameter("make");
			String model = request.getParameter("model");
			String yr = request.getParameter("year");
			String tag = request.getParameter("tag");
			String miles = request.getParameter("mileage");
			String lastServiced = request.getParameter("lastServiced");
			String type = request.getParameter("vehicleType");
			String location = request.getParameter("vehicleLocation");
			System.out.println(make+"-"+model+"-"+yr+"-"+tag+"-"+miles+"-"+lastServiced+"-"+type+"-"+location);
			if (make == null || make.isEmpty()){
				errorMessage = "Missing make";
			}else if (model == null || model.isEmpty()){
				errorMessage = "Missing model";
			}else if (yr == null || yr.isEmpty()){
				errorMessage = "Missing year";
			}else if(tag == null || tag.isEmpty()){
				errorMessage = "Missing tag";
			}else if(miles == null || miles.isEmpty()){
				errorMessage = "Missing mileage";
			}else if(lastServiced == null || lastServiced.isEmpty()){
				errorMessage = "Missing last serviced date";
			}else if(type == null || type.isEmpty()){
				errorMessage = "Missing vehicle type";
			}else if(location == null || location.isEmpty()){
				errorMessage = "Missing assigned location";
			}else{
				int year = Integer.parseInt(yr);
				int mileage = Integer.parseInt(miles);
				int vehicleType = Integer.parseInt(type);
				int assignedLocation = Integer.parseInt(location);
				//Check if location is full
				//get count of vehicles assigned to the new location
				int currentCapacity = ilm.getCurrentCapacity(assignedLocation);
				//Get location's assigned capacity
				Location l = ilm.getLocationById(assignedLocation);
				if (l != null){
					int locationCapacity = l.getCapacity();
					System.out.println("Location capacity: " + locationCapacity);
					//Don't change vehicle is location is full.
					if (locationCapacity > currentCapacity){
						vehicleID = ivm.addVehicle(make, model, year, tag, mileage, lastServiced, vehicleType, assignedLocation);
					}else{
						errorMessage = "This location is full. Select a different location.";
					}
				}else{
					errorMessage = "Unable to retrieve Location object";
				}				
			}
		}catch(NumberFormatException e){
			errorMessage = "Error parsing one of the numeric values.";
		}catch(Exception e){
			errorMessage = e.getMessage();
			System.err.println(e.getMessage());
		}
		request.setAttribute("errorMessage", errorMessage);
		return vehicleID;	
	}

	private boolean editVehicle(HttpServletRequest request, IVehicleManager ivm, ILocationManager ilm,Vehicle vehicle){
		String errorMessage = "";
		int vehicleID = vehicle.getId();
		try{
			String make = request.getParameter("make");
			String model = request.getParameter("model");
			String yr = request.getParameter("year");
			String tag = request.getParameter("tag");
			String miles = request.getParameter("mileage");
			String lastServiced = request.getParameter("lastServiced");
			String type = request.getParameter("vehicleType");
			String location = request.getParameter("vehicleLocation");
			if (make == null || make.isEmpty()){
				errorMessage = "Missing make";
			}else if (model == null || model.isEmpty()){
				errorMessage = "Missing model";
			}else if (yr == null || yr.isEmpty()){
				errorMessage = "Missing year";
			}else if(tag == null || tag.isEmpty()){
				errorMessage = "Missing tag";
			}else if(miles == null || miles.isEmpty()){
				errorMessage = "Missing mileage";
			}else if(lastServiced == null || lastServiced.isEmpty()){
				errorMessage = "Missing last serviced date";
			}else if(type == null || type.isEmpty()){
				errorMessage = "Missing vehicle type";
			}else if(location == null || location.isEmpty()){
				errorMessage = "Missing assigned location";
			}else{
				int year = Integer.parseInt(yr);
				int mileage = Integer.parseInt(miles);
				int vehicleType = Integer.parseInt(type);
				int assignedLocation = Integer.parseInt(location);
				//Logic to check if the vehicle is being assigned to a full location
				if (assignedLocation != vehicle.getAssignedLocation()){
					//get count of vehicles assigned to the new location
					int currentCapacity = ilm.getCurrentCapacity(assignedLocation);
					//Get location's assigned capacity
					Location l = ilm.getLocationById(assignedLocation);
					if (l != null){
						int locationCapacity = l.getCapacity();
						//Don't change vehicle is location is full.
						if (locationCapacity > currentCapacity){
							if (!(ivm.updateVehicle(vehicleID,make, model, year, tag, mileage, lastServiced, vehicleType, assignedLocation))){
								errorMessage = "Could not update Vehicle details.";
							}else{
								return true;
							}
						}else{
							errorMessage = "This location is full. Select a different location.";
						}
					}else{
						errorMessage = "Unable to retrieve Location object";
					}
				}else{
					//Location isn't being changed.
					if (ivm.updateVehicle(vehicleID,make, model, year, tag, mileage, lastServiced, vehicleType, assignedLocation)){
						return true;
					}else{
						errorMessage = "Could not update Vehicle details.";
					}
				}				
			}
		}catch(NumberFormatException e){
			errorMessage = "Error parsing one of the numeric values.";
			System.err.println(e.getMessage());
		}catch(Exception e){
			errorMessage = e.getMessage();
			System.err.println(e.getMessage());
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;	
	}
}
