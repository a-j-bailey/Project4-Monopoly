package application;

import java.io.File;
import java.io.FileNotFoundException;
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
	
	private String type;
	
	private int owner;
	
	private int value;
	
	private int rent;
	
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
	public Property(int position, String color, String name, int price, int mortgageValue, int rent, boolean isMortgaged, boolean isBought) { //note I skipped the ones that don't apply to Property
		super(position, color, name);
		this.value = price;
		this.rent = rent;
		this.mortgageValue = mortgageValue;
		this.isMortgaged = isMortgaged;
		this.isBought = isBought;
		
		
	}
	
	
	/**
	 * 
	 * @param isMortgaged
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
	
	public void setRent(int newRent) {
		this.rent = newRent;
	}
	
	
	/**
	 * 
	 * @return rent value of property
	 */
	
	public int getRent() {
		return rent;
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
	
}
