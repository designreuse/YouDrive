package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.youdrive.interfaces.IMembershipManager;
import com.youdrive.models.Membership;
import com.youdrive.util.Constants;

public class MembershipDAO implements IMembershipManager{

	private PreparedStatement addMembershipStmt;
	private PreparedStatement deleteMembershipStmt;
	private PreparedStatement getMembershipsStmt;
	private PreparedStatement updateMembershipStmt;
	private Constants cs = new Constants();
	private Connection conn = null;
	
	public MembershipDAO(){
		try{
			getMembershipsStmt = conn.prepareStatement("select * from " + Constants.MEMBERSHIP + " order by price");
			addMembershipStmt = conn.prepareStatement("insert into " + Constants.MEMBERSHIP + " values (DEFAULT,?,?,?)" ,Statement.RETURN_GENERATED_KEYS);
			deleteMembershipStmt = conn.prepareStatement("delete from " + Constants.MEMBERSHIP + " where id = ?"); 
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with MembershipDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	@Override
	public ArrayList<Membership> getAllMemberships(){
		ArrayList<Membership> results = new ArrayList<Membership>();
		try{
			ResultSet rs = getMembershipsStmt.executeQuery();
			while (rs.next()){
				results.add(new Membership(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"),rs.getInt("duration")));
			}
		}catch(SQLException e){
			System.err.println(e.getErrorCode());
		}catch(Exception e){
			System.err.println("Problem with getAllMemberships: " + e.getClass().getName() + ": " + e.getMessage());
		}
		return results;
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
}
