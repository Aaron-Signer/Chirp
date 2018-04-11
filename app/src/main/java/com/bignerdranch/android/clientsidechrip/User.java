package com.bignerdranch.android.clientsidechrip;

public class User
{
    private String password;
    private String username;
    private String email;

    public User(String e, String u, String p)
    {
        password=p;
        username=u;
        email=e;
    }

    public boolean correctPassword(String attempt)
    {
        return(attempt.equals(password));
    }

    public String getUsername()
    {
        return username;
    }

}
