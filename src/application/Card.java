package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Card {
	protected String title;
	protected int moveTo;
	protected int fine;
	protected int get;
	protected boolean special;
	protected int specialNum;
	
	
	/**
	 * 
	 * @param cardFile
	 * initializes all the cards in community chest and chance
	 */
	public Card(String cardFile){
		try {
			File inputFile = new File(cardFile);
			Scanner scnr = new Scanner(inputFile);
			while(scnr.hasNextLine()){
				this.title = scnr.next();
				this.moveTo = scnr.nextInt();
				this.fine = scnr.nextInt();
				this.get = scnr.nextInt();
				String isSpecial = scnr.next();
				if (isSpecial.equals("TRUE")){
					this.special = true;
					this.specialNum = scnr.nextInt();
				} else {
					this.special = false;
				}
			}
			scnr.close();
		} catch (FileNotFoundException e) {
			System.err.println("Couldnt load card file");
		}
		
	}
	
	abstract void cardAction();
	
	// We need this. Just havent decided if it should be inherited, or abstract.
	// Depends on how we store cards
	//public void drawCard()
}
