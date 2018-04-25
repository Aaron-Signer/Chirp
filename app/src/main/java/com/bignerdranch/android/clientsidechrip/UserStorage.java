package com.bignerdranch.android.clientsidechrip;

import java.util.*;

public interface UserStorage
{

	public void addUser(User u);
	public User getUserByEmail(String email);
	public ArrayList<User> getUsers();
	public void removeUserByEmail(String email);
	public int getNumberOfUsers();
	public void updateUser(String email, User u);
	//public

}
