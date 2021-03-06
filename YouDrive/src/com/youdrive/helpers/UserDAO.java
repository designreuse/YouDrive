package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import com.youdrive.interfaces.IUserManager;
import com.youdrive.models.User;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class UserDAO implements IUserManager {
	private PreparedStatement getAllAdminsStmt;
	private PreparedStatement getAllCustomersStmt;
	private PreparedStatement getUserStmt;
	private PreparedStatement getUserByUsernameStmt;
	private PreparedStatement authenticateUserStmt;
	private PreparedStatement addRegularUserStmt;
	private PreparedStatement updateAdminUserStmt;
	private PreparedStatement updateRegularUserStmt;
	private PreparedStatement addAdminUserStmt;
	private PreparedStatement checkUsernameStmt;
	private PreparedStatement checkEmailStmt;
	private PreparedStatement deactivateUserStmt;
	private PreparedStatement extendMembershipStmt;

	private Constants cs = Constants.getInstance();
	private Connection conn = null;

	public UserDAO(){
		try{
			conn = ConnectionManager.getInstance();
			getAllAdminsStmt = conn.prepareStatement("select * from " + Constants.USERS + " where isAdmin = 1 and isActive = 1 order by firstName");
			getAllCustomersStmt = conn.prepareStatement("select * from " + Constants.USERS + " where isAdmin = 0 and isActive = 1 order by firstName");
			getUserStmt = conn.prepareStatement("select * from " + Constants.USERS + " where id = ?");
			getUserByUsernameStmt = conn.prepareStatement("select * from " + Constants.USERS + " where username = ?");
			addRegularUserStmt = conn.prepareStatement("insert into " + Constants.USERS + " values (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?,NOW(),DEFAULT)",Statement.RETURN_GENERATED_KEYS);
			addAdminUserStmt = conn.prepareStatement("insert into " + Constants.USERS + " values (DEFAULT,?,?,?,?,DEFAULT,DEFAULT,?,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,1,DEFAULT,DEFAULT,NOW(),DEFAULT)",Statement.RETURN_GENERATED_KEYS);
			authenticateUserStmt = conn.prepareStatement("select * from " + Constants.USERS + " where username = ? and password = ?");
			updateAdminUserStmt = conn.prepareStatement("update " + Constants.USERS + " set username = ?, password = ?,firstName=?,lastName=?,email=? where id = ?");
			updateRegularUserStmt = conn.prepareStatement("update " + Constants.USERS + " set username = ?,password=?,firstName=?,lastName=?,state=?,license=?,email=?,address=?,ccType=?,ccNumber=?,ccSecurityCode=?,ccExpirationDate=? where id = ?");
			checkUsernameStmt = conn.prepareStatement("select username from " + Constants.USERS + " where username = ?");
			checkEmailStmt  = conn.prepareStatement("select email from " + Constants.USERS + " where email = ?");
			deactivateUserStmt = conn.prepareStatement("update " + Constants.USERS + " set memberExpiration = null, isActive = 0 where id = ?");
			extendMembershipStmt = conn.prepareStatement("update " + Constants.USERS + " set memberExpiration = ? where id = ?");
			System.out.println("Instantiated UserDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with UserDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}

	@Override
	public boolean extendMembership(java.util.Date newExpirationDate, int userID){
		try{
			extendMembershipStmt.setTimestamp(1, new java.sql.Timestamp(newExpirationDate.getTime()));
			extendMembershipStmt.setInt(2, userID);
			extendMembershipStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with extendMembership: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return false;
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
			addRegularUserStmt.setString(10, p.getCcNumber());
			addRegularUserStmt.setInt(11, p.getCcSecurityCode());
			addRegularUserStmt.setString(12, p.getCcExpirationDate());
			addRegularUserStmt.setTimestamp(13, new java.sql.Timestamp(p.getMemberExpiration().getTime()));
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
	public User authenticateUser(String username, String password){
		User user = null;
		try{
			authenticateUserStmt.setString(1,username);
			authenticateUserStmt.setString(2,password);
			ResultSet rs = authenticateUserStmt.executeQuery();
			if (rs.next()){
				int id = rs.getInt("id");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String state = rs.getString("state");
				String license = rs.getString("license");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String ccType = rs.getString("ccType");
				String ccNumber = rs.getString("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				Date memberExpiration = rs.getDate("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				boolean isActive = rs.getBoolean("isActive");
				user = new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel);
				user.setActive(isActive);
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
			ResultSet rs = getUserStmt.executeQuery();
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
				String ccNumber = rs.getString("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				java.util.Date memberExpiration = rs.getTimestamp("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				java.util.Date registrationDate = rs.getTimestamp("registrationDate");
				boolean isActive = rs.getBoolean("isActive");
				user = new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel);
				user.setDateCreated(registrationDate);
				user.setActive(isActive);
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
			ResultSet rs = getUserByUsernameStmt.executeQuery();
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
				String ccNumber = rs.getString("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				Date memberExpiration = rs.getDate("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				java.util.Date registrationDate = rs.getTimestamp("registrationDate");
				boolean isActive = rs.getBoolean("isActive");
				user = new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel);
				user.setDateCreated(registrationDate);
				user.setActive(isActive);
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
	public ArrayList<User> getAllAdmins() {
		ArrayList<User> results = new ArrayList<User>();
		try{
			ResultSet rs = getAllAdminsStmt.executeQuery();			
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
				String ccNumber = rs.getString("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				Date memberExpiration = rs.getDate("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				java.util.Date registrationDate = rs.getTimestamp("registrationDate");
				boolean isActive = rs.getBoolean("isActive");
				User temp = new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel);
				temp.setDateCreated(registrationDate);
				temp.setActive(isActive);
				results.add(temp);				
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllAdmins method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}

	@Override
	public boolean updateAdminUser(int id, String username, String password, String firstName, String lastName, String email){
		try{
			updateAdminUserStmt.setString(1,username);
			updateAdminUserStmt.setString(2, password);
			updateAdminUserStmt.setString(3, firstName);
			updateAdminUserStmt.setString(4, lastName);
			updateAdminUserStmt.setString(5, email);
			updateAdminUserStmt.setInt(6, id);
			updateAdminUserStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with updateAdminUser method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return false;
	}

	/**
	 * Update user membership into through a different interface
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param state
	 * @param license
	 * @param email
	 * @return
	 */
	@Override
	public boolean updateUser(int id, String username, String password, String firstName, String lastName, String state, String license, String email, String address, String ccType, String ccNumber, int ccSecurityCode, String ccExpirationDate){
		try{
			updateRegularUserStmt.setString(1,username);
			updateRegularUserStmt.setString(2, password);
			updateRegularUserStmt.setString(3, firstName);
			updateRegularUserStmt.setString(4, lastName);
			updateRegularUserStmt.setString(5, state);
			updateRegularUserStmt.setString(6, license);
			updateRegularUserStmt.setString(7, email);
			updateRegularUserStmt.setString(8, address);
			updateRegularUserStmt.setString(9, ccType);
			updateRegularUserStmt.setString(10, ccNumber);
			updateRegularUserStmt.setInt(11, ccSecurityCode);
			updateRegularUserStmt.setString(12, ccExpirationDate);
			updateRegularUserStmt.setInt(13, id);
			updateRegularUserStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with updateUser method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean isUsernameInUse(String username){
		try{
			checkUsernameStmt.setString(1, username);
			ResultSet rs = checkUsernameStmt.executeQuery();
			if (rs.next()){
				return true;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with isUsernameInUse method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean isEmailInUse(String email){
		try{
			checkEmailStmt.setString(1, email);
			ResultSet rs = checkEmailStmt.executeQuery();
			if (rs.next()){
				return true;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with isUsernameInUse method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return false;
	}

	@Override
	public ArrayList<User> getAllCustomers() {
		ArrayList<User> results = new ArrayList<User>();
		try{
			ResultSet rs = getAllCustomersStmt.executeQuery();			
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
				String ccNumber = rs.getString("ccNumber");
				int ccSecurityCode = rs.getInt("ccSecurityCode");
				String ccExpirationDate = rs.getString("ccExpirationDate");
				boolean isAdmin = rs.getBoolean("isAdmin");
				Date memberExpiration = rs.getDate("memberExpiration");
				int membershipLevel = rs.getInt("membershipLevel");
				java.util.Date registrationDate = rs.getTimestamp("registrationDate");
				boolean isActive = rs.getBoolean("isActive");
				User temp = new User(id, username, password, firstName,lastName, state, license, email,address, ccType, ccNumber, ccSecurityCode, ccExpirationDate, isAdmin,memberExpiration,  membershipLevel);
				temp.setDateCreated(registrationDate);
				temp.setActive(isActive);
				results.add(temp);
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getAllUsers method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
	}
	
	@Override
	public boolean deactivateUser(int userID){
		try{
			deactivateUserStmt.setInt(1, userID);
			deactivateUserStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with deactivateUser method: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return false;
	}
}
