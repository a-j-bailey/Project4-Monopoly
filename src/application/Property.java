package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/*
 * Expected text file line for locations/properties:
 * 		location,Color,Name,Tax,Price,Mortgage,houseCost,rent,rentHouse1,rentHouse2,rentHouse3,rentHouse4,rentHouse5
 */

public class Property extends Location{
	private int value;
	
	private int rent;
	
	private int mortgageValue;
		
	private boolean isMortgaged;
	
	private boolean isBought;
	
	/*
	 * We should just have main read in the txt file values. just make a loop that builds an arrayList or something. 
	 */
	public Property(int position, String color, String name, int price, int mortgage, int rent) { //note I skipped the ones that don't apply to Property
		super(position, color, name);
		this.value = price;
		this.rent = rent;
		
		
	}
	
}
