package com.bignerdranch.android.clientsidechrip;

import java.util.ArrayList;

public interface ChirpStorage {

	public ArrayList<Chirp> getChirps(String email);
	public void addChirp(Chirp c);
	
}
