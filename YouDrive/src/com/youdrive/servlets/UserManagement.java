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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		IUserManager ium = (UserDAO) ctx.getAttribute("userMgr");
		if (ium == null){
			ium = new UserDAO();
		}
		String errorMessage = "";
		String userID = request.getParameter("userID");
		if (userID != null && !userID.isEmpty()){
			try{
				int uID = Integer.parseInt(userID);
				User user = ium.getUser(uID);
				ctx.setAttribute("user", user);
				dispatcher = ctx.getRequestDispatcher("/edituser.jsp");
			}catch(NumberFormatException e){
				errorMessage = "Invalid userID.";
			}
		}else{
			dispatcher = ctx.getRequestDispatcher("/manageusers.jsp");
		}
		request.setAttribute("errorMessage", errorMessage);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext ctx = this.getServletContext();
		RequestDispatcher dispatcher = null;
		IUserManager ium = (UserDAO) ctx.getAttribute("userMgr");
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
				//TODO if admin is adding a regular user, direct them to the registration_page_2
				if (addRegularUserPg1(request,ium)){
					request.setAttribute("errorMessage","");
					dispatcher = ctx.getRequestDispatcher("/addUserPg2.jsp");
				}else{
					dispatcher = ctx.getRequestDispatcher("/adduser.jsp");
				}
			}
		}else if (action.equalsIgnoreCase("addUser2")){
			//Second page of Registration
			int userID = addRegularUserPg2(request,ium);
			if (userID == 0){
				dispatcher = ctx.getRequestDispatcher("/registration_page1.jsp");
			}else{
				dispatcher = ctx.getRequestDispatcher("/user.jsp");
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
				request.setAttribute("errorMessage", "Invalid credentials.");
				dispatcher = ctx.getRequestDispatcher("/login.jsp");
			}
		}else if (action.equalsIgnoreCase("editUser")){
			System.out.println("edituser action");
			String isAdminStr = request.getParameter("isAdmin");
			if (isAdminStr == null || isAdminStr.isEmpty()){
				request.setAttribute("errorMessage", "Unable to determine user type.");
			}else{
				boolean isAdmin = false;
				if (isAdminStr.equalsIgnoreCase("true")){
					isAdmin = true;
				}
				String userID = request.getParameter("id");
				if (userID == null || userID.isEmpty()){
					request.setAttribute("errorMessage", "Invalid user.");
				}else{
					try{
						int id = Integer.parseInt(userID);
						User user = ium.getUser(id);
						if (user != null){
							if (editUser(request,ium,user,isAdmin)){
								request.setAttribute("errorMessage","");
								dispatcher = ctx.getRequestDispatcher("/manageusers.jsp");
							}else{
								request.setAttribute("user", user);
								dispatcher = ctx.getRequestDispatcher("/edituser.jsp");
							}
						}else{
							request.setAttribute("errorMessage", "User not found.");
							dispatcher = ctx.getRequestDispatcher("/manageusers.jsp");
						}
					}catch(NumberFormatException e){
						request.setAttribute("errorMessage", "Invalid user id.");
						dispatcher = ctx.getRequestDispatcher("/manageusers.jsp");
					}catch(Exception e){
						request.setAttribute("errorMessage", e.getMessage());
						dispatcher = ctx.getRequestDispatcher("/manageusers.jsp");
					}
				}
			}
		}
		dispatcher.forward(request,response);
	}

	private int addRegularUserPg2(HttpServletRequest request, IUserManager ium) {
		int userID = 0;
		String address = request.getParameter("address");
		String state = request.getParameter("state");
		String license = request.getParameter("license");
		String ccNumber = request.getParameter("ccNumber");
		String ccType = request.getParameter("ccType");
		String ccSecurityCode = request.getParameter("ccSecurityCode");
		String ccExpiration = request.getParameter("ccExpiration");
		String errorMessage = "";
		if (address == null || address.isEmpty()){
			errorMessage = "Missing address.";
		}else if(license == null || license.isEmpty()){
			errorMessage = "Missing license.";
		}else if(state == null || state.isEmpty()){
			errorMessage = "Missing state.";
		}else if(ccType == null || ccType.isEmpty()){
			errorMessage = "Missing credit card type.";
		}else if(ccNumber == null || ccNumber.isEmpty()){
			errorMessage = "Missing credit card number.";
		}else if(ccSecurityCode == null || ccSecurityCode.isEmpty()){
			errorMessage = "Missing credit card security code.";
		}else if(ccExpiration == null || ccExpiration.isEmpty()){
			errorMessage = "Missing credit card expiration.";
		}else{
			
		}
		return userID;
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

	private boolean editUser(HttpServletRequest request,IUserManager ium, User user, boolean isAdmin){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		int id = user.getId();
		String errorMessage = "";
		if (username == null || username.isEmpty()){
			errorMessage = "Missing username";
		}else if (firstName == null || firstName.isEmpty()){
			errorMessage = "Missing firstName";
		}else if (lastName == null || lastName.isEmpty()){
			errorMessage = "Missing lastName";
		}else if (email == null || email.isEmpty()){
			errorMessage = "Missing email";
		}else if (password == null || password.isEmpty()){
			errorMessage = "Missing password";
		}else if (isAdmin){
			if (ium.updateAdminUser(id, username, password, firstName, lastName, email)){
				return true;
			}else{
				errorMessage = "Unable to update the user details.";
			}
		}else{
			String state = request.getParameter("state");
			String license = request.getParameter("license");
			String address = request.getParameter("address");
			String ccType = request.getParameter("ccType");
			String ccNum = request.getParameter("ccNumber");
			String ccCode = request.getParameter("ccSecurityCode");
			String ccExpirationDate = request.getParameter("ccExpirationDate");
			if (state == null || state.isEmpty()){
				errorMessage = "Missing state";
			}else if (license == null || license.isEmpty()){
				errorMessage = "Missing license";
			}else if (address == null || address.isEmpty()){
				errorMessage = "Missing address";
			}else if (ccType == null || ccType.isEmpty()){
				errorMessage = "Missing credit card type";
			}else if (ccNum == null || ccNum.isEmpty()){
				errorMessage = "Missing credit card number";
			}else if (ccCode == null || ccCode.isEmpty()){
				errorMessage = "Missing credit card security code";
			}else if (ccExpirationDate == null || ccExpirationDate.isEmpty()){
				errorMessage = "Missing credit card expiration date.";
			}else{
				try{
					int ccNumber = Integer.parseInt(ccNum);
					int ccSecurityCode = Integer.parseInt(ccCode);
					if (ium.updateUser(id, username, password, firstName, lastName, state, license, email, address, ccType, ccNumber, ccSecurityCode, ccExpirationDate)){
						return true;
					}else{
						errorMessage = "Unable to update the user details.";
					}
				}catch(NumberFormatException e){
					errorMessage = "Error parsing credit card number or security code.";
				}catch(Exception e){
					errorMessage = e.getMessage();
				}
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
	}
}
