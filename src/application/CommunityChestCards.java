package application;

public class CommunityChestCards extends Card{
	
	
	//final static String cardFile = "CommunityChestCards.txt";
	
	/**
	 * Creates CommunityChestCard object from String passed to it
	 * @param cardFile : line from card constructor file
	 */
	public CommunityChestCards(String cardFile) {
		super(cardFile);
		
	}
	
	
	/**
	 * Completes the action of the card based on the cards member variables.
	 */
	public static void cardAction() {
		if (moveTo > -1){
			Game.moveTo(moveTo);
			System.out.println("moveTo Card");
			Game.getController().setAlert(title);
		}
		if (fine > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney((-1)*fine);
			System.out.println("Fine Card");
			Game.getController().setAlert(title);
		}
		if (get > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney(get);
			System.out.println("Got Some Money Card");
			Game.getController().setAlert(title);
		}
		
		if (special){
			switch (specialNum){
				
				case 1:
					Game.getCurrPlayer().addGetOutOfJailFreeCard();						//Get out of jail free
					System.out.println("Get out of jail card");
					break;
				case 2:
					Game.goToJailSucker();												//Go to jail
					System.out.println("go to jail card");
					break;	
				case 3:
					Game.manageAllPlayersMoney(-50);									//Take $50 from each player
					System.out.println("take $50");
					break;
				case 4:
					Game.manageAllPlayersMoney(-10);									//Take $10 from each player
					System.out.println("take $10");
					break;
				case 5:
					int numHouses = Game.getCurrPlayer().getNumHouses();				//For each house/hotel pay...
					int numHotels = Game.getCurrPlayer().getNumHotels();
					Game.getCurrPlayer().changeMoney((-1)*115*numHotels - 40*numHouses);
					System.out.println("House thing");
					break;
					
			}
		}
		
	}
	
	
	
	

}
