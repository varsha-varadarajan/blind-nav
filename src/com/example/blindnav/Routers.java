package com.example.blindnav;

public class Routers {
	
	String ssid;
	float x;
	float y;
	
	public Routers(String ssid,float x,float y)
	{
		this.ssid=ssid;
		this.x=x;
		this.y=y;

	}

	public Routers()
	{
	}

	public void setSsidd(String ssid)
	{
		this.ssid=ssid;
	}

	public String getSsid()
	{
		
		return this.ssid;
	}


	public void setx(float x)
	{
		this.x=x;
	}

	public float getx()
	{
		
		return this.x;
	}

	public void sety(float y)
	{
		this.y=y;
	}

	public float gety()
	{
		
		return this.y;
	}
	}



