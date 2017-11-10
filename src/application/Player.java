package application;

import java.util.ArrayList;
import java.util.Scanner;

import Controller.GameController;

public class Player {
	private ArrayList<Residential> residentialProperties;
	private ArrayList<Utility> utilityProperties;
	private int money = 1500;	
	private int numGetOutOfJailCards = 0;
	private String playerName;
	private int pos = 0;
	private boolean isIncarcerated = false;
	
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
		switch (pos){
			case 0:
				GameController.moveToken(Game.getCurrPlayerNum(), x, y);
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
				break;
			case 12:
				break;
			case 13:
				break;
			case 14:
				break;
			case 15:
				break;
			case 16:
				break;
			case 17:
				break;
			case 18:
				break;
			case 19:
				break;
			case 20:
				break;
			case 21:
				break;
			case 22:
				break;
			case 23:
				break;
			case 24:
				break;
			case 25:
				break;
			case 26:
				break;
			case 27:
				break;
			case 28:
				break;
			case 29:
				break;
			case 30:
				break;
			case 31:
				break;
			case 32:
				break;
			case 33:
				break;
			case 34:
				break;
			case 35:
				break;
			case 36:
				break;
			case 37:
				break;
			case 38:
				break;
			case 39:
				break;
		}
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
	/*
	public void fine(int amount){
		if (amount > this.money){
			this.money -= amount;
		} else {
			//TODO: Prompt user to do something about this so they don't go broke
			System.err.println("You'd go broke");
		}
	}
	*/
	/**
	 * Adds a specific amount of money to player's money
	 * @param amount
	 */
	public void changeMoney(int amount){
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
	
	/**
	 * Returns whether or not the player is in jail
	 * @return
	 */
	public boolean isIncarcerated(){
		return this.isIncarcerated;
	}
	
	/**
	 * Changes the players status from in to out of jail
	 */
	public void changeIncarceration(){
		this.isIncarcerated = !this.isIncarcerated;
	}
}
