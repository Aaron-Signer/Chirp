package com.bignerdranch.android.clientsidechrip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Database
{
    private static Database soleInstance=null;
    private ArrayList<User> u;

    public static Database get()
    {
        if (soleInstance == null)
            soleInstance = new Database();
        return soleInstance;
    }

    private Database()
    {
        u = new ArrayList<User>();
        u.add(new User("mgurneal@gmail.com", "mgurneal", "robot123"));
        u.add(new User("ninja@gmail.com", "ninja", "ninja123"));
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

    public boolean userReal(String usr)
    {
        for(int i = 0; i < u.size(); i++)
        {
            if(u.get(i).getUsername().equals(usr))
            {
                return true;
            }
        }
        return false;
    }

    public User getUser(String usr)
    {
        for(int i = 0; i < u.size(); i++)
        {
            if(u.get(i).getUsername().equals(usr))
            {
                return u.get(i);
            }
        }
        return null;

    }
}
