package application;


import java.util.Scanner;


/*
 * Expected text file line for locations/properties:
 * 		location,Color,Name,Tax,Price,Mortgage,houseCost,rent,rentHouse1,rentHouse2,rentHouse3,rentHouse4,rentHouse5
 * 
 * 
 * there are duplicates with the getters. Fix that. 
 * 
 * also, make getters for:
 * 		isMortgaged
 * 		isBought
 */

public class Property extends Location{
	

	private int owner;
	private int value;
	
	private int mortgageValue;
	private boolean isMortgaged;	
	private boolean isBought;
	
	/**
	 * Constructor for a Property
	 * 
	 * @param position
	 * @param color
	 * @param name
	 * @param price
	 * @param mortgage
	 * @param rent
	 */
	public Property(String inputLine) {
		super(inputLine);
		
		this.isMortgaged = false;
		this.isBought = false;
		this.owner = 0;
		
		Scanner lnScn = new Scanner(inputLine);
		lnScn.useDelimiter(",");
		
		lnScn.next();
		lnScn.next();			//These three are taken care of in super
		lnScn.next();
		
		lnScn.next(); 			//Skip over Tax space
		
		this.value = lnScn.nextInt();
		this.mortgageValue = lnScn.nextInt();
		
		
		lnScn.close();
	}
	
	
	/**
	 * 
	 * @param isMortgaged 
	 * Changes isMortgage status
	 * 
	 */
	public void setIsMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}
	
	/**
	 * 
	 * @return value of property
	 */
	public int getValue() {
		return value;
	}
	
	
	
	/**
	 * 
	 * @return mortgage Value
	 */
	public int getMortgageValue() {
		return mortgageValue;
	}
	/**
	 * 
	 * @return true if property is mortgaged
	 */
	public boolean isMortgaged() {
		return isMortgaged;
	}
	/**
	 * 
	 * @return true if property is bought
	 */
	public boolean isBought() {
		return isBought;
	}
	
	
	/**
	 * returns owners
	 */
	public int getOwner() {
		return owner;
	}
	
	/**
	 * 
	 * @param change owner to input integer
	 */
	public void changeOwner(int newOwner) {
		this.owner = newOwner;
		if (!(owner == 0)) {
			this.isBought = false;					
		}
		else {
			this.isBought = true;
		}
	}
}
