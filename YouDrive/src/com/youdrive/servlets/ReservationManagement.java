package com.youdrive.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.ReservationDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IReservationManager;

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
		if (irm == null){
			irm = new ReservationDAO();
			session.setAttribute("reservationMgr", irm);
		}
		String dispatchedPage = "/user.jsp";
		String action = request.getParameter("action");
		if (action != null && !action.isEmpty()){
			if (action.equalsIgnoreCase("checkAvailableVehicles")){
				String location = request.getParameter("selectLocation");
				String pickupDate = request.getParameter("pickupDate");
				String pickupTime = request.getParameter("pickupTime");
				String dropoffDate = request.getParameter("dropoffDate");
				String dropoffTime = request.getParameter("dropoffTime");
				if (location == null || location.isEmpty()){
					request.setAttribute("errorMessage","Missing a location");
				}else if (pickupDate == null || pickupDate.isEmpty()){
					request.setAttribute("errorMessage","Missing a pickup date");
				}else if (pickupTime == null || pickupTime.isEmpty()){
					request.setAttribute("errorMessage","Missing a pickup time.");
				}else if (dropoffDate == null || dropoffDate.isEmpty()){
					request.setAttribute("errorMessage","Missing a dropoff date");
				}else if (dropoffTime == null || dropoffTime.isEmpty()){
					request.setAttribute("errorMessage","Missing a dropoff time.");
				}else{
					//validate Date
					if (isThisDateValid(pickupDate) && isThisDateValid(dropoffDate)){
						//validate times
						if (isTimeValid(pickupTime) && isTimeValid(dropoffTime)){
							//validate proper location
						}else{
							request.setAttribute("errorMessage", "Invalid pickup or dropoff times. Please use the values in the dropdown box.");
						}
					}else{
						request.setAttribute("errorMessage", "Invalid pickup date or dropoff date. Please enter your dates in this form: MM/DD/YYYY.");
					}
				}
			}else{
				
			}
			
		}else{
			
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
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		sdf.setLenient(false); 
		try { 
			//if not valid, it will throw ParseException
			java.util.Date date = sdf.parse(dateToValidate);
			System.out.println("Validated date: " + date);
		}catch(ParseException e){ 
			e.printStackTrace();
			return false;
		} 
		return true;
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
