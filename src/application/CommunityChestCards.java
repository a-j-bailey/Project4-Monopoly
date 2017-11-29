package application;

public class CommunityChestCards extends Card{
	
	
	final static String cardFile = "CommunityChestCards.txt";

	public CommunityChestCards(String cardFile) {
		super(cardFile);
		
	}
	
	@Override
	public void cardAction() {
		if (moveTo > -1){
			Game.moveTo(moveTo);
		}
		if (fine > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney((-1)*fine);
		}
		if (get > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.changeMoney(get);
		}
		
		if (special){
			switch (specialNum){
				
				case 1:
					Game.getCurrPlayer().addGetOutOfJailFreeCard();						//Get out of jail free
					break;
				case 2:
					Game.goToJailSucker();												//Go to jail
					break;	
				case 3:
					Game.manageAllPlayersMoney(-50);									//Take $50 from each player
					break;
				case 4:
					Game.manageAllPlayersMoney(-10);									//Take $10 from each player
					break;
				case 5:
					int numHouses = Game.getCurrPlayer().getNumHouses();				//For each house/hotel pay...
					int numHotels = Game.getCurrPlayer().getNumHotels();
					Game.getCurrPlayer().changeMoney((-1)*115*numHotels - 40*numHouses);
					break;
					
			}
		}
		
	}
	
	
	
	

}
