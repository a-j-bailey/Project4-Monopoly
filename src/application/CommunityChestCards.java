package application;

public class CommunityChestCards extends Card{
	
	
	//final static String cardFile = "CommunityChestCards.txt";

	public CommunityChestCards(String cardFile) {
		super(cardFile);
		
	}
	
	
	/**
	 * Does the specified action of a card 
	 */
	public static void cardAction() {
		if (moveTo > -1){
			Game.moveTo(moveTo);
			System.out.println("moveTo Card");
		}
		if (fine > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney((-1)*fine);
			System.out.println("Fine Card");
		}
		if (get > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney(get);
			System.out.println("Got Some Money Card");
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
