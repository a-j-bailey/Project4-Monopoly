package application;

import java.util.Scanner;

public class Residential extends Property{
	private int houseCost;
	
	private int numHouses;
	
	private int numSet;
	private int rent;
	private int rentHouse1;
	private int rentHouse2;
	private int rentHouse3;
	private int rentHouse4;
	private int rentHouse5;
	
	
	/**
	 * 
	 * @param location
	 * @param Color
	 * @param name
	 * @param tax
	 * @param price
	 * @param mortgage
	 * @param houseCost
	 * @param rent
	 * @param rentHouse1
	 * @param rentHouse2
	 * @param rentHouse3
	 * @param rentHouse4
	 * @param rentHouse5
	 * Constructor for Residential Properties
	 */
	
	
	public Residential(String inputLine){
		super(inputLine);
		
		Scanner lnScn = new Scanner(inputLine);
		lnScn.useDelimiter(",");
		
		
		
		lnScn.next();
		lnScn.next();			//These three are taken care of in super
		lnScn.next();
		
		lnScn.next(); 			//Skip over Tax space
		
		lnScn.next();		//These two are taken care of in super
		lnScn.next();
		
		
		this.houseCost = lnScn.nextInt();
		this.rent = lnScn.nextInt();
		this.rentHouse1 = lnScn.nextInt();
		this.rentHouse2 = lnScn.nextInt();
		this.rentHouse3 = lnScn.nextInt();
		this.rentHouse4 = lnScn.nextInt();
		this.rentHouse5 = lnScn.nextInt();
		this.numSet = lnScn.nextInt();
		
		numHouses = 0;
		
		
		
		lnScn.close();
	}
	
	/**
	 * build a house and change rent value
	 * @return 
	 */
	public void buildHouse(int numHousesBuilt) {
		numHouses = numHousesBuilt;
		if(numHouses == 1) {
			this.setRent(this.rentHouse1);
		}
		else if(numHouses == 2) {
			this.setRent(this.rentHouse2);
		}
		else if(numHouses == 3) {
			this.setRent(this.rentHouse3);
		}
		else if(numHouses == 4) {
			this.setRent(this.rentHouse4);
		}
		else if(numHouses == 5) {
			this.setRent(this.rentHouse5);
		}
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
	
	/**
	 * 
	 * @return rent with one house on the property
	 */
	public int getRentHouse1() {
		return rentHouse1;
	}
	/**
	 * 
	 * @return rent with two houses on the property
	 */
	public int getRentHouse2() {
		return rentHouse2;
	}
	/**
	 * 
	 * @return rent with three houses on the property
	 */
	public int getRentHouse3() {
		return rentHouse3;
	}
	/**
	 * 
	 * @return rent with four houses on the property
	 */
	public int getRentHouse4() {
		return rentHouse4;
	}
	
	/**
	 * 
	 * @return rent with five houses on the property
	 */
	public int getRentHouse5() {
		return rentHouse5;
	}
}
