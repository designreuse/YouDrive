package com.youdrive.interfaces;

import com.youdrive.models.User;

public interface IUserManager {
	public int addAdmin(String username, String password, String firstName, String lastName, String email);
	public int addUser(User p);
	public int addAdminUser(String username, String password, String firstName, String lastName, String email);
	public int addMembership(String name, double price, int duration);
	public String deleteMembership(int id);
	User authenticateUser(String username, String password);
}