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

import com.youdrive.helpers.MembershipDAO;
import com.youdrive.helpers.UserDAO;
import com.youdrive.interfaces.IMembershipManager;
import com.youdrive.interfaces.IUserManager;
import com.youdrive.models.Membership;
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
		IMembershipManager imm = (MembershipDAO) ctx.getAttribute("membershipMgr");
		if (imm == null){
			imm = new MembershipDAO();
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
		IMembershipManager imm = (MembershipDAO) ctx.getAttribute("membershipMgr");
		if (imm == null){
			imm = new MembershipDAO();
			ctx.setAttribute("membershipMgr", imm);
		}
		String errorMessage = "", dispatchedPage = "";
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("addMembership")){
			int membershipID = addMembership(request,imm);
			if (membershipID == 0){
				//Add membership failed
				dispatchedPage = "/admin.jsp";
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
			
		}else{
			dispatchedPage = "/index.jsp";
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
	
	private boolean deleteMembership(HttpServletRequest request,IMembershipManager imm, Membership membership){
		
		return false;
	}
}
