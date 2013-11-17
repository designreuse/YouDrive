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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		ILocationManager ilm = (LocationDAO) ctx.getAttribute("ilm");
		if (ilm == null){
			ilm = new LocationDAO();
		}
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("addLocation")){
			System.out.println("Got here.");
			int id = addLocation(request,ilm);
			if (id == 0){
				System.out.println("Problem saving location to db.");
			}else{
				System.out.println("location saved with id of " + id);
			}
		}else{
			
		}
	}

	private int addLocation(HttpServletRequest request, ILocationManager ilm){
		String errorMessage = "";
		try{
			String name = request.getParameter("locationName");
			String address = request.getParameter("locationAddress");
			String c = request.getParameter("capacity");
			int cap = Integer.parseInt(c);
			int vehicleTypeID = ilm.addLocation(name, address, cap);
			System.out.println(vehicleTypeID+"-"+name+"-"+address+"-"+cap);
			return vehicleTypeID;
		}catch(NumberFormatException e){
			errorMessage = "Error parsing one of the numeric values.";
		}catch(Exception e){
			errorMessage = e.getMessage();
			System.err.println(e.getMessage());
		}
		request.setAttribute("errorMessage", errorMessage);
		return 0;	
	}

}
