package com.youdrive.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.ReservationDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IReservationManager;
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
		HttpSession session = request.getSession();
		ILocationManager ilm = (LocationDAO) session.getAttribute("locationMgr");
		if (ilm == null){
			ilm = new LocationDAO();
			session.setAttribute("userMgr", ilm);
		}
		String locationID = request.getParameter("locationID");
		String searchType = request.getParameter("searchType");
		String dispatchedPage = "/login.jsp";
		if (locationID != null && !locationID.isEmpty()){
			int locID = Integer.parseInt(locationID);
			Location loc = ilm.getLocationById(locID);
			if (loc != null){
				session.setAttribute("location", loc);
				request.setAttribute("errorMessage","");
				dispatchedPage =  "/editlocation.jsp";
			}else{
				request.setAttribute("errorMessage", "Unable to find Location object.");
				dispatchedPage = "/managelocations.jsp";
			}
		}else if (searchType != null && !searchType.isEmpty()){
			int sType = 0;
			try{
				sType = Integer.parseInt(searchType);
				request.setAttribute("errorMessage","");
			}catch(NumberFormatException e){
				request.setAttribute("errorMessage","Passed a non-numeric value.");
			}finally{
				request.setAttribute("searchType", sType);
			}
			dispatchedPage = "/managelocations.jsp";

		}
		dispatcher = ctx.getRequestDispatcher(dispatchedPage);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		ILocationManager ilm = (LocationDAO) session.getAttribute("locationMgr");
		if (ilm == null){
			ilm = new LocationDAO();
			session.setAttribute("locationMgr", ilm);
		}
		String dispatchedPage = "/admin.jsp";
		String action = request.getParameter("action");
		if (action != null && !action.isEmpty()){
			if (action.equalsIgnoreCase("addLocation")){
				System.out.println("addLocation action.");
				int id = addLocation(request,ilm);
				if (id == 0){
					dispatchedPage = "/addlocation.jsp";
				}else{
					request.setAttribute("errorMessage","");
					dispatchedPage = "/managelocations.jsp";
				}
			}else if (action.equalsIgnoreCase("editLocation")){
				System.out.println("Edit Location action.");
				Location loc = (Location) ctx.getAttribute("location");
				if (loc == null){
					System.out.println("Location object not found in request object so instantiating one.");
					String locationID = request.getParameter("locationID");
					int locID = Integer.parseInt(locationID);
					loc = ilm.getLocationById(locID);
				}
				if (editLocation(request,ilm,loc)){
					//Set update message
					System.out.println("Location updated.");
					request.setAttribute("errorMessage", "");	
					dispatchedPage = "/managelocations.jsp";			
				}else{
					request.setAttribute("location", loc);
					dispatchedPage = "/editlocation.jsp";
				}
			}else if (action.equalsIgnoreCase("deleteLocation")){
				IReservationManager irm = (ReservationDAO)session.getAttribute("reserveMgr");
				if (irm == null){
					irm = new ReservationDAO();
					session.setAttribute("reserveMgr", irm);
				}
				dispatchedPage = "/managelocations.jsp";
				boolean result = deleteLocation(request,ilm,irm);
				if (result){
					System.out.println("Removed the location.");
				}
			}
		}else{
			request.setAttribute("errorMessage", "Unknown POST request");
			dispatchedPage =  "/login.jsp";
		}
		dispatcher = ctx.getRequestDispatcher(dispatchedPage);
		dispatcher.forward(request,response);
	}
	
	/**
	 * Delete a Location
	 * @param request
	 * @param ilm
	 * @param irm
	 * @param location
	 * @return
	 */
	private boolean deleteLocation(HttpServletRequest request, ILocationManager ilm, IReservationManager irm){
		String locID = request.getParameter("locationID");
		String errorMessage = "";
		if (locID == null || locID.isEmpty()){
			errorMessage = "Invalid location id found.";					
		}else{
			try{
				int locationID = Integer.parseInt(locID);
				//Check if this location is a valid object
				Location l = ilm.getLocationById(locationID);
				if (l != null){
					//Check if this location is in use for active reservations
					int count = irm.checkIfLocationInUse(locationID);
					if (count > 0){
						errorMessage = "Location in use for " + count +  " reservations.";
					}else{
						//Check if vehicles are assigned to this location
						count = ilm.getCountOfLocations(locationID);
						if (count == -1){
							errorMessage = "Unspecified exception getting the count of all vehicles assigned to this location.";
						}else if (count > 0){
							errorMessage = count + " vehicles assigned to this location. Re-assign them first.";
						}else{
							//Delete the location now
							boolean result = ilm.deleteLocationById(locationID);
							if (!result){
								errorMessage = "Unable to delete this Location. Please contact the admin.";
							}else{
								//Successfully deleted a location!
							}
						}
					}
				}else{
					errorMessage = "Location not found in system.";
				}
			}catch(NumberFormatException e){
				errorMessage = "Invalid location ID parameter found.";
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
		
	}

	/**
	 * Editing a Location
	 * @param request
	 * @param ilm
	 * @param location
	 * @return
	 */
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
				//TODO make sure # of assigned vehicles is == or less than this capacity
				//TODO make sure capacity is not a negative number
				int locationID = location.getId();
				int capacity = Integer.parseInt(size);
				if (capacity > 0){
					int currentCapacity = ilm.getCurrentCapacity(locationID);
					if (currentCapacity <= capacity){
						boolean locationInUse = ilm.isLocationNameInUse(name);
						//If new location name is not in use or if location name is unchanged
						if (!locationInUse || name.equalsIgnoreCase(location.getName())){
							ilm.updateLocation(locationID, name, address, capacity);
							return true;
						}else{
							errorMessage = "Location name already in use.";
						}
					}else{
						errorMessage = "Increase the capacity of this location. " + currentCapacity + " vehicles are at this location.";
					}
				}else{
					errorMessage = "Location must hold at least 1 vehicle.";
				}
			}catch(NumberFormatException e){
				errorMessage = "Error parsing location capacity.";
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
	}

	/**
	 * Adding a Location
	 * @param request
	 * @param ilm
	 * @return
	 */
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
				if (cap >= 1){
					locationID = ilm.addLocation(name, address, cap);
				}else{
					errorMessage = "Location must be able to hold at least 1 vehicle.";
				}
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
