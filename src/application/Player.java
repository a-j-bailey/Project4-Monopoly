package application;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	private ArrayList<Residential> residentialProperties;
	private ArrayList<Utility> utilityProperties;
	private int money = 1500;	
	private int numGetOutOfJailCards = 0;
	private String playerName;
	private int pos = 0;
	
	/**
	 * Constructs new player from string
	 * @param name
	 */
	public Player(String name){
		this.playerName = name;
	}
	
	/**
	 * Returns the amount of money a player has as an int
	 * @author adambailey
	 * @return
	 */
	public int getMoney(){
		return this.money;
	}

	/**
	 * returns the number of get out of jail free cards a player has
	 * @author adambailey
	 * @return
	 */
	public int getGetOutOfJailFreeCards(){
		return this.numGetOutOfJailCards;
	}
	
	/**
	 * increments get out of jail free cards by one
	 */
	public void addGetOutOfJailFreeCard(){
		this.numGetOutOfJailCards++;
	}
	
	/**
	 * decrements get out of jail free cards by one
	 */
	public void removeGetOutOfJailFreeCard(){
		this.numGetOutOfJailCards--;
	}
	
	/**
	 * Changes position by num passed to it
	 * can be positive or negative
	 * @param num
	 */
	public void changePos(int num){
		this.pos += num;
	}
	
	/**
	 * Sets position to given location
	 * @param num
	 */
	public void setPos(int num){
		this.pos = num;
		//TODO: Call that locations action.
	}
	
	/**
	 * Returns players position as an int.
	 * @return
	 */
	public int getPos(){
		return this.pos;
	}
	
	/**
	 * Removes amount of money from player
	 * @param amount
	 */
	public void fine(int amount){
		if (amount > this.money){
			this.money -= amount;
		} else {
			System.err.println("You'd go broke");
		}
	}
	
	/**
	 * Adds a specific amount of money to player's money
	 * @param amount
	 */
	public void gainMoney(int amount){
		this.money += amount;
	}
	
	//Returns First Name Only
	public String getPlayerName() {
		String name = playerName;
		Scanner scan = new Scanner(name);
		scan.useDelimiter(" ");
		return scan.next();
	}
	
	/**
	 * Returns the total number of houses on all of a player's properties
	 * @return
	 */
	public int getNumHouses(){
		int totalHouses = 0;
		for(Residential property : residentialProperties){
			totalHouses += property.getNumHouses();
		}
		return totalHouses;
	}
	
	/**
	 * Returns the total number of hotels on all of a player's properties
	 * @return
	 */
	public int getNumHotels(){
		int numHotels = 0;
		for(Residential property : residentialProperties){
			if(property.getNumHouses() == 5){
				numHotels++;
			}
		}
		return numHotels;
	}
}
