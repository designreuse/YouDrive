package com.youdrive.interfaces;

import com.youdrive.models.User;

public interface IUserManager {
	public int addAdmin(String username, String password, String firstName, String lastName, String email);
	public int addUser(User p);
}
