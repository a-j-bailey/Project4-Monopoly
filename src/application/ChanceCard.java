package application;

public class ChanceCard extends Card{
	
	//final static String cardFile = "ChanceCards.txt";
	
	/**
	 * Constructs ChanceCard object from the string that is passed to it.
	 * @param cardFile : line from card constructor file.
	 */
	public ChanceCard(String cardFile) {
		super(cardFile);
	}
	
	
	/**
	 * Completes the action of the card based on the cards member variables.
	 */
	public void cardAction(){									//if the card is a "move to" card
		if (getMoveTo() > -1){
			Game.moveTo(getMoveTo());
			System.out.println("move to card");
		}
		if (getFine() > -1){									//If the card is a fine
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney((-1)*getFine());
			System.out.println("Get fined idiot");
		}
		if (getGet() > -1){										//If the card pays the player money
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney(getGet());
			System.out.println("Get Money, jerk");
		}
		if (isSpecial()){
			switch (getSpecialNum()){
				case 1: 
					System.out.println("nearest RR Card");
					//move player to nearest RR
					//RRs @: 5, 15, 25, 35
					if(Game.getCurrPlayer().getPos() < 5) {				//if the player is before the first RR
						Game.getCurrPlayer().changePos(5);
					}
					else if(Game.getCurrPlayer().getPos() < 15) {		//^^ but second RR
						Game.getCurrPlayer().changePos(15);
					}
					else if(Game.getCurrPlayer().getPos() < 25) {		//Third RR
						Game.getCurrPlayer().changePos(25);
					}
					else if(Game.getCurrPlayer().getPos() < 35) {		//Fourth RR
						Game.getCurrPlayer().changePos(35);
					}
					else if(Game.getCurrPlayer().getPos() >= 35) {		//If player is after the fourth RR but not passed go yet
						Game.getCurrPlayer().changePos(5);
					}										
					break;
				case 2:
					Game.getCurrPlayer().addGetOutOfJailFreeCard();						//Get out of jail free
					System.out.println("get out of jail free");
					break;
				case 3:
					Game.getCurrPlayer().changePos(-3);									//Go back three spaces
					System.out.println("go back three spaces");
					break;
				case 4:
					Game.goToJailSucker();												//Go to jail
					System.out.println("Go to jail");
					break;	
				case 5:
					int numHouses = Game.getCurrPlayer().getNumHouses();				//For each house/hotel pay...
					int numHotels = Game.getCurrPlayer().getNumHotels();
					Game.getCurrPlayer().changeMoney((-1)*100*numHotels - 25*numHouses);
					System.out.println("House hotel thing");
					break;
				case 6:
					Game.getCurrPlayer().setPos(5);										//report to reading railroad
					System.out.println("Reading Railroad. Go there");
					break;
				case 7:
					Game.manageAllPlayersMoney(50);										//Pay each player $50
					System.out.println("pay each player $50");
					break;
			}
		}
	}
}