package com.bignerdranch.android.clientsidechrip;

import android.util.Log;

import java.util.Comparator;

public class ChirpComparator implements Comparator<Chirp> {

    @Override
    public int compare(Chirp c1, Chirp c2)
    {
//        Log.d("InCompare", c1.getDate().toString() + " " + c2.getDate().toString() + " " +  c1.getDate().compareTo(c2.getDate()));
        return c2.getDate().compareTo(c1.getDate());

//        if(c1.getDate().after(c2.getDate()))
//            return -1;
//        else if(c1.getDate().before(c2.getDate()))
//            return 1;
//        else
//            return 0;

    }
}
