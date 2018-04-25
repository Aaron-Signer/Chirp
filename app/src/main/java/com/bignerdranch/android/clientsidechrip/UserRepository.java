package com.bignerdranch.android.clientsidechrip;

import java.util.*;

public class UserRepository
{
	private ArrayList<User> users;
	private static UserRepository instance = null;
	
	private UserRepository()
	{
		resetRepository();
	}
	
	public static UserRepository getInstance()
	{
		if(instance == null)
			instance = new UserRepository();
		
		return instance;
	}
	
	public void resetRepository()
	{
		users = new ArrayList<User>();
		users.add(new User( "Aaron-Signer","signap22@wclive.westminster.edu", "Aaron"));
		users.add(new User( "Matt-Gurneal","gurnmc22@wclive.westminster.edu", "Matt"));
		users.add(new User( "Jamie-Thompson","thomjm22@wclive.westminster.edu", "Jamie"));
	}
	
	public void addUser(User u)
	{
		users.add(u);
	}
	
	public User getUserByEmail(String email)
	{
		for(int i = 0; i < users.size(); i++)
		{
			if(users.get(i).getEmail().equals(email))
				return users.get(i);
		}
		return null;
	}
	
	public ArrayList<User> getUsers()
	{
		return users;
	}
	
	public void removeUserByEmail(String email)
	{
		for(int i = 0; i < users.size(); i++)
			if(users.get(i).getEmail().equals(email))
				users.remove(i);
	}
	
	public int getNumberOfUsers()
	{
		return users.size();
	}
	
}
