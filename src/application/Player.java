package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import Controller.GameController;

public class Player {
	private HashMap<Integer, ArrayList<Property>> playerProperties;
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
		this.playerProperties = new HashMap<>();
		for(int i=1; i<11; i++){
			this.playerProperties.put(i, new ArrayList<Property>());
		}
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
	public void changePos(int destination){
		int temp = this.pos;
		while(this.pos != destination) {
			this.pos++;
			this.pos = this.pos % 40;
			
			if (this.pos == 0) {
				this.changeMoney(200);
			}
		}
		//this.pos = (this.pos + destination) % 40;		//this is from when the method took a dice roll rather than a destination
		//this.pos = destination;
		switch (pos){
			case 0:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 426, 408);
				break;
			case 1:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 378, 408);
				break;
			case 2:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 342, 408);
				break;
			case 3:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 304, 408);
				break;
			case 4:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 268, 408);
				break;
			case 5:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 231, 408);
				break;
			case 6:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 193, 408);
				break;
			case 7:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 157, 408);
				break;
			case 8:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 120, 408);
				break;
			case 9:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 83, 408);
				break;
			case 10:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 422);
				break;
			case 11:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 360);
				break;
			case 12:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 323);
				break;
			case 13:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 286);
				break;
			case 14:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 249);
				break;
			case 15:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 212);
				break;
			case 16:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 176);
				break;
			case 17:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 139);
				break;
			case 18:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 103);
				break;
			case 19:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 66);
				break;
			case 20:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 35, 16);
				break;
			case 21:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 83, 6);
				break;
			case 22:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 120, 6);
				break;
			case 23:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 157, 6);
				break;
			case 24:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 193, 6);
				break;
			case 25:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 231, 6);
				break;
			case 26:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 268, 6);
				break;
			case 27:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 304, 6);
				break;
			case 28:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 342, 6);
				break;
			case 29:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 378, 6);
				break;
			case 30:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 426, 18);
				break;
			case 31:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 66);
				break;
			case 32:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 103);
				break;
			case 33:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 139);
				break;
			case 34:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 176);
				break;
			case 35:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 212);
				break;
			case 36:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 249);
				break;
			case 37:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 286);
				break;
			case 38:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 323);
				break;
			case 39:
				Game.getController().moveToken(Game.getCurrPlayerNum(), 436, 360);
				break;
		}
	}
	
	/**
	 * Sets position to given location
	 * @param num
	 */
	public void setPos(int num){
		this.pos = num;
		Game.getController().moveToken(Game.getCurrPlayerNum(), 25, 422); 
		//Set X-Y Coord. of NOT just visiting.
		
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
	public void changeMoney(double amount){
		System.out.println("\tChange Money: " + amount);
		this.money += amount;
		System.out.println("\t" + this.money);
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
			
		for(int i = 1; i <= 8; i++) {
			for(int j = 0; j < playerProperties.get(i).size(); j++) {
				Residential resProperty = (Residential) playerProperties.get(i).get(j);
				if(resProperty.getNumHouses() < 5) {
					totalHouses += resProperty.getNumHouses();
				}
			}

		}
		return totalHouses;
	}
	
	/**
	 * Returns the total number of hotels on all of a player's properties
	 * @return
	 */
	public int getNumHotels(){
		int totalHotels = 0;		
		for(int i = 1; i <= 8; i++) {
			for(int j = 0; j < playerProperties.get(i).size(); j++) {
				Residential resProperty = (Residential) playerProperties.get(i).get(j);
				if(resProperty.getNumHouses() == 5) {
					totalHotels += 1;
				}
			}

		}
		return totalHotels;
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
	
	/**
	 * Adds properties 
	 */
	public void addProperty(Property prop){
		if(prop.getPropertyType().equals("Utility")){
			String name = prop.getPropertyName();
			Scanner nameScn = new Scanner(name);
			name = nameScn.next();
			if(name.equals("Electric") || name.equals("Water")){
				playerProperties.get(10).add(prop);
			} else {
				playerProperties.get(9).add(prop);
			}
			nameScn.close();
		} else {
			Residential resProp = (Residential) prop;
			playerProperties.get(resProp.getNumSet()).add(resProp);
		}
	}
	
	public void removeProperty(){
		//TODO;
	}
	
	public HashMap<Integer, ArrayList<Property>> getProperties(){
		return this.playerProperties;
	}
}
