package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class UserDAO {
	private PreparedStatement getAllUsersStmt;
	private PreparedStatement getUserStmt;
	private PreparedStatement authenticateUserStmt;
	private PreparedStatement addAdminStmt;
	private PreparedStatement addUserStmt;
	private PreparedStatement deleteUserByUsernameStmt;
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
			System.out.println("Instantiated LocationDAO");
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with LocationDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
