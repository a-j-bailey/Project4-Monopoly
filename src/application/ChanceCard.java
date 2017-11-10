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
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
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