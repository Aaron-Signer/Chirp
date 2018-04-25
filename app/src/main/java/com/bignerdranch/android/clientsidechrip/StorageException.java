package com.bignerdranch.android.clientsidechrip;

public class StorageException extends Exception{

	private static final long serialVersionUID = 1L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable t) {
		super(message, t);
	}

	
}
