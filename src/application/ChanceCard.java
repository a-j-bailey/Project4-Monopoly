package application;

public abstract class ChanceCard extends Card{
	
	final static String cardFile = "ChanceCards.txt";
	
	public ChanceCard() {
		super(cardFile);
	}
	
	@Override
	public void cardAction(){
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
					//move player to nearest RR
					//RRs @: 5, 15, 25, 35
					break;
				case 2:
					Game.getCurrPlayer().addGetOutOfJailFreeCard();
					break;
				case 3:
					Game.getCurrPlayer().changePos(-3);
					break;
				case 4:
					Game.goToJailSucker();
					break;
				case 5:
					int numHouses = Game.getCurrPlayer().getNumHouses();
					int numHotels = Game.getCurrPlayer().getNumHotels();
					Game.getCurrPlayer().changeMoney((-1)*100*numHotels - 25*numHouses);
					break;
				case 6:
					Game.getCurrPlayer().setPos(5);
					break;
				case 7:
					Game.manageAllPlayersMoney(50);
					break;
			}
		}
	}
}