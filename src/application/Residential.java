package application;


/*
 * 
 * 
 * 
 * 
 */



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
	
	
	public Residential(int location, String color, String name, int value, int mortgageValue, int rent, boolean isMortgaged, boolean isBought, int housePrice, int rentHouse1, int rentHouse2, int rentHouse3, int rentHouse4, int rentHouse5){
		super(location, color, name, value, rent, mortgageValue, isMortgaged, isBought);
		this.housePrice=housePrice;
		this.numHouses=0;
		this.rentHouse1 = rentHouse1;
		this.rentHouse2 = rentHouse2;
		this.rentHouse3 = rentHouse3;
		this.rentHouse4 = rentHouse4;
		this.rentHouse5 = rentHouse5;
		
		this.setNum=0;		//TBD if we need this or not. 
	}
	
	/**
	 * build a house and change rent value
	 * @return 
	 */
	public void buildHouse() {
		if(numHouses < 5) {
			numHouses++;
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
		else {
			//return some error or something that no more houses can be built
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
	 * @return number of properties in a set
	 */
	public int getSetNum() {
		return setNum;
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
