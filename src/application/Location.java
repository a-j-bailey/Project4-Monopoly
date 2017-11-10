package application;

/*
 * Expected text file line for locations/properties:
 * 		location,Color,Name,Tax,Price,Mortgage,houseCost,rent,rentHouse1,rentHouse2,rentHouse3,rentHouse4,rentHouse5
 */

public class Location {
	
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
	public Location(int position, String color, String name) {
		this.position = position;
		this.color = color;
		this.propertyName = name;
		
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
}
