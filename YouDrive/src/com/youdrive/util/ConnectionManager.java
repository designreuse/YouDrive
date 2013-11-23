package com.youdrive.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import com.youdrive.util.Constants;

public class ConnectionManager {
	private static Connection conn = null;

	public static Connection getInstance(){
		if (conn == null){
			try {
				DBInit();
			}catch (ClassNotFoundException e){
				System.err.println("Class not found: " + e.getMessage());
			}catch(SQLException e) {
				System.err.println("SQLException: " + e.getErrorCode());
			}
		}
		return conn;
	}

	private static void DBInit() throws ClassNotFoundException, SQLException {
		//TODO Consider using Connection pooling
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.username, Constants.password);
	}
}
