package com.bignerdranch.android.clientsidechrip;

import java.util.*;

public class User {

	private String handle, email, password;
	private int ids [];
	
	public User(String h, String e, String p)
	{
		handle = h;
		email = e;
		password = p;
	}

	public boolean rightPassword(String pw)
	{
		return pw.equals(password);
	}
	
	public String getHandle()
	{
		return handle;
	}

	public String getEmail()
	{
		return email;
	}
	
	public void setHandle(String h)
	{
		handle = h;
	}
	
	public void setEmail(String e)
	{
		email = e;
	}
	
}
