package application;

import java.util.Scanner;

public class Player {
	private int money = 1500;
	
	//private int playerNum;
	
	private int numGetOutOfJailCards;
	
	private String playerName;
	
	
	public Player(String name){
		this.playerName = name;
	}
	
	public int getMoney(){
		return this.money;
	}

	public int getGetOutOfJailFreeCards(){
		return this.numGetOutOfJailCards;
	}
	
	public void fine(int amount){
		if (amount > this.money){
			this.money -= amount;
		} else {
			System.err.println("You'd go broke");
		}
	}
	
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
	
	
}
