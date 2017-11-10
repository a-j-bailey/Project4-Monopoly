package application;

public class Residential extends Property{
	private int housePrice;
	
	private int numHouses;
	
	private int setNum;
	
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
	public Residential(int location, String Color, String name, int tax, int price, int mortgage, int houseCost, int rent, int rentHouse1, int rentHouse2, int rentHouse3, int rentHouse4, int rentHouse5){
		//TODO: Finish Constructor
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
	 * @return number of properties in a set
	 */
	public int getSetNum() {
		return setNum
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
