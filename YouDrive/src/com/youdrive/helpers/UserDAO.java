package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.youdrive.interfaces.IUserManager;
import com.youdrive.models.User;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class UserDAO implements IUserManager {
	private PreparedStatement getAllUsersStmt;
	private PreparedStatement getUserStmt;
	private PreparedStatement authenticateUserStmt;
	private PreparedStatement addAdminStmt;
	private PreparedStatement addUserStmt;
	private PreparedStatement deleteUserByUsernameStmt;
	private PreparedStatement addMembershipStmt;
	private PreparedStatement deleteMembershipStmt;
	private PreparedStatement updateMembershipStmt;

	private Constants cs = new Constants();
	private Connection conn = null;
	private static LocationDAO ldao = null;

	public UserDAO(){
		try{
			conn = ConnectionManager.getInstance();
			getAllUsersStmt = conn.prepareStatement("select * from " + Constants.USERS + " order by name");
			getUserStmt = conn.prepareStatement("select * from " + Constants.USERS + " where id = ?");
			authenticateUserStmt = conn.prepareStatement("select * from " + Constants.USERS + " where name = ?");
			addAdminStmt = conn.prepareStatement("insert into " + Constants.USERS + " values (DEFAULT,?,?,?,?,DEFAULT,DEFAULT,?,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,?,DEFAULT,DEFAULT)",Statement.RETURN_GENERATED_KEYS);
			addUserStmt = conn.prepareStatement("insert into " + Constants.USERS + " values (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			deleteUserByUsernameStmt = conn.prepareStatement("select * from " + Constants.USERS + " where username = ?");
			addMembershipStmt = conn.prepareStatement("insert into " + Constants.MEMBERSHIP + " values (DEFAULT,?,?,?)" ,Statement.RETURN_GENERATED_KEYS);
			deleteMembershipStmt = conn.prepareStatement("delete from " + Constants.MEMBERSHIP + " where id = ?"); 
			System.out.println("Instantiated LocationDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with LocationDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public int addAdmin(String username, String password, String firstName,String lastName, String email) {
		int userID = 0;
		try{
			addAdminStmt.setString(1, username);
			addAdminStmt.setString(2, password);
			addAdminStmt.setString(3, firstName);
			addAdminStmt.setString(4, lastName);
			addAdminStmt.setString(5, email);
			addAdminStmt.executeUpdate();
			ResultSet rs = addAdminStmt.getGeneratedKeys();
			if (rs.next()){
				userID = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addAdmin method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return userID;
	}

	@Override
	public int addUser(User p) {
		int userID = 0;
		try{
			addAdminStmt.setString(1, p.getUsername());
			addAdminStmt.setString(2, p.getPassword());
			addAdminStmt.setString(3, p.getFirstName());
			addAdminStmt.setString(4, p.getLastName());
			addAdminStmt.setString(5, p.getState());
			addAdminStmt.setString(6, p.getLicense());
			addAdminStmt.setString(7, p.getAddress());
			addAdminStmt.setString(8, p.getCcType());
			addAdminStmt.setInt(9, p.getCcNumber());
			addAdminStmt.setInt(10, p.getCcSecurityCode());
			addAdminStmt.setDate(11, p.getCcExpirationDate());
			addAdminStmt.setBoolean(12, p.isAdmin());
			addAdminStmt.setDate(13, p.getMemberExpiration());
			userID = addAdminStmt.executeUpdate();
			ResultSet rs = addAdminStmt.getGeneratedKeys();
			if (rs.next()){
				userID = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addUser method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return userID;
	}

	@Override
	public int addMembership(String name, double price, int duration) {
		int membershipID = 0;
		try{
			addMembershipStmt.setString(1, name);
			addMembershipStmt.setDouble(2, price);
			addMembershipStmt.setInt(3, duration);
			addMembershipStmt.executeUpdate();
			ResultSet rs = addMembershipStmt.getGeneratedKeys();
			if (rs.next()){
				membershipID = rs.getInt(1);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with addMembership method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return membershipID;
	}

	@Override
	public String deleteMembership(int id) {
		String errorCode = "";
		try{
			deleteMembershipStmt.setInt(1, id);
			deleteMembershipStmt.executeUpdate();
		}catch(SQLException e){
			errorCode = String.valueOf(e.getErrorCode());
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			errorCode = "Error";
			System.err.println("Problem with addMembership method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return errorCode;
	}
}
