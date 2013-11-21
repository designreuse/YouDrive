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

import com.youdrive.helpers.VehicleTypeDAO;
import com.youdrive.interfaces.IVehicleTypeManager;
import com.youdrive.models.User;
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
		HttpSession session = request.getSession();
		IVehicleTypeManager ivtm = (VehicleTypeDAO) session.getAttribute("vehicleTypeMgr");
		if (ivtm == null){
			ivtm = new VehicleTypeDAO();
			session.setAttribute("vehicleTypeMgr", ivtm);
		}		
		String vehicleTypeID = request.getParameter("vehicleTypeID");
		String searchType = request.getParameter("searchType");
		String dispatchedPage = "/index.jsp";
		if (vehicleTypeID != null && !vehicleTypeID.isEmpty()){
			int typeID = Integer.parseInt(vehicleTypeID);
			VehicleType vType = ivtm.getVehicleType(typeID);
			if (vType != null){
				session.setAttribute("vehicleType", vType);
				request.setAttribute("errorMessage","");
				dispatchedPage = "/editvehicletype.jsp";
			}else{
				request.setAttribute("errorMessage", "Unable to find Vehicle Type object.");
				dispatchedPage = "/managevehicles.jsp";
			}
		}else if (searchType != null && !searchType.isEmpty()){
			//Simple sets the right search type integer which the managevehicles.jsp page uses to sort the list
			int sType = 0;
			try{
				sType = Integer.parseInt(searchType);
				request.setAttribute("errorMessage","");
			}catch(NumberFormatException e){
				request.setAttribute("errorMessage","Passed a non-numeric value.");
			}finally{
				request.setAttribute("searchType", sType);
			}
			dispatchedPage = "/managevehicletypes.jsp";
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
		IVehicleTypeManager ivtm = (VehicleTypeDAO) session.getAttribute("vehicleTypeMgr");
		if (ivtm == null){
			ivtm = new VehicleTypeDAO();
			session.setAttribute("vehicleTypeMgr", ivtm);
		}	
		String action = request.getParameter("action");
		String dispatchedPage = "/managevehicletypes.jsp";
		if (action != null && !action.isEmpty()){
			if(action.equalsIgnoreCase("addVehicleType")){
				//adding a vehicle type
				int id = addVehicleType(request,ivtm);
				if (id == 0){
					System.err.println("Problem saving vehicle type to db.");
					dispatchedPage = "/addvehicletype.jsp";
				}else{
					request.setAttribute("errorMessage","");
					dispatchedPage = "/managevehicletypes.jsp";
				}
			}else if (action.equalsIgnoreCase("editVehicleType")){
				System.out.println("editVehicleType action");
				String errorMessage = "";
				VehicleType vType = (VehicleType)session.getAttribute("vehicleType");
				if (vType == null){
					String vehicleType = request.getParameter("vehicleTypeID");
					if (vehicleType ==  null || vehicleType.isEmpty()){
						errorMessage = "No vehicle type requested.";
					}else{
						//Create vehicle type object
						int vehicleTypeID = Integer.parseInt(vehicleType);
						vType = ivtm.getVehicleType(vehicleTypeID);
					}
				}
				if (editVehicleType(request,ivtm,vType)){
					request.setAttribute("errorMessage", "");
					dispatchedPage = "/managevehicletypes.jsp";
				}else{
					request.setAttribute("vehicleType", vType);
					dispatchedPage = "/editvehicletype.jsp";
				}
			}
		}else{
			request.setAttribute("errorMessage", "Unknown POST request");
			dispatchedPage = "/login.jsp";
		}
		dispatcher = ctx.getRequestDispatcher(dispatchedPage);
		dispatcher.forward(request,response);
	}

	/**
	 * Edit a vehicle type.
	 * @param request
	 * @param ivtm
	 * @param vType
	 * @return
	 */
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
					boolean isTypeInUse = ivtm.isTypeInUse(type);
					//If the vehicle type has not been created OR the type is unchanged.
					if (!isTypeInUse || type.equalsIgnoreCase(vType.getType())){
						ivtm.updateVehicleType(vehicleTypeID,type, hourlyPrice, dailyPrice);
						System.out.println(vehicleTypeID+"-"+type+"-"+hourlyPrice+"-"+dailyPrice);
						return true;
					}else{
						errorMessage = "Type (" + type + ") already in use.";
					}
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

	/**
	 * Adding a Vehicle type
	 * @param request
	 * @param ivtm
	 * @return
	 */
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
					boolean isTypeInUse = ivtm.isTypeInUse(type);
					if (!isTypeInUse){
						vehicleTypeID = ivtm.addVehicleType(type, hourlyPrice, dailyPrice);
						System.out.println(vehicleTypeID+"-"+type+"-"+hourlyPrice+"-"+dailyPrice);
					}else{
						errorMessage = "Vehicle type already in use.";
					}
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
