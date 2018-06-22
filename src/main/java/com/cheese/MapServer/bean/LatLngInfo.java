package com.cheese.MapServer.bean;

public class LatLngInfo
{
	private double latitude;
	private double longitude;

	public LatLngInfo()
	{
	}

	public LatLngInfo(double latitude, double longitude)
	{
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
}