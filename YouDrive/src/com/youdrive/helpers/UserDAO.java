package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.youdrive.interfaces.IUserManager;
import com.youdrive.models.User;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class UserDAO implements IUserManager {
	private PreparedStatement getAllUsersStmt;
	private PreparedStatement getUserStmt;
	private PreparedStatement getUserByUsernameStmt;
	private PreparedStatement authenticateUserStmt;
	private PreparedStatement addRegularUserStmt;
	private PreparedStatement addAdminUserStmt;
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
			getAllUsersStmt = conn.prepareStatement("select * from " + Constants.USERS + " order by lastName");
			getUserStmt = conn.prepareStatement("select * from " + Constants.USERS + " where id = ?");
			getUserByUsernameStmt = conn.prepareStatement("select * from " + Constants.USERS + " where username = ?");
			addRegularUserStmt = conn.prepareStatement("insert into " + Constants.USERS + " values (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?)",Statement.RETURN_GENERATED_KEYS);
			addAdminUserStmt = conn.prepareStatement("insert into " + Constants.USERS + " values (DEFAULT,?,?,?,?,DEFAULT,DEFAULT,?,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,1,DEFAULT,DEFAULT)",Statement.RETURN_GENERATED_KEYS);
			deleteUserByUsernameStmt = conn.prepareStatement("select * from " + Constants.USERS + " where username = ?");
			addMembershipStmt = conn.prepareStatement("insert into " + Constants.MEMBERSHIP + " values (DEFAULT,?,?,?)" ,Statement.RETURN_GENERATED_KEYS);
			deleteMembershipStmt = conn.prepareStatement("delete from " + Constants.MEMBERSHIP + " where id = ?"); 
			authenticateUserStmt = conn.prepareStatement("select * from " + Constants.USERS + " where username = ? and password = ?");
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
			addRegularUserStmt.setString(1, username);
			addRegularUserStmt.setString(2, password);
			addRegularUserStmt.setString(3, firstName);
			addRegularUserStmt.setString(4, lastName);
			addRegularUserStmt.setString(5, email);
			addRegularUserStmt.executeUpdate();
			ResultSet rs = addRegularUserStmt.getGeneratedKeys();
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
	public int addAdminUser(String username, String password, String firstName, String lastName, String email) {
		int userID = 0;
		try{
			addAdminUserStmt.setString(1, username);
			addAdminUserStmt.setString(2,password);
			addAdminUserStmt.setString(3,firstName);
			addAdminUserStmt.setString(4,lastName);
			addAdminUserStmt.setString(5,email);
			addAdminUserStmt.executeUpdate();
			ResultSet rs = addAdminUserStmt.getGeneratedKeys();
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
	public int addUser(User p) {
		int userID = 0;
		try{
			addRegularUserStmt.setString(1, p.getUsername());
			addRegularUserStmt.setString(2, p.getPassword());
			addRegularUserStmt.setString(3, p.getFirstName());
			addRegularUserStmt.setString(4, p.getLastName());
			addRegularUserStmt.setString(5, p.getState());
			addRegularUserStmt.setString(6, p.getLicense());
			addRegularUserStmt.setString(7, p.getEmail());
			addRegularUserStmt.setString(8, p.getAddress());
			addRegularUserStmt.setString(9, p.getCcType());
			addRegularUserStmt.setInt(10, p.getCcNumber());
			addRegularUserStmt.setInt(11, p.getCcSecurityCode());
			addRegularUserStmt.setString(12, p.getCcExpirationDate());
			addRegularUserStmt.setDate(13, p.getMemberExpiration());
			addRegularUserStmt.setInt(14, p.getMembershipLevel());
			userID = addRegularUserStmt.executeUpdate();
			ResultSet rs = addRegularUserStmt.getGeneratedKeys();
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
			System.err.println("Problem with deleteMembership method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return errorCode;
	}

	@Override
	public User authenticateUser(String username, String password){
		User user = null;
		try{
			authenticateUserStmt.setString(1,username);
			authenticateUserStmt.setString(2,password);
			ResultSet rs = authenticateUserStmt.executeQuery();
			if (rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setAdmin(rs.getBoolean("isAdmin"));
				return user;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with authenticateUser method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return user;
	}

	@Override
	public User getUser(int userID) {
		User user = null;
		try{
			getUserStmt.setInt(1,userID);
			ResultSet rs = authenticateUserStmt.executeQuery();
			if (rs.next()){
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String state = rs.getString("state");
				String license = rs.getString("license");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String ccType = rs.getString("ccType");
				int ccNumber = rs.getInt("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				Date memberExpiration = rs.getDate("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				user = new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel);
				return user;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getUser method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try{
			getUserByUsernameStmt.setString(1,username);
			ResultSet rs = authenticateUserStmt.executeQuery();
			if (rs.next()){
				int id = rs.getInt("id");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String state = rs.getString("state");
				String license = rs.getString("license");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String ccType = rs.getString("ccType");
				int ccNumber = rs.getInt("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				Date memberExpiration = rs.getDate("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				user = new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel);
				return user;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getUserByUsername method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return user;
	}

	@Override
	public ArrayList<User> getAllUsers() {
		ArrayList<User> results = new ArrayList<User>();
		try{
			ResultSet rs = getAllUsersStmt.executeQuery();			
			while (rs.next()){
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String state = rs.getString("state");
				String license = rs.getString("license");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String ccType = rs.getString("ccType");
				int ccNumber = rs.getInt("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				Date memberExpiration = rs.getDate("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				results.add(new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel));				
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllUsers method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}
}
