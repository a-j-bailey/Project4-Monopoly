package application;

public class cardLocation extends Location{
	
	private String title;
	
	/**
	 * Builds cardLocation Location from the String passed to it
	 * @param inputLine : line from property text file
	 */
	public cardLocation(String inputLine){
		super(inputLine);
	}
}
