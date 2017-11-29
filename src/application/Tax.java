package application;

import java.util.Scanner;

public class Tax extends Location{
	
	private int taxAmount;
	
	/**
	 * constructs spaces where a tax is imposed
	 */
	public Tax(String inputLine) {
		super(inputLine);
		
		Scanner lnScn = new Scanner(inputLine);
		lnScn.useDelimiter(",");
		lnScn.next();
		lnScn.next();
		lnScn.next();
		this.taxAmount = lnScn.nextInt();
		
		lnScn.close();
		
	}
	
	
	/**
	 * 
	 * @return taxAmount
	 */
	public int getTaxAmount() {
		return this.taxAmount;
	}
	
	
	
	
}
