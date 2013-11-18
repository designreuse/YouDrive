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


import com.youdrive.helpers.VehicleDAO;
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
		if (ivm == null){
			ivm = new VehicleDAO();
			ctx.setAttribute("vehicleMgr", ivm);
		}
		String action = request.getParameter("action");
		//Adding a single vehicle
		if (action.equalsIgnoreCase("addVehicle")){
			int id = addVehicle(request,ivm);
			if (id == 0){
				System.err.println("Problem saving vehicle to db.");
				dispatcher = ctx.getRequestDispatcher("/addvehicle.jsp");
			}else{
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/admin.jsp");
			}
		}else{
			dispatcher = ctx.getRequestDispatcher("/login.jsp");
		}
		dispatcher.forward(request,response);
	}

	private int addVehicle(HttpServletRequest request, IVehicleManager ivm){
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
				vehicleID = ivm.addVehicle(make, model, year, tag, mileage, lastServiced, vehicleType, assignedLocation);
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
}
