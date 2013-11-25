package com.youdrive.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.JSONException;
import org.json.JSONObject;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.ReservationDAO;
import com.youdrive.helpers.VehicleDAO;
import com.youdrive.helpers.VehicleTypeDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IReservationManager;
import com.youdrive.interfaces.IVehicleManager;
import com.youdrive.interfaces.IVehicleTypeManager;
import com.youdrive.models.Reservation;
import com.youdrive.models.ReservationStatus;
import com.youdrive.models.User;
import com.youdrive.models.Vehicle;
import com.youdrive.models.VehicleType;

/**
 * Servlet implementation class ReservationManagement
 */
@WebServlet("/ReservationManagement")
public class ReservationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReservationManagement() {
		super(); 
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		IReservationManager irm = (ReservationDAO) session.getAttribute("reservationMgr");
		IVehicleManager ivm = (VehicleDAO) session.getAttribute("vehicleMgr");
		IVehicleTypeManager ivtm = (VehicleTypeDAO) session.getAttribute("vehicleTypeMgr");
		if (irm == null){
			irm = new ReservationDAO();
			session.setAttribute("reservationMgr", irm);
		}		
		if (ivm == null){
			ivm = new VehicleDAO();
			session.setAttribute("vehicleMgr", ivm);
		}	
		if (ivtm == null){
			ivtm = new VehicleTypeDAO();
			session.setAttribute("vehicleTypeMgr", ivtm);
		}
		String dispatchedPage = "/user.jsp";
		String action = request.getParameter("action");
		if (action != null && !action.isEmpty()){
			//Condition is true if user is coming from reservevehicle.jsp page
			if (action.equalsIgnoreCase("checkAvailableVehicles")){
				User user = (User)session.getAttribute("loggedInUser");
				String location = request.getParameter("selectLocation");
				String vehicleType = request.getParameter("selectVehicleType");
				String pickupDate = request.getParameter("pickupDate");
				String pickupTime = request.getParameter("pickupTime");
				String dropoffDate = request.getParameter("dropoffDate");
				String dropoffTime = request.getParameter("dropoffTime");
				dispatchedPage = "/reservevehicle.jsp";
				if (user == null){
					request.setAttribute("errorMessage", "Please log in to perform this action.");
				}else if (location == null || location.isEmpty()){
					request.setAttribute("errorMessage","Missing a location");
				}else if (vehicleType == null || vehicleType.isEmpty()){
					request.setAttribute("errorMessage", "Missing vehicle type selection.");
				}else if (pickupDate == null || pickupDate.isEmpty()){
					request.setAttribute("errorMessage","Missing a pickup date");
				}else if (pickupTime == null || pickupTime.isEmpty()){
					request.setAttribute("errorMessage","Missing a pickup time.");
				}else if (dropoffDate == null || dropoffDate.isEmpty()){
					request.setAttribute("errorMessage","Missing a dropoff date");
				}else if (dropoffTime == null || dropoffTime.isEmpty()){
					request.setAttribute("errorMessage","Missing a dropoff time.");
				}else{
					try{
						int vTypeID = Integer.parseInt(vehicleType);
						VehicleType vt = ivtm.getVehicleType(vTypeID);
						if (vt != null){
							//validate Date
							if (isThisDateValid(pickupDate) && isThisDateValid(dropoffDate)){
								//validate times
								if (isTimeValid(pickupTime) && isTimeValid(dropoffTime)){
									//Create date objects
									Calendar startDate = getDate(pickupDate,pickupTime);
									Calendar stopDate = getDate(dropoffDate,dropoffTime);

									//Reservations can't be more than 72hrs in duration
									Calendar maxBookingDate = Calendar.getInstance();
									maxBookingDate.setTime(startDate.getTime());
									maxBookingDate.add(Calendar.HOUR, 72);

									if (startDate != null && stopDate != null){
										if (startDate.compareTo(stopDate) > 0){
											request.setAttribute("errorMessage", "Stop date must be later than the start Date.");
										}else if (stopDate.compareTo(maxBookingDate) > 0){
											System.out.println("Max booking date: " + maxBookingDate.getTime());
											request.setAttribute("errorMessage", "Exceeds max reservation of 72 hours.");
										}else{
											java.util.Date sDate = startDate.getTime();
											java.util.Date eDate = stopDate.getTime();
											System.out.println("User dates: " + sDate + " end: " + eDate);
											int locationID = Integer.parseInt(location);
											int vehicleTypeID = Integer.parseInt(vehicleType);
											//Get all vehicles of that type at that location
											ArrayList<Vehicle> allVehicles = ivm.getAllVehiclesByLocationAndType(locationID, vehicleTypeID);
											int size = allVehicles.size();
											if (size == 0){
												request.setAttribute("errorMessage", "0 vehicles at that location and type combination");
											}else{
												ArrayList<Vehicle> results = new ArrayList<Vehicle>();
												//Going through list and
												for (Vehicle v : allVehicles){
													//Get reservations in Reservations that fit this location id and vehicle id
													ArrayList<Reservation> inReservationsTable = irm.getAllReservations(v.getAssignedLocation(),v.getId());
													//Add this vehicle if it is not in the Reservations table
													if (inReservationsTable.isEmpty()){
														results.add(v);
													}else{
														//Loop through the found reservations to check the dates
														boolean foundOverlap = false;
														for (Reservation r : inReservationsTable){												
															java.util.Date rStartDate = r.getReservationStart();
															java.util.Date rEndDate = r.getReservationEnd();
															System.out.println("Reservation dates: " + rStartDate + " End: " + rEndDate);												
															//x.compareTo(y) < 0 if x is before y
															//x.compareTo(y) > 0 if x is after y
															if ((rStartDate.compareTo(sDate) < 0 && rStartDate.compareTo(eDate) < 0 && rEndDate.compareTo(sDate) > 0 && rEndDate.compareTo(eDate) < 0)
																	|| (rStartDate.compareTo(sDate) > 0 && rStartDate.compareTo(eDate) < 0 && rEndDate.compareTo(sDate) > 0 && rEndDate.compareTo(eDate) > 0) 
																	|| (rStartDate.compareTo(sDate) > 0 && rStartDate.compareTo(eDate) < 0 && rEndDate.compareTo(eDate) < 0 && rEndDate.compareTo(sDate) > 0) 
																	|| (rStartDate.compareTo(sDate) < 0 && rStartDate.compareTo(eDate) < 0 && rEndDate.compareTo(eDate) > 0 && rEndDate.compareTo(sDate) > 0) ){
																
																//Check reservationStatus if overlap is found.
																String reservationStatus = irm.getStatus(r.getId());
																if (!(reservationStatus.equalsIgnoreCase("Cancelled")) && !(reservationStatus.equalsIgnoreCase("Returned"))){
																	System.err.println("Found single sample of vehicle and location combination that overlaps. Breaking from inner for loop.");
																	foundOverlap = true;
																	break;
																}
															}
														}
														//if overlap wasn't found, add the vehicle to the list.
														if (!foundOverlap){
															results.add(v);
														}
													}
												}

												//Send to results page
												if (results.size() > 0){
													session.setAttribute("locationID", locationID);
													session.setAttribute("vehicleTypeID", vehicleTypeID);										
													try{
														JSONObject j = new JSONObject();
														j.put("locationID", locationID);
														j.put("vehicleTypeID", vehicleTypeID);
														j.put("pickupDate", sDate);
														j.put("dropoffDate", eDate);
														session.setAttribute("resultParams", j);
													}catch(JSONException e){
														System.err.println("Error converting to JSONObject.");
													}
													session.setAttribute("startDate", sDate);
													session.setAttribute("endDate", eDate);
													session.setAttribute("hourlyPrice", vt.getHourlyPrice());
													session.setAttribute("dailyPrice", vt.getDailyPrice());
													session.setAttribute("searchResults",results);
													dispatchedPage = "/reservationcheck.jsp";
												}else{
													request.setAttribute("errorMessage", "Change parameters. Found vehicles reserved that overlap your start/end dates/times");
												}
											}
										}
									}else{
										request.setAttribute("errorMessage","Error parsing the dates.");
									}
								}else{
									request.setAttribute("errorMessage", "Invalid pickup or dropoff times. Please use the values in the dropdown box.");
								}
							}else{
								request.setAttribute("errorMessage", "Invalid pickup date or dropoff date. Please enter your dates in this form: MM/DD/YYYY.");
							}
						}else{
							request.setAttribute("errorMessage", "Invalid vehicle type.");
						}
					}catch(NumberFormatException e){
						request.setAttribute("errorMessage","Invalid vehicle type id found.");
					}
				}
			}else if (action.equalsIgnoreCase("makeReservation")){
				//Condition is true if user clicks "reserve" on the reservationcheck.jsp page
				//details on what the user selected are in the session parameters 
				System.out.println("makeReservationForm action");
				User user = (User)session.getAttribute("loggedInUser");
				String vehicleID = request.getParameter("vehicleID");
				int locationID = (int)session.getAttribute("locationID");
				java.util.Date startDate = (java.util.Date) session.getAttribute("startDate");
				java.util.Date stopDate = (java.util.Date) session.getAttribute("endDate");
				dispatchedPage = "/reservecheck.jsp";

				if(user == null){ 
					request.setAttribute("errorMessage","Please log in before making this request.");
				}else if (vehicleID == null || vehicleID.isEmpty()){
					request.setAttribute("errorMessage","No selection made.");
				}else if (locationID == 0){
					request.setAttribute("errorMessage","No location.");
				}else if (startDate == null){
					request.setAttribute("errorMessage","Invalid startDate.");	
				}else if (stopDate == null){
					request.setAttribute("errorMessage","Invalid stopDate");
				}else{
					//TODO insert entry into Reservations and ReservationStatus					
					try{
						int vID = Integer.parseInt(vehicleID);
						Vehicle v = ivm.getVehicle(vID);
						if (v != null){
							//Make reservation
							Calendar reservationDate = Calendar.getInstance(Locale.US);							
							int reservationID = irm.makeReservation(user.getId(),locationID,vID,startDate,stopDate);
							if (reservationID > 0){
								int reservationStatusID = irm.addReservationStatus(reservationID,reservationDate.getTime(),"Created");
								if (reservationStatusID > 0){
									//Update reservationStatus
									Reservation r = new Reservation(reservationID,user.getId(), locationID, vID, startDate, stopDate);
									ReservationStatus rs = new ReservationStatus(reservationStatusID, reservationID, reservationDate.getTime(), "Created");
									request.setAttribute("reservedVehicle", v);
									request.setAttribute("reservation", r);
									request.setAttribute("reservationStatus", rs);
									request.setAttribute("errorMessage", "Reservation Made!!");
									dispatchedPage = "/confirmation.jsp";
								}else{
									request.setAttribute("errorMessage", "Unable to save reservationStatus.");
									System.err.println("Uh oh, was unable to save the reservation status but an entry was made to Reservations table.");
								}
							}else{
								request.setAttribute("errorMessage", "Unable to create Reservation.");
							}
						}else{
							request.setAttribute("errorMessage", "Vehicle not found!!");
						}
					}catch(NumberFormatException e){
						request.setAttribute("errorMessage", e.getMessage());
					}
				}
			}else{
				//
				dispatchedPage = "/user.jsp";
			}			
		}else{
			request.setAttribute("errorMessage", "Unknown POST request.");
			dispatchedPage = "/user.jsp";
		}
		dispatcher = ctx.getRequestDispatcher(dispatchedPage);
		dispatcher.forward(request,response);
	}


	//http://www.mkyong.com/java/how-to-check-if-date-is-valid-in-java/
	/**
	 * Return true if the Date is an actual date
	 * @param dateToValidate
	 * @return boolean
	 * @throws java.text.ParseException
	 */
	public boolean isThisDateValid(String dateToValidate){
		if(dateToValidate == null){
			return false;
		}		
		try { 
			//if not valid, it will throw ParseException
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
			sdf.setLenient(false);
			java.util.Date d = sdf.parse(dateToValidate);
			return true;
		}catch(Exception e){ 
			e.printStackTrace();
		} 
		return false;
	}

	public Calendar getDate(String date, String time){
		if(date == null || time == null){
			return null;
		}		
		try { 
			//if not valid, it will throw ParseException
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm",Locale.US);
			sdf.setLenient(false);
			java.util.Date d = sdf.parse(date.trim()+" "+time.trim());
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return cal;
		}catch(ParseException e){ 
			e.printStackTrace();
		} 
		return null;
	}

	//http://www.mkyong.com/regular-expressions/how-to-validate-time-in-24-hours-format-with-regular-expression/
	/**
	 * Returns true if the time is a valid 24-hr format time.
	 * False otherwise
	 * @param timeToValidate
	 * @return boolean
	 */
	public boolean isTimeValid(String timeToValidate){
		String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
		Matcher matcher = pattern.matcher(timeToValidate);
		return matcher.matches();
	}

}
