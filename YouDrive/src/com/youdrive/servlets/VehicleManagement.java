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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		IVehicleManager ivm = (VehicleDAO) ctx.getAttribute("ivm");
		if (ivm == null){
			ivm = new VehicleDAO();
		}
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("addVehicle")){
			int id = addVehicle(request,ivm);
			if (id == 0){
				System.out.println("Problem saving vehicle to db.");
			}else{
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/admin.jsp");
			}
		}else if(action.equalsIgnoreCase("addVehicleType")){
			System.out.println("Got here.");
			int id = addVehicleType(request,ivm);
			if (id == 0){
				System.out.println("Problem saving vehicle type to db.");
			}else{
				System.out.println("Vehicle saved with id of " + id);
			}
		}else{
			
		}
		dispatcher.forward(request,response);
	}

	private int addVehicleType(HttpServletRequest request, IVehicleManager ivm){
		String errorMessage = "";
		try{
			String type = request.getParameter("vehicleTypeName");
			String hPrice = request.getParameter("hourlyPrice");
			String dPrice = request.getParameter("dailyPrice");
			Double hourlyPrice = Double.parseDouble(hPrice);
			Double dailyPrice = Double.parseDouble(dPrice);
			int vehicleTypeID = ivm.addVehicleType(type, hourlyPrice, dailyPrice);
			System.out.println(vehicleTypeID+"-"+type+"-"+hourlyPrice+"-"+dailyPrice);
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
	
	private int addVehicle(HttpServletRequest request, IVehicleManager ivm){
		String errorMessage = "";
		try{
			String make = request.getParameter("make");
			String model = request.getParameter("model");
			int year = Integer.parseInt(request.getParameter("year"));
			String tag = request.getParameter("tag");
			int mileage = Integer.parseInt(request.getParameter("mileage"));
			String lastServiced = request.getParameter("lastServiced");
			int vehicleType = Integer.parseInt(request.getParameter("vehicleType"));
			int assignedLocation = Integer.parseInt(request.getParameter("vehicleLocation"));
			System.out.println(make+"-"+model+"-"+year+"-"+tag+"-"+mileage+"-"+lastServiced+"-"+vehicleType+"-"+assignedLocation);
			int vehicleID = ivm.addVehicle(make, model, year, tag, mileage, lastServiced, vehicleType, assignedLocation);
			return vehicleID;
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
