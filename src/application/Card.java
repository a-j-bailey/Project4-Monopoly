package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Card {
	protected static String title;
	protected static int moveTo;
	protected static int fine;
	protected static int get;
	protected static boolean special;
	protected static int specialNum;
	
	
	/*
	 * This doesn't work. It needs to take in one line at a time and make one card at a time
	 * then call the method in game that adds it to the chance or community chest desk (stack)
	 * So we can have an actual deck. 
	 */
	
	
	/**
	 * 
	 * @param cardFile
	 * initializes all the cards in community chest and chance
	 */
	public Card(String cardLine){


		Scanner scnr = new Scanner(cardLine);

		this.title = scnr.next();
		Card.moveTo = scnr.nextInt();
		this.fine = scnr.nextInt();
		this.get = scnr.nextInt();
		String isSpecial = scnr.next();
		if (isSpecial.equals("TRUE")){
			this.special = true;
			this.specialNum = scnr.nextInt();
		} else {
			this.special = false;
		}

		scnr.close();


	}

	//abstract public static void cardAction();
	
	// We need this. Just havent decided if it should be inherited, or abstract.
	// Depends on how we store cards
	//public void drawCard()
}
