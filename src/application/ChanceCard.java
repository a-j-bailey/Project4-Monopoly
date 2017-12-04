package application;

public abstract class ChanceCard extends Card{
	
	//final static String cardFile = "ChanceCards.txt";
	
	public ChanceCard(String cardFile) {
		super(cardFile);
	}
	
	
	/**
	 * does the specified action of a card
	 */
	public static void cardAction(){
		if (moveTo > -1){
			Game.moveTo(moveTo);
			System.out.println("move to card");
		}
		if (fine > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney((-1)*fine);
			System.out.println("Get fined idiot");
		}
		if (get > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney(get);
			System.out.println("Get Money, jerk");
		}
		if (special){
			switch (specialNum){
				case 1: 
					//move player to nearest RR
					//RRs @: 5, 15, 25, 35
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