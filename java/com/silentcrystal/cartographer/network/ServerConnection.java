package com.silentcrystal.cartographer.network;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerConnection implements Parcelable {
	public static final int CONNECTED = 0;
	public static final int NOT_FOUND = 1;
	public static final int UNAUTHORIZED = 2;
	
	protected String serverAddress;
	protected String userName;
	protected String userPassword;
	protected int status;
	
	public ServerConnection(String serverAddress, String userName, String userPassword) {
		this.serverAddress = serverAddress;
		this.userName = userName;
		this.userPassword = userPassword;
	}
	
	public int describeContents() {
		return 0;
	}
	public void writeToParcel(Parcel out, int flags) {
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public void connect() {
		this.status = CONNECTED;
	}
}
