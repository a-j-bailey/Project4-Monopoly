package application;

import java.util.Scanner;

/*
 * Expected text file line for locations/properties:
 * 		Type,Name,Color,Tax,Price,Mortgage,houseCost,rent,rentHouse1,rentHouse2,rentHouse3,rentHouse4,rentHouse5,NumberOfPropInSet	
 * 
 * 
 * 
 * possibly add a variable for the type of property it is (residential, utility, etc.) to determine which constructor it uses
 * 
 * 
 */
public class Location {
	private String type;
	private String color;
	private String propertyName;
	
	/**
	 * 
	 * @param color
	 * @param name
	 * @param Type
	 * Constructor for location
	 */
	public Location(String inputLine) {
		Scanner lnScn = new Scanner(inputLine);
		lnScn.useDelimiter(",");
		this.type = lnScn.next();
		this.propertyName = lnScn.next();
		System.out.println("this is the poperty name: " + propertyName);
		this.color = lnScn.next();
		lnScn.close();
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
