package com.youdrive.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
import com.youdrive.helpers.VehicleDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IReservationManager;
import com.youdrive.interfaces.IVehicleManager;
import com.youdrive.models.Location;
import com.youdrive.models.User;
import com.youdrive.models.Vehicle;
import com.youdrive.models.Comment;

/**
 * Servlet implementation class VehicleManagement
 */
@WebServlet("/VehicleManagement")
public class VehicleManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat sdf;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VehicleManagement() {
		super();
		sdf = new SimpleDateFormat("MM/dd/YYYY",Locale.getDefault());
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		IVehicleManager ivm = (VehicleDAO) session.getAttribute("vehicleMgr");
		if (ivm == null){
			ivm = new VehicleDAO();
			session.setAttribute("vehicleMgr", ivm);
		}	
		String vehicleID = request.getParameter("vehicleID");
		String searchType = request.getParameter("searchType");
		String viewComments = request.getParameter("viewComments");
		String dispatchedPage = "/login.jsp";
		if (vehicleID != null && !vehicleID.isEmpty()){
			System.out.println("getting vehicle details.");
			int vID = Integer.parseInt(vehicleID);
			Vehicle vehicle = ivm.getVehicle(vID);
			if (vehicle != null){
				session.setAttribute("vehicle", vehicle);
				request.setAttribute("errorMessage","");
				dispatchedPage = "/editvehicle.jsp";
			}else{
				request.setAttribute("errorMessage", "Unable to find Vehicle object.");
				dispatchedPage = "/managevehicles.jsp";
			}
		}else if (searchType != null && !searchType.isEmpty()){
			System.out.println("Performing sorting action.");
			int sType = 0;
			try{
				sType = Integer.parseInt(searchType);
			}catch(NumberFormatException e){
				System.err.println("Passed a non-numeric value.");
				request.setAttribute("errorMessage","Passed a non-numeric value.");
			}finally{
				request.setAttribute("searchType", sType);
			}
			User user = (User) session.getAttribute("loggedInUser");
			dispatchedPage = (user.isAdmin())?"/managevehicles.jsp":"/reservationcheck.jsp";
		}else if (viewComments != null && !viewComments.isEmpty()){
			System.out.println("Retrieving comments.");
			try{
				int vID = Integer.parseInt(viewComments);
				Vehicle v = ivm.getVehicle(vID);
				ArrayList<Comment> allComments = ivm.getVehicleComments(vID);
				v.setVehicleComments(allComments);
				session.setAttribute("vehicleComments", allComments);
				session.setAttribute("vehicle", v);
				dispatchedPage = "/viewcomments.jsp";
			}catch(NumberFormatException e){
				request.setAttribute("errorMessage","Invalid parameter request.");
				dispatchedPage = "/managevehicles.jsp";
			}
		}else{
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			if (loggedInUser != null){
				if (loggedInUser.isAdmin()){
					dispatchedPage = "/managevehicles.jsp";
				}else{
					dispatchedPage = "/user.jsp";
				}
			}
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
		IVehicleManager ivm = (VehicleDAO) session.getAttribute("vehicleMgr");
		ILocationManager ilm = (LocationDAO) session.getAttribute("locationMgr");
		IReservationManager irm = (ReservationDAO) session.getAttribute("reservationMgr");
		if (ivm == null){
			ivm = new VehicleDAO();
			session.setAttribute("vehicleMgr", ivm);
		}
		if (ilm == null){
			ilm = new LocationDAO();
			session.setAttribute("locationMgr", ilm);
		}
		if (irm == null){
			irm = new ReservationDAO();
			session.setAttribute("reservationMgr", irm);
		}
		String action = request.getParameter("action");
		//Adding a single vehicle
		if (action != null && !action.isEmpty()){
			if (action.equalsIgnoreCase("addVehicle")){
				int id = addVehicle(request,ivm,ilm);
				if (id == 0){
					System.err.println("Problem saving vehicle to db.");
					dispatcher = ctx.getRequestDispatcher("/addvehicle.jsp");
				}else{
					//Add comment to database
					String comment = request.getParameter("comment");
					if (comment != null && !comment.isEmpty()){
						//Get logged in user
						User user = (User) session.getAttribute("loggedInUser");
						if (user != null){
							int commentId = ivm.addVehicleComment(id, comment, user.getId());
							if (commentId == 0){
								System.err.println("Unable to save comment.");
								request.setAttribute("errorMessage","Unable to save comment but vehicle was created.");
							}else{
								//Successfully added comment.
								System.out.println("Added vehicle and created comment.");
							}
						}else{
							request.setAttribute("errorMessage","No user logged in.");
							System.err.println("User somehow got logged out during comment saving after vehicle was created");
						}
					}else{
						System.err.println("User object in session is null.");
					}
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
							//Check for comment and add.
							String comment = request.getParameter("comment");
							if (comment != null && !comment.isEmpty()){
								//Get logged in user
								User user = (User) session.getAttribute("loggedInUser");
								if (user != null){
									int commentId = ivm.addVehicleComment(v.getId(), comment, user.getId());
									if (commentId == 0){
										System.err.println("Unable to save comment.");
										request.setAttribute("errorMessage","Unable to save comment.");
									}else{
										//Successfully added comment.
									}
								}else{
									request.setAttribute("errorMessage","No user logged in.");
								}
							}else{
								System.err.println("User object in session is null.");
							}
							if (editVehicle(session,request,ivm,ilm,v)){
								request.setAttribute("errorMessage", "");
								session.setAttribute("allVehicles", ivm.getAllVehicles());
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
			}else if (action.equalsIgnoreCase("deleteVehicle")){
				String vehicleIDStr = request.getParameter("vehicleID");
				if (vehicleIDStr != null && !vehicleIDStr.isEmpty()){
					try{
						int vehicleID = Integer.parseInt(vehicleIDStr);
						Vehicle v = ivm.getVehicle(vehicleID);
						if (v != null){
							if (deleteVehicle(session,request,ivm,ilm,irm,v)){
								request.setAttribute("errorMessage", "");
							}
						}else{
							request.setAttribute("errorMessage", "Vehicle not found.");
						}
					}catch(NumberFormatException e){
						request.setAttribute("errorMessage", "Unable to parse vehicle id.");
					}
				}else{
					request.setAttribute("errorMessage", "Missing vehicle ID");
				}
				dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
			}else{
				dispatcher = ctx.getRequestDispatcher("/login.jsp");
			}
		}else{
			request.setAttribute("errorMessage", "Unknown POST request");
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

	/**
	 * Delete the vehicle
	 * @param session
	 * @param request
	 * @param ivm
	 * @param ilm
	 * @param irm
	 * @param vehicle
	 * @return
	 */
	private boolean deleteVehicle(HttpSession session, HttpServletRequest request, IVehicleManager ivm, ILocationManager ilm, IReservationManager irm, Vehicle vehicle){
		//Check if vehicle is involved in any active reservations
		String errorMsg = "";
		int vID = vehicle.getId();
		int count = irm.isVehicleInUse(vID);
		if (count == -1){
			errorMsg = "Error finding vehicle status.";
		}else if (count == 0){
			//Vehicle not in use in reservations
			//Delete vehicle comments;
			System.out.println("Vehicle not in use in any reservations so delete safely.");
			return ivm.deleteVehicle(vID);			
		}else{
			//Check if 
			//Delete vehicle only if there is NO open reservation
			count = irm.getCancelledOrReturnedReservationCount(vID);
			if (count == 0){
				errorMsg = "Open reservations found with this vehicle. Please return or cancel the reservation hold on this vehicle first.";
			}else if (count < 0){
				errorMsg = "Error executing the SQL statement.";
			}else{
				return ivm.deleteVehicle(vID);
			}
		}
		request.setAttribute("errorMessage", errorMsg);
		return false;
	}
	
	/**
	 * Edit a vehicle details.
	 * @param session
	 * @param request
	 * @param ivm
	 * @param ilm
	 * @param vehicle
	 * @return
	 */
	private boolean editVehicle(HttpSession session, HttpServletRequest request, IVehicleManager ivm, ILocationManager ilm,Vehicle vehicle){
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
							int dateLength = lastServiced.length();
							//By default, the date is shown on the form with hh:mm:ss
							//this code snippet truncated it to the first 11 characters which is parseable
							System.out.println("Length: " + dateLength + " form Date:" + lastServiced);
							if (dateLength != 11){
								System.out.println("form Date:" + lastServiced);
								lastServiced = lastServiced.substring(0,10);
								System.out.println("formatted Date:" + lastServiced);
							}
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
					int dateLength = lastServiced.length();
					//By default, the date is shown on the form with hh:mm:ss
					//this code snippet truncated it to the first 11 characters which is parseable
					System.out.println("Length: " + dateLength + " form Date:" + lastServiced);
					if (dateLength != 11){
						System.out.println("form Date:" + lastServiced);
						lastServiced = lastServiced.substring(0,10);
						System.out.println("formatted Date:" + lastServiced);
					}
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
