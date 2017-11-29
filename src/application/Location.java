package application;

import java.util.Scanner;

/*
 * Expected text file line for locations/properties:
 * 		location,Color,Name,Tax,Price,Mortgage,houseCost,rent,rentHouse1,rentHouse2,rentHouse3,rentHouse4,rentHouse5
 * 
 * 
 * 
 * possibly add a variable for the type of property it is (residential, utility, etc.) to determine which constructor it uses
 * 
 * 
 */
public class Location {
	private String type;
	private int position;
	private String color;
	private String propertyName;
	
	/**
	 * 
	 * @param position
	 * @param color
	 * @param name
	 * Constructor for location
	 */
	public Location(String inputLine) {
		Scanner lnScn = new Scanner(inputLine);
		lnScn.useDelimiter(",");
		this.type = lnScn.next();
		this.propertyName = lnScn.next();
		System.out.println("\t" + this.propertyName);
		lnScn.close();
	}
	
	/**
	 * @return int for position around board (0-39)
	 */
	public int getPos(){
		return position;
	}
	
	/**
	 * @return String hex value of color.
	 */
	public String getColor(){
		return color;
	}
	
	/**
	 * 
	 * @return property Name
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	public String getPropertyType(){
		return type;
	}
}
