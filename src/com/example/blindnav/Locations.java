package com.example.blindnav;

public class Locations
{
	int id;
	String r_name;
	int x;
	int y;

public Locations(int id,String r_name,int x,int y)
{
	this.id=id;
	this.r_name=r_name;
	this.x=x;
	this.y=y;

}

public Locations()
{
}

public Locations(int x,int y){
this.x=x;
this.y=y;

}
public void setId(int id)
{
	this.id=id;
}

public int getId()
{
	
	return this.id;
}
public void setName(String r_name)
{
	this.r_name=r_name;
}

public String getName()
{
	
	return this.r_name;
}


public void setxLoc(int x)
{
	this.x=x;
}

public int getxLoc()
{
	
	return this.x;
}

public void setyLoc(int y)
{
	this.y=y;
}

public int getyLoc()
{
	
	return this.y;
}
}
