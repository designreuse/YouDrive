package com.youdrive.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.youdrive.helpers.VehicleDAO;
import com.youdrive.interfaces.IVehicleManager;
import com.youdrive.models.Comment;
import com.youdrive.models.User;
import com.youdrive.models.Vehicle;

/**
 * Servlet implementation class VehicleBrowser
 */
@WebServlet("/VehicleBrowser")
public class VehicleBrowser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VehicleBrowser() {
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
		IVehicleManager ivm = (VehicleDAO) session.getAttribute("vehicleMgr");
		if (ivm == null){
			ivm = new VehicleDAO();
			session.setAttribute("vehicleMgr", ivm);
		}
		String vehicleID = request.getParameter("vehicleID");
		String searchType = request.getParameter("searchType");
		String viewComments = request.getParameter("viewComments");
		String locationID = request.getParameter("locationID");
		String dispatchedPage = "/login.jsp";
		
		//Direct to reservation form for a specific vehicle
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
			
		//Sort vehicles list
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
			if( locationID != null && !locationID.isEmpty()){
				dispatchedPage = "/viewlocation.jsp";
			}
			else{
				dispatchedPage = "/browsevehicles.jsp";
			}
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
		dispatcher.forward(request,response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
