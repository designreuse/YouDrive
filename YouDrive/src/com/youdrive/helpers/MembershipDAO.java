package com.youdrive.helpers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.youdrive.interfaces.IMembershipManager;
import com.youdrive.models.Membership;
import com.youdrive.util.ConnectionManager;
import com.youdrive.util.Constants;

public class MembershipDAO implements IMembershipManager{

	private PreparedStatement addMembershipStmt;
	private PreparedStatement deleteMembershipStmt;
	private PreparedStatement getAllMembershipsStmt;
	private PreparedStatement getMembershipStmt;
	private PreparedStatement checkMembershipNameStmt;
	private PreparedStatement updateMembershipStmt;
	private PreparedStatement updateDurationStmt;
	private PreparedStatement countCustomersInMembershipStmt;
	private Constants cs = Constants.getInstance();
	private Connection conn = null;
	
	public MembershipDAO(){
		try{
			conn = ConnectionManager.getInstance();
			getAllMembershipsStmt = conn.prepareStatement("select * from " + Constants.MEMBERSHIP + " order by price");
			getMembershipStmt = conn.prepareStatement("select * from " + Constants.MEMBERSHIP + " where id = ?");
			addMembershipStmt = conn.prepareStatement("insert into " + Constants.MEMBERSHIP + " values (DEFAULT,?,?,?)" ,Statement.RETURN_GENERATED_KEYS);
			deleteMembershipStmt = conn.prepareStatement("delete from " + Constants.MEMBERSHIP + " where id = ?"); 
			checkMembershipNameStmt = conn.prepareStatement("select * from " + Constants.MEMBERSHIP + " where name = ?");
			updateMembershipStmt = conn.prepareStatement("update " + Constants.MEMBERSHIP + " set name = ?,price = ?,duration = ? where id = ?");
			countCustomersInMembershipStmt = conn.prepareStatement("select count(*) from " + Constants.USERS + " u left outer join " + Constants.MEMBERSHIP + " m on m.id = u.membershipLevel where u.isAdmin = 0 and m.id = ?");
			updateDurationStmt = conn.prepareStatement("update " + Constants.USERS + " set memberExpiration = ? where membershipLevel = ?");
			System.out.println("Instantiated MembershipDAO");
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with MembershipDAO constructor: " + e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	@Override
	public Membership getMembership(int id){
		Membership member = null;
		try{
			getMembershipStmt.setInt(1, id);
			ResultSet rs = getMembershipStmt.executeQuery();
			if (rs.next()){
				member = new Membership(id,rs.getString("name"),rs.getDouble("price"),rs.getInt("duration"));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			
		}
		return member;
	}
	
	@Override
	public ArrayList<Membership> getAllMemberships(){
		ArrayList<Membership> results = new ArrayList<Membership>();
		try{
			ResultSet rs = getAllMembershipsStmt.executeQuery();
			while (rs.next()){
				results.add(new Membership(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"),rs.getInt("duration")));
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
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
	public boolean deleteMembership(int id) {
		try{
			deleteMembershipStmt.setInt(1, id);
			deleteMembershipStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with deleteMembership method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return false;
	}
	
	@Override
	public boolean isNameInUse(String name){
		try{
			checkMembershipNameStmt.setString(1, name);
			ResultSet rs = checkMembershipNameStmt.executeQuery();
			if (rs.next()){
				return true;
			}
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with isNameInUse method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return false;
	}
	
	@Override
	public  boolean updateMembership(int id, String name, double price, int duration){
		try{
			updateMembershipStmt.setString(1, name);
			updateMembershipStmt.setDouble(2, price);
			updateMembershipStmt.setInt(3, duration);
			updateMembershipStmt.setInt(4, id);
			updateMembershipStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with updateMembership method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return false;
	}
	
	@Override
	public int getUsersOnMembership(int membershipID){
		int result = 0;
		try{
			countCustomersInMembershipStmt.setInt(1, membershipID);
			ResultSet rs = countCustomersInMembershipStmt.executeQuery();
			if (rs.next()){
				result = rs.getInt(1);
			}
			return result;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with getUsersOnMembership method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return result;
	}
	
	@Override
	public boolean updateExpirationDate(Date expirationDate,int membershipLevel){
		try{
			updateDurationStmt.setDate(1, expirationDate);
			updateDurationStmt.setInt(2, membershipLevel);
			updateDurationStmt.executeUpdate();
			return true;
		}catch(SQLException e){
			System.err.println(cs.getError(e.getErrorCode()));
		}catch(Exception e){
			System.err.println("Problem with updateExpirationDate method: " + e.getClass().getName() + ": " + e.getMessage());			
		}
		return false;
	}
}
