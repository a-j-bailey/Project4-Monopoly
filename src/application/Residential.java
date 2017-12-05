package application;

import java.util.ArrayList;
import java.util.Scanner;

public class Residential extends Property{
	private int houseCost;
	
	private int numHouses;
	
	private int numSet;
	private ArrayList<Integer> rentList;
	private int rent;
	
	
	
	/**
	 * Constructs Residential property from the string that is passed to it
	 * @param inputLine
	 */
	public Residential(String inputLine){
		super(inputLine);
		rentList = new ArrayList<Integer>();
		
		Scanner lnScn = new Scanner(inputLine);
		lnScn.useDelimiter(",");
		
		
		
		lnScn.next();
		lnScn.next();			//These three are taken care of in super
		lnScn.next();
		
		lnScn.next(); 			//Skip over Tax space
		
		lnScn.next();		//These two are taken care of in super
		lnScn.next();
		
		
		this.houseCost = lnScn.nextInt();
		this.rentList.add(lnScn.nextInt());
		this.rentList.add(lnScn.nextInt());
		this.rentList.add(lnScn.nextInt());
		this.rentList.add(lnScn.nextInt());
		this.rentList.add(lnScn.nextInt());
		this.rentList.add(lnScn.nextInt());
		this.numSet = lnScn.nextInt();
		
		numHouses = 0;
		
		rent = rentList.get(0);
		
		
		
		lnScn.close();
	}
	
	/**
	 * Build houses 
	 * @param numHousesBuilt
	 */
	public void buildHouse(int numHousesBuilt) {
		numHouses = numHousesBuilt;
		this.rent = rentList.get(numHouses);
	}





	
	
	/**
	 * 
	 * @return number of houses on property
	 */
	public int getNumHouses(){
		return this.numHouses;
	}
	

	
	/**
	 * 
	 * 
	 * @param newRent Changes rent of the property 
	 */
	
	public void setRent(int newRent) {
		this.rent = newRent;
	}
	
	
	/**
	 * 
	 * @return rent 
	 */
	public int getRent() {
		return this.rent;
	}
	
	
	public int getHouseCost() {
		return this.houseCost;
	}
	
	
	/**
	 * 
	 * @return number of properties in a set
	 */
	public int getNumSet() {
		return numSet;
	}
	
	
}
