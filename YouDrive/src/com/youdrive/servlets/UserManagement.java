package com.youdrive.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.UserDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IUserManager;
import com.youdrive.models.User;

/**
 * Servlet implementation class UserManagement
 */
@WebServlet("/UserManagement")
public class UserManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserManagement() {
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
		IUserManager ium = (UserDAO) ctx.getAttribute("ium");
		if (ium == null){
			ium = new UserDAO();
		}
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("addUser1")){
			String isAdmin = request.getParameter("isAdmin");
			if (isAdmin != null){
				int userID = addAdminUser(request,ium);
				if (userID != 0){
					request.setAttribute("errorMessage","");
					dispatcher = ctx.getRequestDispatcher("/admin.jsp");
				}else{
					dispatcher = ctx.getRequestDispatcher("/adduser.jsp");
				}
			}else{
				if (addRegularUserPg1(request,ium)){
					request.setAttribute("errorMessage","");
					dispatcher = ctx.getRequestDispatcher("/addUserPg2.jsp");
				}else{
					dispatcher = ctx.getRequestDispatcher("/adduser.jsp");
				}
			}
		}else if (action.equalsIgnoreCase("registerUser1")){
			if (addRegularUserPg1(request,ium)){
				request.setAttribute("errorMessage","");				
				dispatcher = ctx.getRequestDispatcher("/registration_page2.jsp");
			}else{
				dispatcher = ctx.getRequestDispatcher("/registration_page1.jsp");
			}
		}else if (action.equalsIgnoreCase("login")){
			User user = authenticateUser(request,ium);
			if (user != null){
				request.setAttribute("errorMessage","");
				if (user.isAdmin()){
					dispatcher = ctx.getRequestDispatcher("/admin.jsp");
				}else{
					dispatcher = ctx.getRequestDispatcher("/user.jsp");
				}
			}else{
				dispatcher = ctx.getRequestDispatcher("/login.jsp");
			}
		}else{

		}
		dispatcher.forward(request,response);
	}

	private User authenticateUser(HttpServletRequest request,IUserManager ium){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username == null || username.isEmpty()){
			request.setAttribute("errorMessage", "Missing username");
		}else if (password == null || password.isEmpty()){
			request.setAttribute("errorMessage", "Missing password");
		}else{
			User user = ium.authenticateUser(username, password);
			return user;
		}
		return null;
	}

	private int addAdminUser(HttpServletRequest request,IUserManager ium){
		int userID = 0;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		if (username == null || username.isEmpty()){
			request.setAttribute("errorMessage", "Missing username");
		}else if (password == null || password.isEmpty()){
			request.setAttribute("errorMessage", "Missing password");
		}else if (firstName == null || firstName.isEmpty()){
			request.setAttribute("errorMessage", "Missing first name");
		}else if (lastName == null || lastName.isEmpty()){
			request.setAttribute("errorMessage", "Missing last name");
		}else if (email == null || email.isEmpty()){
			request.setAttribute("errorMessage", "Missing email address");
		}else{
			request.setAttribute("errorMessage","");
			userID = ium.addAdminUser(username, password, firstName, lastName, email);
		}
		return userID;
	}

	private boolean addRegularUserPg1(HttpServletRequest request,IUserManager ium){
		int userID = 0;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		HashMap<String,String> addUser1 = new LinkedHashMap<String,String>();
		if (username == null || username.isEmpty()){
			request.setAttribute("errorMessage", "Missing username");
		}else if (password == null || password.isEmpty()){
			request.setAttribute("errorMessage", "Missing password");
		}else if (firstName == null || firstName.isEmpty()){
			request.setAttribute("errorMessage", "Missing first name");
		}else if (lastName == null || lastName.isEmpty()){
			request.setAttribute("errorMessage", "Missing last name");
		}else if (email == null || email.isEmpty()){
			request.setAttribute("errorMessage", "Missing email address");
		}else{
			request.setAttribute("errorMessage","");
			addUser1.put("username",username);
			addUser1.put("password", password);
			addUser1.put("firstName", firstName);
			addUser1.put("lastName", lastName);
			addUser1.put("email", email);
			request.setAttribute("registration_page1", addUser1);
			return true;
		}
		return false;
	}
}
