package com.youdrive.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.youdrive.helpers.LocationDAO;
import com.youdrive.helpers.MembershipDAO;
import com.youdrive.helpers.ReservationDAO;
import com.youdrive.helpers.UserDAO;
import com.youdrive.helpers.VehicleDAO;
import com.youdrive.helpers.VehicleTypeDAO;
import com.youdrive.interfaces.ILocationManager;
import com.youdrive.interfaces.IMembershipManager;
import com.youdrive.interfaces.IReservationManager;
import com.youdrive.interfaces.IUserManager;
import com.youdrive.interfaces.IVehicleManager;
import com.youdrive.interfaces.IVehicleTypeManager;
import com.youdrive.models.Membership;
import com.youdrive.models.Reservation;
import com.youdrive.models.User;

/**
 * Servlet implementation class MembershipManagement
 */
@WebServlet("/MembershipManagement")
public class MembershipManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MembershipManagement() {
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
		IMembershipManager imm = (MembershipDAO) session.getAttribute("membershipMgr");
		if (imm == null){
			imm = new MembershipDAO();
			session.setAttribute("membershipMgr", imm);
		}
		String errorMessage = "", dispatchedPage = "";
		String membershipID = request.getParameter("membershipID");
		if (membershipID != null && !membershipID.isEmpty()){
			try{
				System.out.println("membership ID found.");
				int mID = Integer.parseInt(membershipID);
				Membership membership = imm.getMembership(mID);
				session.setAttribute("membership", membership);
				dispatchedPage = "/editmembership.jsp";
			}catch(NumberFormatException e){
				errorMessage = "Invalid membership id.";
				dispatchedPage = "/admin.jsp";
			}
		}else{
			dispatchedPage = "/admin.jsp";
		}
		request.setAttribute("errorMessage", errorMessage);
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
		IMembershipManager imm = (MembershipDAO) session.getAttribute("membershipMgr");
		IUserManager ium = (UserDAO)session.getAttribute("userMgr");
		if (imm == null){
			imm = new MembershipDAO();
			session.setAttribute("membershipMgr", imm);
		}
		if (ium == null){
			ium = new UserDAO();
			session.setAttribute("userMgr", ium);
		}		
		String dispatchedPage = "";
		String action = request.getParameter("action");
		if (action != null && !action.isEmpty()){
			if (action.equalsIgnoreCase("addMembership")){
				int membershipID = addMembership(request,imm);
				if (membershipID == 0){
					//Add membership failed
					dispatchedPage = "/addmembership.jsp";
				}else{
					dispatchedPage = "/managememberships.jsp";				
				}
			}else if (action.equalsIgnoreCase("editMembership")){
				System.out.println("Editmembership action");
				String id = request.getParameter("membershipID");
				if (id == null || id.isEmpty()){
					request.setAttribute("errorMessage", "Invalid membership object.");
					dispatchedPage = "/managememberships.jsp";
				}else{
					try{
						int membershipID = Integer.parseInt(id);
						Membership member = imm.getMembership(membershipID);
						if (member != null){
							if (editMembership(request,imm,member)){
								request.setAttribute("errorMessage", "");
								dispatchedPage = "/managememberships.jsp";
							}else{
								session.setAttribute("membership", member);
								dispatchedPage = "/editmembership.jsp";
							}
						}else{
							request.setAttribute("errorMessage", "Membership object not found.");
							dispatchedPage = "/managememberships.jsp";
						}
					}catch(NumberFormatException e){
						request.setAttribute("errorMessage", "Invalid membership id type");
						dispatchedPage = "/managememberships.jsp";
					}catch(Exception e){
						request.setAttribute("errorMessage", e.getMessage());
						dispatchedPage = "/managememberships.jsp";
					}
				}
			}else if (action.equalsIgnoreCase("deleteMembership")){
				//Admin function for removing membership plans
				deleteMembership(request,imm);
				dispatchedPage = "/managememberships.jsp";
			}else if (action.equalsIgnoreCase("extendMembership")){
				//By default, extend by 6 months. 
				//TODO allow use to choose new plan to extend to.
				//Assumes the user on usermembership.jsp is a customer. 
				System.out.println("extendMembership action called.");
				User user = (User)session.getAttribute("loggedInUser");
				if (user != null){
					if (user.isActive()){
						dispatchedPage = (user.isAdmin()) ? "/managecustomers.jsp":"/usermembership.jsp";
						//Id of 1 in Memberships table should be the default plan
						Membership defaultPlan = imm.getMembership(1);
						if (defaultPlan != null){
							java.util.Date currentExpDate = user.getMemberExpiration();
							Calendar newExpirationDate = Calendar.getInstance();
							newExpirationDate.setTime(currentExpDate);
							newExpirationDate.add(Calendar.MONTH, defaultPlan.getDuration());
							boolean result = ium.extendMembership(newExpirationDate.getTime(), user.getId());
							
							if (result){
								request.setAttribute("infoMessage","Successfully extended your membership plan. Your card has been charged $" + defaultPlan.getPrice());
							}else{
								request.setAttribute("errorMessage", "Unable to extend your membership at this time.");
							}
						}else{
							request.setAttribute("errorMessage", "Invalid membership plan selected. Admin problem.");
						}
					}else{
						dispatchedPage = "/login.jsp";
						request.setAttribute("errorMessage", "Your account has been deactivated.");
					}
				}else{
					dispatchedPage = "/login.jsp";
					request.setAttribute("errorMessage", "You are not logged in.");
				}
			}else if (action.equalsIgnoreCase("terminateUserMembership")){
				System.out.println("Terminate Membership action called.");
				String userID = request.getParameter("customerID");
				User loggedInUser = (User)session.getAttribute("loggedInUser");
				dispatchedPage = (loggedInUser.isAdmin()) ? "/managecustomers.jsp":"/usermembership.jsp";
				if (loggedInUser != null){
					if (userID != null && !userID.isEmpty()){
						try{
							int uID = Integer.parseInt(userID);	
							IReservationManager irm = (ReservationDAO) session.getAttribute("reservationMgr");						
							if (irm == null){
								irm = new ReservationDAO();
								session.setAttribute("reservationMgr", irm);
							}
							User user = ium.getUser(uID);
							if (user != null){
								//Check if user has outstanding reservations
								//and prompt them to return first. If not, then delete user
								//Delete the user
								ArrayList<Reservation> userReservations = irm.getUserReservations(uID);
								boolean foundActiveReservations = false;
								for (Reservation r : userReservations){
									//ReservationStatusList will only be of size 1 if it hasn't been returned or cancelled
									if (r.getReservationStatusList().size() == 1){
										foundActiveReservations = true;
										break;
									}
								}

								if (!foundActiveReservations){
									boolean result = ium.deactivateUser(uID);
									if (!result){
										request.setAttribute("errorMessage", "Unable to terminate this membership. Please contact an admin.");
									}else{
										dispatchedPage = (loggedInUser.isAdmin()) ? "/managecustomers.jsp":"/index.jsp";
										//Invalidate user and maybe session
										if (!loggedInUser.isAdmin()){
											session.setAttribute("loggedInUser", null);
											session.invalidate();
										}
									}
								}else{
									request.setAttribute("errorMessage", "Active reservations found. Please return or cancel all reservations before cancelling your membership.");
								}
							}else{
								request.setAttribute("errorMessage", "The selected user does not exist.");
							}
						}catch(NumberFormatException e){
							request.setAttribute("errorMessage", "Invalid customer ID found.");
						}
					}else{
						request.setAttribute("errorMessage", "No customer ID parameter found.");
					}
				}else{
					request.setAttribute("errorMessage", "Not authorized to perform this action.");
				}
			}else{
				dispatchedPage = "/index.jsp";
			}
		}else{
			request.setAttribute("errorMessage", "Unknown POST request");
			dispatchedPage = "/login.jsp";
		}
		dispatcher = ctx.getRequestDispatcher(dispatchedPage);
		dispatcher.forward(request,response);
	}


	private int addMembership(HttpServletRequest request,IMembershipManager imm){
		int membershipID = 0;
		String name = request.getParameter("membershipLevel");
		String price = request.getParameter("price");
		String duration = request.getParameter("duration");
		String errorMessage = "";
		if (name == null || name.isEmpty()){
			errorMessage = "Missing name";
		}else if (name == null || name.isEmpty()){
			errorMessage = "Missing price";
		}else if (name == null || name.isEmpty()){
			errorMessage = "Missing duration";
		}else{
			try{
				double Price = Double.parseDouble(price);
				int Duration = Integer.parseInt(duration);
				if (Price >= 0){
					if (Duration > 0){
						//Check if name is in use
						boolean isNameInUse = imm.isNameInUse(name);
						if (!isNameInUse){
							membershipID = imm.addMembership(name, Price, Duration);
						}else{
							errorMessage = "Please choose another name.";
						}
					}else{
						errorMessage = "Duration needs to be at least 1 month long.";
					}					
				}else{
					errorMessage = "Price needs to be a positive number.";
				}
			}catch(NumberFormatException e){
				errorMessage = "Unable to parse price or duration.";
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return membershipID;
	}

	private boolean editMembership(HttpServletRequest request,IMembershipManager imm, Membership membership){
		String name = request.getParameter("membershipLevel");
		String price = request.getParameter("price");
		String duration = request.getParameter("duration");
		String errorMessage = "";
		if (name == null || name.isEmpty()){
			errorMessage = "Missing name";
		}else if (name == null || name.isEmpty()){
			errorMessage = "Missing price";
		}else if (name == null || name.isEmpty()){
			errorMessage = "Missing duration";
		}else{
			try{
				double Price = Double.parseDouble(price);
				int Duration = Integer.parseInt(duration);
				if (Price >= 0){
					if (Duration > 0){
						//Check if name is in use
						boolean isNameInUse = imm.isNameInUse(name);
						if (!isNameInUse || name.equalsIgnoreCase(membership.getName())){
							return imm.updateMembership(membership.getId(), name, Price, Duration);
						}else{
							errorMessage = "Please choose another name.";
						}
					}else{
						errorMessage = "Duration needs to be at least 1 month long.";
					}					
				}else{
					errorMessage = "Price needs to be a positive number.";
				}
			}catch(NumberFormatException e){
				errorMessage = "Unable to parse price or duration.";
			}
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
	}

	private boolean deleteMembership(HttpServletRequest request,IMembershipManager imm){
		System.out.println("Calling deleteMembership");
		String errorMessage = "";
		String membershipID = request.getParameter("membershipID");
		if (membershipID != null && !membershipID.isEmpty()){
			try{
				int mID = Integer.parseInt(membershipID);
				//Get the number of users on this plan
				int numOfUsers = imm.getUsersOnMembership(mID);
				if (numOfUsers > 0){
					errorMessage = "Found " + numOfUsers + " users on this plan. Re-assign those members first.";					
				}else{
					Membership m = imm.getMembership(mID);
					if (m != null){
						boolean result = imm.deleteMembership(mID);
						if (!result){
							errorMessage = "Error deleting Membership Plan.";
						}else{
							return true;
						}
					}else{
						errorMessage = "Unable to find Membership type in system.";
					}
				}
			}catch(NumberFormatException e){
				errorMessage = "Invalid membership id format.";
			}
		}else{
			errorMessage = "Invalid parameter used.";
		}
		request.setAttribute("errorMessage", errorMessage);
		return false;
	}
}
