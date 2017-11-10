package application;

public abstract class ChanceCard extends Card{

	public ChanceCard(String cardFile) {
		super(cardFile);
	}
	
	@Override
	public void cardAction(){
		if (moveTo > -1){
			Game.moveTo(moveTo);
		}
		if (fine > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.fine(fine);
		}
		if (get > -1){
			Player currPlayer = Game.getCurrPlayer();
			currPlayer.gainMoney(get);
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
					Game.getCurrPlayer().setPos(10);
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
			}
		}
	}
}