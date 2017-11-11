package application;

public abstract class Tax extends Location{
	
	private int taxAmount;
	
	/**
	 * constructs spaces where a tax is imposed
	 */
	public void Tax(String name, String color, Int location, int taxAmount) {
		super(location, color, name);
		this.taxAmount = taxAmount;
	}
	/**
	 * taxes player
	 */
	public void taxPlayer() {
		//subtract tax amount from player's cash. 
	}
	
}
