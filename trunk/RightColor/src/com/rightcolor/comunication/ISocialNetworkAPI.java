package com.rightcolor.comunication;

public interface ISocialNetworkAPI {
	public static final String STATUS_UPDATE_SUCCESS = "Your status has been successfully updated.";
	public static final String STATUS_UPDATE_FAILURE = "Your status has not been updated.";
	public static final String STATUS_UPDATE_URL = "http://bit.ly/1mKRzVi"; //https://play.google.com/store/apps/details?id=com.rightcolor
    
    public void updateStatus(String status);
}
