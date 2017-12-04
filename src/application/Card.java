package application;

import java.util.Scanner;

public class Card {
	private String title;
	private int moveTo;
	private int fine;
	private int get;
	private boolean special;
	private int specialNum;
	
	
	/*
	 * This doesn't work. It needs to take in one line at a time and make one card at a time
	 * then call the method in game that adds it to the chance or community chest desk (stack)
	 * So we can have an actual deck. 
	 */
	
	
	/**
	 * Builds cards
	 * @param cardFile : name of file to read cards from.
	 */
	public Card(String cardLine){
		Scanner scnr = new Scanner(cardLine);
		scnr.useDelimiter(",");
		this.title =scnr.next();
		this.moveTo =scnr.nextInt();
		this.fine = scnr.nextInt();
		this.get = scnr.nextInt();
		String isSpecial = scnr.next();
		if (isSpecial.equals("TRUE")){
			this.special = true;
			this.specialNum =scnr.nextInt();
		} else {
			this.special = false;
		}

		scnr.close();
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getMoveTo() {
		return moveTo;
	}


	public void setMoveTo(int moveTo) {
		this.moveTo = moveTo;
	}


	public int getFine() {
		return fine;
	}


	public void setFine(int fine) {
		this.fine = fine;
	}


	public int getGet() {
		return get;
	}


	public void setGet(int get) {
		this.get = get;
	}


	public boolean isSpecial() {
		return special;
	}


	public void setSpecial(boolean special) {
		this.special = special;
	}


	public int getSpecialNum() {
		return specialNum;
	}


	public void setSpecialNum(int specialNum) {
		this.specialNum = specialNum;
	}

	//abstract public static void cardAction();
	
	// We need this. Just havent decided if it should be inherited, or abstract.
	// Depends on how we store cards
	//public void drawCard()
}
