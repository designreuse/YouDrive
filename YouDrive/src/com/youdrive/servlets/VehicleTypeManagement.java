package com.youdrive.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youdrive.helpers.VehicleDAO;
import com.youdrive.helpers.VehicleTypeDAO;
import com.youdrive.interfaces.IVehicleManager;
import com.youdrive.interfaces.IVehicleTypeManager;
import com.youdrive.models.VehicleType;

/**
 * Servlet implementation class VehicleTypeManagement
 */
@WebServlet("/VehicleTypeManagement")
public class VehicleTypeManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VehicleTypeManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		IVehicleTypeManager ivtm = (VehicleTypeDAO) ctx.getAttribute("vehicleTypeMgr");
		if (ivtm == null){
			ivtm = new VehicleTypeDAO();
			ctx.setAttribute("vehicleTypeMgr", ivtm);
		}		
		String vehicleTypeID = request.getParameter("vehicleTypeID");
		if (vehicleTypeID != null && !vehicleTypeID.isEmpty()){
			int typeID = Integer.parseInt(vehicleTypeID);
			VehicleType vType = ivtm.getVehicleType(typeID);
			if (vType != null){
				ctx.setAttribute("vehicleType", vType);
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/editvehicletype.jsp");
			}else{
				request.setAttribute("errorMessage", "Unable to find Vehicle Type object.");
				dispatcher = ctx.getRequestDispatcher("/managevehicles.jsp");
			}
		}else{
			dispatcher = ctx.getRequestDispatcher("/index.jsp");
		}
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		IVehicleTypeManager ivtm = (VehicleTypeDAO) ctx.getAttribute("vehicleTypeMgr");
		if (ivtm == null){
			ivtm = new VehicleTypeDAO();
			ctx.setAttribute("vehicleTypeMgr", ivtm);
		}	
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("addVehicleType")){
			//adding a vehicle type
			int id = addVehicleType(request,ivtm);
			if (id == 0){
				System.err.println("Problem saving vehicle type to db.");
				dispatcher = ctx.getRequestDispatcher("/addvehicletype.jsp");
			}else{
				request.setAttribute("errorMessage","");
				dispatcher = ctx.getRequestDispatcher("/managevehicletypes.jsp");
			}
		}else if (action.equalsIgnoreCase("editVehicleType")){
			VehicleType vType = (VehicleType)ctx.getAttribute("vehicleType");
			if (vType == null){
				String vehicleType = request.getParameter("vehicleTypeID");
				int vehicleTypeID = Integer.parseInt(vehicleType);
				vType = ivtm.getVehicleType(vehicleTypeID);
			}
			if (editVehicleType(request,ivtm,vType)){
				request.setAttribute("errorMessage", "");
				dispatcher = ctx.getRequestDispatcher("/managevehicletypes.jsp");
			}else{
				dispatcher = ctx.getRequestDispatcher("/editvehicletype.jsp");
			}
		}
		dispatcher.forward(request,response);
	}
	
	
	private boolean editVehicleType(HttpServletRequest request, IVehicleTypeManager ivtm, VehicleType vType){
		String errorMessage = "";
		int vehicleTypeID = vType.getId();

		try{
			String type = request.getParameter("vehicleTypeName");
			String hPrice = request.getParameter("hourlyPrice");
			String dPrice = request.getParameter("dailyPrice");
			if (type == null || type.isEmpty()){
				errorMessage = "Missing vehicle type";
			}else if (hPrice == null || hPrice.isEmpty()){
				errorMessage = "Missing hourly price";
			}else if (dPrice == null || dPrice.isEmpty()){
				errorMessage = "Missing daily price";
			}else{
				Double hourlyPrice = Double.parseDouble(hPrice);
				Double dailyPrice = Double.parseDouble(dPrice);
				if (hourlyPrice < 0 ){
					errorMessage = "Hourly Pricing must be greater than or equal to zero.";
				}else if (dailyPrice < 0){
					errorMessage = "Daily Pricing must be greater than or equal to zero.";
				}else{
					ivtm.updateVehicleType(vehicleTypeID,type, hourlyPrice, dailyPrice);
					System.out.println(vehicleTypeID+"-"+type+"-"+hourlyPrice+"-"+dailyPrice);
					return true;
				}
			}
		}catch(NumberFormatException e){
			errorMessage = "Error parsing the hourly or daily price entered.";
		}catch(Exception e){
			errorMessage = e.getMessage();
			System.err.println(e.getMessage());
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;	
	}
	
	private int addVehicleType(HttpServletRequest request, IVehicleTypeManager ivtm){
		String errorMessage = "";
		int vehicleTypeID = 0;
		try{
			String type = request.getParameter("vehicleTypeName");
			String hPrice = request.getParameter("hourlyPrice");
			String dPrice = request.getParameter("dailyPrice");
			if (type == null || type.isEmpty()){
				errorMessage = "Missing vehicle type";
			}else if (hPrice == null || hPrice.isEmpty()){
				errorMessage = "Missing hourly price";
			}else if (dPrice == null || dPrice.isEmpty()){
				errorMessage = "Missing daily price";
			}else{
				Double hourlyPrice = Double.parseDouble(hPrice);
				Double dailyPrice = Double.parseDouble(dPrice);
				if (hourlyPrice < 0 ){
					errorMessage = "Hourly Pricing must be greater than or equal to zero.";
				}else if (dailyPrice < 9){
					errorMessage = "Daily Pricing must be greater than or equal to zero.";
				}else{
					vehicleTypeID = ivtm.addVehicleType(type, hourlyPrice, dailyPrice);
					System.out.println(vehicleTypeID+"-"+type+"-"+hourlyPrice+"-"+dailyPrice);
				}
			}
		}catch(NumberFormatException e){
			errorMessage = "Error parsing the hourly or daily price entered.";
		}catch(Exception e){
			errorMessage = e.getMessage();
			System.err.println(e.getMessage());
		}
		request.setAttribute("errorMessage", errorMessage);
		return vehicleTypeID;	
	}
}