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
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.models.Location;

/**
 * Servlet implementation class LocationBrowser
 */
@WebServlet("/LocationBrowser")
public class LocationBrowser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationBrowser() {
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
			session.setAttribute("locationMgr", ilm);
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
				dispatchedPage =  "/viewlocation.jsp";
			}else{
				request.setAttribute("errorMessage", "Unable to find Location object.");
				dispatchedPage = "/browselocations.jsp";
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
			dispatchedPage = "/browselocations.jsp";

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
