package com.youdrive.servlets;

import java.io.IOException;

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

/**
 * Servlet implementation class LocationManagement
 */
@WebServlet("/LocationManagement")
public class LocationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		ILocationManager ilm = (LocationDAO) ctx.getAttribute("locationMgr");
		if (ilm == null){
			ilm = new LocationDAO();
		}
		String locationID = request.getParameter("locationID");
		if (locationID != null && !locationID.isEmpty()){
			int locID = Integer.parseInt(locationID);
			Location loc = ilm.getLocationById(locID);
			if (loc != null){
				request.setAttribute("location", loc);
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/editlocation.jsp");
			}else{
				request.setAttribute("errorMessage", "Unable to find Location object.");
				dispatcher = ctx.getRequestDispatcher("/managelocations.jsp");
			}
/*			boolean isLocationInUse = ilm.isLocationInUse(locID);
			if (isLocationInUse){
				
			}else{
				errorMessage = "This location has vehicles assigned to it. Please re-assign those vehicles first before deleting."
			}*/
		}
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		ILocationManager ilm = (LocationDAO) ctx.getAttribute("locationMgr");
		if (ilm == null){
			ilm = new LocationDAO();
		}
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("addLocation")){
			System.out.println("Got here.");
			int id = addLocation(request,ilm);
			if (id == 0){
				dispatcher = ctx.getRequestDispatcher("/addlocation.jsp");
			}else{
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/admin.jsp");
			}
		}else if (action.equalsIgnoreCase("editLocation")){
			Location loc = (Location) ctx.getAttribute("location");
			if (loc == null){
				String locationID = request.getParameter("locationID");
				int locID = Integer.parseInt(locationID);
				loc = ilm.getLocationById(locID);
			}
			if (editLocation(request,ilm,loc)){
				//Set update message
				request.setAttribute("errorMessage", "");	
				dispatcher = ctx.getRequestDispatcher("/managelocations.jsp");			
			}else{
				//Set error message
			}
			dispatcher = ctx.getRequestDispatcher("/editlocation.jsp");
		}else{
			
		}
		dispatcher.forward(request,response);
	}

	private boolean editLocation(HttpServletRequest request, ILocationManager ilm, Location location){
		String name = request.getParameter("locationName");
		String address = request.getParameter("locationAddress");
		String size = request.getParameter("capacity");
		String errorMessage = "";
		if (name == null || name.isEmpty()){
			errorMessage = "Missing location name";
		}else if (address == null || address.isEmpty()){
			errorMessage = "Missing location address";
		}else if (size == null || size.isEmpty()){
			errorMessage = "Missing location capacity";
		}else{
			try{
				int capacity = Integer.parseInt(size);
				ilm.updateLocation(location.getId(), name, address, capacity);				
				return true;
			}catch(NumberFormatException e){
				errorMessage = "Error parsing location capacity.";
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
	}
	
	private int addLocation(HttpServletRequest request, ILocationManager ilm){
		String errorMessage = "";
		int locationID = 0;
		try{
			String name = request.getParameter("locationName");
			String address = request.getParameter("locationAddress");
			String c = request.getParameter("capacity");
			if (name == null || name.isEmpty()){
				errorMessage = "Missing location name";
			}else if (address == null || address.isEmpty()){
				errorMessage = "Missing location address";
			}else if (c == null || c.isEmpty()){
				errorMessage = "Missing location capacity";
			}else{
				int cap = Integer.parseInt(c);
				locationID = ilm.addLocation(name, address, cap);
			}
			System.out.println(locationID+"-"+name+"-"+address+"-"+c);
		}catch(NumberFormatException e){
			errorMessage = "Error parsing one of the location capacity. Please enter a whole number.";
		}catch(Exception e){
			errorMessage = e.getMessage();
			System.err.println(e.getMessage());
		}
		request.setAttribute("errorMessage", errorMessage);
		return locationID;	
	}

}
