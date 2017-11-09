package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Card {
	
	//TODO: Figure out how to represent deck of cards.
	//      Should each card be a seperate object with a method that handles the action of that card?
	
	protected String title;
	protected int moveTo;
	protected int fine;
	protected int get;
	protected boolean special;
	protected int specialNum;
	
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
