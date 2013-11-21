package com.youdrive.servlets;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.youdrive.helpers.UserDAO;
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
		HttpSession session = request.getSession();
		IUserManager ium = (UserDAO) session.getAttribute("userMgr");
		if (ium == null){
			ium = new UserDAO();
			session.setAttribute("userMgr", ium);
		}
		String errorMessage = "";
		String userID = request.getParameter("userID");
		String searchType = request.getParameter("searchType");
		String dispatchedPage = "/login.jsp";
		if (userID != null && !userID.isEmpty()){
			try{
				int uID = Integer.parseInt(userID);
				User user = ium.getUser(uID);
				session.setAttribute("user", user);
				dispatchedPage = "/edituser.jsp";
			}catch(NumberFormatException e){
				errorMessage = "Invalid userID.";
			}
		}else if (searchType != null && !searchType.isEmpty()){
			//Default sort is by lastname i.e. number 2
			int sType = 2;
			try{
				sType = Integer.parseInt(searchType);
				request.setAttribute("errorMessage","");
			}catch(NumberFormatException e){
				request.setAttribute("errorMessage","Passed a non-numeric value.");
			}finally{
				request.setAttribute("searchType", sType);
			}
			dispatchedPage = "/manageusers.jsp";
		}else{
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			if (loggedInUser != null){
				if (loggedInUser.isAdmin()){
					dispatchedPage = "/manageusers.jsp";
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
		IUserManager ium = (UserDAO) session.getAttribute("userMgr");
		if (ium == null){
			ium = new UserDAO();
			session.setAttribute("userMgr", ium);
		}
		String action = request.getParameter("action");
		//TODO replace dispatcher = blah blah with single call at bottom
		String dispatchedPage = "/index.jsp";
		if (action != null && !action.isEmpty()){
			if (action.equalsIgnoreCase("addAdmin")){
				System.out.println("addAdmin Action");
				int userID = addAdminUser(request,ium);
				if (userID != 0){
					request.setAttribute("errorMessage","");
					dispatchedPage = "/admin.jsp";
				}else{
					dispatchedPage = "/adduser.jsp";
				}
			}else if (action.equalsIgnoreCase("registerUser1")){
				System.out.println("Page 1 of User Registration Action");
				if (addRegularUserPg1(request,ium,session,ctx)){
					request.setAttribute("errorMessage","");				
					dispatchedPage = "/registration_page2.jsp";
				}else{
					dispatchedPage = "/registration_page1.jsp";
				}
			}else if (action.equalsIgnoreCase("registerUser2")){
				//Second page of Registration
				@SuppressWarnings("unchecked")
				HashMap<String,String> pg1 = (LinkedHashMap<String, String>) session.getAttribute("registration_page1");
				if (pg1 != null && !pg1.isEmpty()){				
					int userID = addRegularUserPg2(request,ium,pg1);
					if (userID == 0){
						dispatchedPage = "/registration_page1.jsp";
					}else{
						User user = ium.getUser(userID);
						session.setAttribute("loggedInUser", user);
						dispatchedPage = "/user.jsp";
					}
				}else{
					System.err.println("Empty map coming from registration page 2");
					request.setAttribute("errorMessage", "Missing registration information from first page.");
					dispatchedPage = "/registration_page1.jsp";
				}
			}else if (action.equalsIgnoreCase("login")){
				User user = authenticateUser(request,ium);
				if (user != null){
					request.setAttribute("errorMessage","");
					//Send user to right landing page
					if (user.isAdmin()){
						dispatchedPage = "/admin.jsp";
					}else{
						dispatchedPage = "/user.jsp";
					}
					//Stash logged in user to session context
					session.setAttribute("loggedInUser", user);
				}else{
					request.setAttribute("errorMessage", "Invalid credentials.");
					dispatchedPage = "/login.jsp";
				}
			}else if (action.equalsIgnoreCase("AdminEditUser")){
				System.out.println("admin edituser action");
				String isAdminStr = request.getParameter("isAdmin");
				//TODO fix this
				if (isAdminStr == null || isAdminStr.isEmpty()){
					request.setAttribute("errorMessage", "Unable to determine user type.");
					dispatchedPage = "/manageusers.jsp";
				}else{
					boolean isAdmin = false;
					if (isAdminStr.equalsIgnoreCase("true")){
						isAdmin = true;
					}
					String userID = request.getParameter("id");
					if (userID == null || userID.isEmpty()){
						request.setAttribute("errorMessage", "Invalid user.");
						dispatchedPage = "/manageusers.jsp";
					}else{
						try{
							int id = Integer.parseInt(userID);
							User user = ium.getUser(id);
							if (user != null){
								if (editUser(request,ium,user,isAdmin)){
									request.setAttribute("errorMessage","");
									dispatchedPage = "/manageusers.jsp";
								}else{
									request.setAttribute("user", user);
									dispatchedPage = "/edituser.jsp";
								}
							}else{
								request.setAttribute("errorMessage", "User not found.");
								dispatchedPage = "/manageusers.jsp";
							}
						}catch(NumberFormatException e){
							request.setAttribute("errorMessage", "Invalid user id.");
							dispatchedPage = "/manageusers.jsp";
						}catch(Exception e){
							request.setAttribute("errorMessage", e.getMessage());
							dispatchedPage = "/manageusers.jsp";
						}
					}
				}
			}
		}else{
			request.setAttribute("errorMessage", "Invalid POST request.");
			dispatchedPage = "/login.jsp";
		}
		dispatcher = ctx.getRequestDispatcher(dispatchedPage);
		dispatcher.forward(request,response);
	}

	/**
	 * Returns the User object if successfully logged in and null otherwise
	 * @param request
	 * @param ium
	 * @return
	 */
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

	/**
	 * adding an admin user to the system
	 * Return the userid if successful and false otherwise
	 * @param request
	 * @param ium
	 * @return
	 */
	private int addAdminUser(HttpServletRequest request,IUserManager ium){
		int userID = 0;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String errorMessage = "";
		if (username == null || username.isEmpty()){
			errorMessage = "Missing username";
		}else if (password == null || password.isEmpty()){
			errorMessage = "Missing password";
		}else if (firstName == null || firstName.isEmpty()){
			errorMessage = "Missing firstName";
		}else if (lastName == null || lastName.isEmpty()){
			errorMessage = "Missing lastName";
		}else if (email == null || email.isEmpty()){
			errorMessage = "Missing email";
		}else{
			boolean isUsernameInUse = ium.isUsernameInUse(username);
			boolean isEmailInUse = ium.isEmailInUse(email);
			if (isUsernameInUse){
				errorMessage = "This username is already in use.";
			}else if (isEmailInUse){
				errorMessage = "This email address is already in use.";
			}else{	
				userID = ium.addAdminUser(username, password, firstName, lastName, email);
				errorMessage = "";
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return userID;
	}

	/**
	 * First page of adding a user to the system
	 * Returns true if this page is successful and stashes
	 * a HashMap of the variables entered into a session variable for use in the final step on page 2
	 * @param request
	 * @param ium
	 * @param session
	 * @param ctx
	 * @return
	 */
	private boolean addRegularUserPg1(HttpServletRequest request,IUserManager ium, HttpSession session, ServletContext ctx){
		int userID = 0;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String errorMessage = "";
		HashMap<String,String> addUser1 = new LinkedHashMap<String,String>();
		if (username == null || username.isEmpty()){
			errorMessage = "Missing username";
		}else if (password == null || password.isEmpty()){
			errorMessage = "Missing password";
		}else if (firstName == null || firstName.isEmpty()){
			errorMessage = "Missing first name";
		}else if (lastName == null || lastName.isEmpty()){
			errorMessage = " Missing last name";
		}else if (email == null || email.isEmpty()){
			errorMessage = "Missing email address";
		}else{
			boolean isUsernameInUse = ium.isUsernameInUse(username);
			boolean isEmailInUse = ium.isEmailInUse(email);
			if (isUsernameInUse){
				errorMessage = "This username is already in use.";
			}else if (isEmailInUse){
				errorMessage = "This email address is already in use.";
			}else{				
				//Stash user into into map and send in request object
				addUser1.put("username",username);
				addUser1.put("password", password);
				addUser1.put("firstName", firstName);
				addUser1.put("lastName", lastName);
				addUser1.put("email", email);
				session.setAttribute("registration_page1", addUser1);
				System.out.println("added registration_page1 to session.");
				request.setAttribute("errorMessage","");
				return true;
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
	}

	/**
	 * Second registration page for the user to complete registration
	 * Returns the user's id if successfully to system and 0 otherwise.
	 * @param request
	 * @param ium
	 * @param page1_details
	 * @return
	 */
	private int addRegularUserPg2(HttpServletRequest request, IUserManager ium,HashMap<String,String> page1_details) {
		int userID = 0;
		String membershipLevel = request.getParameter("membershipLevel");
		String address = request.getParameter("address");
		String state = request.getParameter("state");
		String license = request.getParameter("license");
		String ccNumber = request.getParameter("ccNumber");
		String ccType = request.getParameter("ccType");
		String ccSecurityCode = request.getParameter("ccSecurityCode");
		String ccExpirationDate = request.getParameter("ccExpiration");
		String errorMessage = "";

		if (membershipLevel == null || membershipLevel.isEmpty()){
			errorMessage = "Must select a membership level.";
		}else if (address == null || address.isEmpty()){
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
		}else if(ccExpirationDate == null || ccExpirationDate.isEmpty()){
			errorMessage = "Missing credit card expiration.";
		}else{
			try{
				int ccCode = Integer.parseInt(ccSecurityCode);
				int memberLevel = Integer.parseInt(membershipLevel);
				if (validateCreditCard(ccNumber)){
					ArrayList<Integer> expDates = validateExpirationDate(ccExpirationDate);
					if (!expDates.isEmpty()){
						User user = new User();
						user.setAddress(address);
						user.setCcExpirationDate(ccExpirationDate);
						user.setCcNumber(ccNumber);
						user.setCcSecurityCode(ccCode);
						user.setState(state);
						user.setLicense(license);
						user.setMembershipLevel(memberLevel);
						user.setFirstName(page1_details.get("firstName"));
						user.setLastName(page1_details.get("lastName"));
						user.setEmail(page1_details.get("email"));
						user.setUsername(page1_details.get("username"));
						user.setPassword(page1_details.get("password"));
						user.setAdmin(false);
						userID = ium.addUser(user);
						errorMessage = "";
					}else{
						errorMessage = "Invalid expiration dates. Please enter MM/YYYY format.";
					}
				}else{
					errorMessage = "Invalid credit card number; Must be 16 digits.";
				}
			}catch(NumberFormatException e){
				errorMessage = "Invalid format for the security code or invalid membership level selected.";
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return userID;
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
			//TODO check for duplicate
			if (ium.updateAdminUser(id, username, password, firstName, lastName, email)){
				return true;
			}else{
				errorMessage = "Unable to update the user details.";
			}
		}else{
			//Admin editing a non=admin user so require extra stuff
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
					int ccSecurityCode = Integer.parseInt(ccCode);
					if (validateCreditCard(ccNum)){
						if (ium.updateUser(id, username, password, firstName, lastName, state, license, email, address, ccType, ccNum, ccSecurityCode, ccExpirationDate)){
							return true;
						}else{
							errorMessage = "Unable to update the user details.";
						}
					}else{
						errorMessage = "Invalid credit card number; Must be 16 digits.";
					}
				}catch(NumberFormatException e){
					errorMessage = "Error parsing security code.";
				}catch(Exception e){
					errorMessage = e.getMessage();
				}
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
	}

	/**
	 * Validate the expiration date entered i.e. must be later than current month if it expires in current year or 
	 * must expire >= any number of years before the current year
	 * Returns a list of 2 numbers if successful and an empty list otherwise.
	 * @param code
	 * @return
	 */
	private ArrayList<Integer> validateExpirationDate(String expDate){
		String mth = "";
		String yr = "";
		ArrayList<Integer> results = new ArrayList<Integer>();
		if (!expDate.isEmpty()){
			int slashIndex = expDate.indexOf("/");
			mth = expDate.substring(0,slashIndex);
			yr = expDate.substring(slashIndex+1);
			try{
				Calendar cal = Calendar.getInstance(); 			
				int month = Integer.parseInt(mth);
				//Month should be in range of 1 to 12 and year cannot be less than current year
				if (month <= 12 && month >= 1){
					int year = Integer.parseInt(yr);
					//Year should be greater than current calendar year OR
					//Be in the current year AND have the expiration month greater than the calendar month
					int calYear = cal.get(Calendar.YEAR);
					int calMonth = cal.get(Calendar.MONTH);
					if (year > calYear || 
							(year >= cal.get(Calendar.YEAR) && month > calMonth)){
						results.add(month);
						results.add(year);
					}
				}
			}catch(NumberFormatException e){
				System.err.println("Invalid expiration date.");
			}
		}
		return results;
	}

	/**
	 * Credit Card number is made of numbers and should be 16 characters
	 * @param ccNumber
	 * @return
	 */
	private boolean validateCreditCard(String ccNumber){
		return (ccNumber.matches("[0-9]+") && ccNumber.length() == 16); 
	}
}
