package com.bignerdranch.android.clientsidechrip;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable
{
    private static Database soleInstance=null;
    private String email;
    private boolean loggedIn;


    public static Database get() {
        if (soleInstance == null)
            soleInstance = new Database();
        return soleInstance;
    }

    private Database()
    {
        loggedIn=false;
        email="";
    }

    public static void createDefaultDatabase()
    {
        Database d = new Database();
        soleInstance = d;
    }

    public static void load(File f)throws IOException, ClassNotFoundException
    {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        soleInstance = (Database)ois.readObject();
        ois.close();
    }

    public void save(File f) throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(this);
        oos.close();
    }

    public void logIn(String e)
    {
        email = e;
        loggedIn=true;
    }

    public void logOut()
    {
        email="";
        loggedIn=false;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public String getEmail(){
        return email;
    }


}
