package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game{
	final static String chanceCardFile = "ChanceCards.txt";
	final static String communityChestCardFile = "communityChestCards.txt";
	private static Stack<CommunityChestCards> communityChest = new Stack<CommunityChestCards>();
	private static Stack<ChanceCard> chance = new Stack<ChanceCard>();
	private static HashMap<Integer, Player> players = new HashMap<>();
	private static int numPlayers;
	private static int currPlayer = 0;
	private static GameController gc;
	private static boolean canReroll = false;
	private static HashMap<Integer, Location> locations = new HashMap<Integer, Location>();
	private static int[] currentDice = new int[2];
	private static int numOfRolls;
	private static int[] actionSpotLocations = new int[] {2, 4, 7, 17, 22, 33, 36, 38};
	private static Boolean gameOver = false;
	
	
	/**
	 * Launches the GUI window for the Monopoly game
	 */
	public void launchGUI(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MonopolyGUI.fxml"));
			Parent root;
            root = fxmlLoader.load();
            gc = fxmlLoader.getController();
            System.out.println("\tgc = " + gc);
            Stage stage = new Stage();
            stage.setTitle("MONOPOLY");
            stage.setScene(new Scene(root, 900, 640));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Accepts an arrayList of names for players.
	 * Iterates through this list creating new instances of Player
	 * and adding them to the game.
	 * 
	 * @author adambailey
	 * @param playerNames
	 */
	public Game(ArrayList<String> playerNames){	
		
		for(int i=0; i<playerNames.size(); i++){
			Player thisPlayer = new Player(playerNames.get(i));
			thisPlayer.setPNum(i);
			players.put(i, thisPlayer);
		}
		
		//load Those Cards Though
		//ChanceCard(chanceCardFile);
		
		numPlayers = playerNames.size();
		System.out.println("\tLoading Properties");
		loadProperties();
		System.out.println("\tProperties Loaded"
				+ "\nLoading Cards");
		
		launchGUI();
	}
	
	
	/**
	 * Initializes Locations from "Properties.txt" and adds them to a HashMap
	 */
	public static void loadProperties(){
		try{
			File propertyFile = new File("Properties.txt");
			Scanner scnr = new Scanner(propertyFile);
			int i = 0;
			while (scnr.hasNextLine()){
				System.out.print("\t" + i);
				String nextLine = scnr.nextLine();
				Scanner lnScn = new Scanner(nextLine);
				lnScn.useDelimiter(",");
				String id = lnScn.next();
				if (id.equals("Residential")){
					Residential property = new Residential(nextLine);
					locations.put(i, property);
				} else if (id.equals("Tax")){
					Tax tax = new Tax(nextLine);
					locations.put(i,  tax);
				} else if (id.equals("Utility")){
					Utility utility = new Utility(nextLine);
					locations.put(i, utility);
				} else if (id.equals("cardLocation")){
					cardLocation cardLocation = new cardLocation(nextLine);
					locations.put(i, cardLocation);
				} else if (id.equals("Location")){
					Location location = new Location(nextLine);
					locations.put(i, location);
				}
				lnScn.close();
				i++;
			}
			Location jail = new Location("Location,In Jail,#000000");
			locations.put(50, jail);
			scnr.close();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open property file");
			System.err.println("\t" + e.getMessage());
		}
		
	}
	
	
	
	/**
	 * Adds the Chance card passed to it to the game's deck of Chance Cards
	 * @param card : Chance Card
	 */
	public static void addToChanceDeck(ChanceCard card) {
		chance.push(card);
	}
	
	/**
	 * Adds the Community Chest card passed to it to the game's deck of Chance Cards
	 * @param card : Community Chest Card
	 */
	public static void addToCommunityChestDeck(CommunityChestCards card) {
		communityChest.push(card);
	}
	
	
	
	/**
	 * Returns an int that is the number of players the game started with
	 * (Does not change after players are removed from the game)
	 * @author adambailey
	 * @return
	 */
	public static int getNumPlayers(){
		return numPlayers;
	}
	
	/**
	 * Returns player at index i.
	 * @param index
	 * @return the player with respect to an index
	 */
	public static Player getPlayer(int index){
		return players.get(index);
	}
	
	/**
	 * Returns the player object for the player who's turn it currently is.
	 * @return
	 */
	public static Player getCurrPlayer(){
		return getPlayer(currPlayer);
	}
	
	/**
	 * Returns the number of the current player.
	 * i.e 1 - 6 (or numPlayers)
	 * NOT INDEX
	 * @return int for the number of the current player
	 */
	public static int getCurrPlayerNum(){
		System.out.println("Current Player Num: " + currPlayer);
		return getPlayer(currPlayer).getPNum() + 1;
	}

	/**
	 * Rolls dice, moves player, and handles all other actions related to a player's roll. 
	 * Calls methods accordingly
	 * Is triggered by player pressing the "Roll Dice" button
	 * Saves dice roll to currentDice array.
	 * @return
	 */
	public static void rollDice(){
		if (!Game.getCurrPlayer().isIncarcerated()) {


			numOfRolls++;

			Random rand = new Random();
			//int d1 = rand.nextInt(6)+1;
			//int d2 = rand.nextInt(6)+1;
			
			//FOR TROUBLESHOOTING
			//System.out.println("-- enter dice roll: --");
			//Scanner input = new Scanner(System.in);
			int d1 = 1; //input.nextInt();
			int d2 = 0; //input.nextInt();
			
			System.out.println("\tDICE: " + d1 + " " + d2);

			if (d1 == d2){
				canReroll = true;
			} 
			else {
				canReroll = false;
			}
			if (numOfRolls == 3) {
				canReroll = false;
				goToJailSucker();
			}
			else {
				players.get(currPlayer).changePos((players.get(currPlayer).getPos() + d1 + d2) % 40);
				System.out.println(Game.getCurrPlayer().getPos());
				currentDice[0] = d1;
				currentDice[1] = d2;
				if (Game.getCurrPlayer().getPos() == 30) {
					canReroll = false;
					goToJailSucker();
				}
				
				
				else {
					for  (int i = 0; i < 8; i++) {
						if (Game.getCurrPlayer().getPos() == actionSpotLocations[i]) {
							//actionSpot(Game.getCurrPlayer().getPos());
						}
					}
				}
				
			}
		}
		else {

			if (Game.getCurrPlayer().getNumRollsInJail() == 3) {
				Game.getCurrPlayer().changeMoney(-50);
				canReroll = true;
				getOutOfJailSucker();
				
			}

			else {

				Random rand = new Random();
				int d1 = rand.nextInt(6)+1;
				int d2 = rand.nextInt(6)+1;

				//FOR TROUBLESHOOTING
				//System.out.println("-- enter dice roll: --");
				//Scanner input = new Scanner(System.in);
				//int d1 = input.nextInt();
				//int d2 = input.nextInt();
				
				Game.getCurrPlayer().addNumRollsInJail();
				
				canReroll = false;


				if (d1 == d2){
					currentDice[0] = d1;
					currentDice[1] = d2;
					getOutOfJailSucker();

				} 
				else {
					endTurn();
				}
			}
		}

	}
	
	/**
	 * Receives the current players location and checks to see if they should 
	 * be taxed or draw a card based on their location.
	 * @param location : index of location
	 */
	public static void actionSpot(int location) {
		
		if (location == 2 || location == 17 || location == 33) {		//Community Chest
			CommunityChestCards.cardAction();
		}
		if (location == 7 || location == 22 || location == 36) {		//Chance
			ChanceCard.cardAction();
		}
		if (location == 4 || location == 38) {							//Tax
			Game.getCurrPlayer().changeMoney(((Tax) locations.get(location)).getTaxAmount());		
		}
		
		
	}
	
	
	
	
	
	/**
	 * Cycles through the properties that were mortgaged/unMortgaged in the popup. 
	 * Changes their isMortgaged Boolean and adds or subtracts from the player's money.
	 * @param propertiesToDo
	 */
	public static void mortgageProperties(Stack<Integer> propertiesToDo){

		while(!propertiesToDo.isEmpty()) {
			if(((Property) locations.get(propertiesToDo.peek())).isMortgaged()) {  //Look, I have no idea what the (Property) thing is at the bigging of this if statement. It fixed my problems though
				((Property) locations.get(propertiesToDo.peek())).setIsMortgaged(false);		//I'm pretty sure its specifying the location type
				int temp = ((Property) locations.get(propertiesToDo.pop())).getMortgageValue();
				getCurrPlayer().changeMoney((-1)*temp);


			}
			else if(!((Property) locations.get(propertiesToDo.peek())).isMortgaged()) {  
				((Property) locations.get(propertiesToDo.peek())).setIsMortgaged(true);	
				int temp = ((Property) locations.get(propertiesToDo.pop())).getMortgageValue();
				getCurrPlayer().changeMoney(temp);


			}
		}



	}

	/**
	 * Moves current player to given position.
	 */
	//public static void moveTo(int newPos){			This is now in Player.java
	//	currPlayer.changePos(newPos);
	//}
	
	/**
	 * Sends current player to jail and LOCKS 'EM UP
	 */
	public static void goToJailSucker(){
		players.get(currPlayer).changeIncarceration(true);
		players.get(currPlayer).setPos(50);
		canReroll = false;										//Instead of this method ending their turn, We should let them hit the button and let them know theyre in jail
		gc.setAlert("You're in jail.\nRoll doubles to get out of jail.");
	}
	
	/**
	 * Sets player into the "visiting jail" position, 
	 * If they rolled doubles, it lets them roll again and move.
	 */
	public static void getOutOfJailSucker() {
		//print something about how you're out of jail
		players.get(currPlayer).changeIncarceration(false);
		players.get(currPlayer).setPos(10);
		
		if (currentDice[0] == currentDice[1]) {
			players.get(currPlayer).changePos((10 + currentDice[0] + currentDice[1]) % 40);
			gc.setAlert("You've made it out of jail! #FreeBird");
		}
		else {
			gc.setAlert("You made it out of jail!");//Just Tell Them They are out of jail and give them the option to end their turn				//HERE HERE HERE HERE
		}
	}
	
	/**
	 * Use a negative amount if the current player is gaining money. 
	 * Use a positive amount if the current player is losing money.
	 * @param amount : amount that the other player's money should change by.
	 */
	public static void manageAllPlayersMoney(int amount){
		for(int i=0; i<numPlayers; i++){
			if(i != currPlayer){
				players.get(i).changeMoney(amount);
			}
			players.get(currPlayer).changeMoney((-1)*amount*numPlayers);
		}
	}
	
	/**
	 * 
	 * @return : instance of GameController
	 */
	public static GameController getController(){
		return gc;
	}
	
	/**
	 * Removes the current player from the game. 
	 * Checks to see if there is only one player left in the game. 
	 * If so, declares the last player as the winner.
	 */
	public static void removePlayer(){
		System.err.println("--REMOVE PLAYER--\n" + getCurrPlayer().getPlayerName());
		HashMap<Integer, ArrayList<Property>> playerProperties = getCurrPlayer().getProperties();
		for(int i=1; i<10; i++){
			for(int j=0; j<playerProperties.get(i).size(); j++){
				if(playerProperties.get(i).get(j).getPropertyType().equals("Residential")){
					Residential resProp = (Residential) playerProperties.get(i).get(j);
					resProp.buildHouse(0);
				}
				playerProperties.get(i).get(j).changeOwner(0);
			}
		}
		gc.removePlayerPanel(getCurrPlayer().getPNum());
		players.remove(getCurrPlayer().getPNum());
		
		if(players.size() == 1){
			for(int i=0; i<numPlayers; i++){
				if(players.containsKey(i)){
					gameOver = true;
					gc.broadcastWinner(getPlayer(i).getPlayerName());
				}
			}
		}
	}
	
	/**
	 * Ends the current player's turn. 
	 * If they have less than $0, removes them from the game. 
	 * Otherwise, if the game isnt over, it que's up the next player 
	 * and increments the currPlayer integer.
	 */
	public static void endTurn(){
		if(getCurrPlayer().getMoney() < 0){
			removePlayer();
		}
		
		if(!gameOver){
			numOfRolls = 0;
			do{
				currPlayer = (currPlayer + 1) % numPlayers;
			} while(!players.containsKey(currPlayer));
			System.out.println("\tCurr Player " + currPlayer);
			gc.nextPlayer();
			gc.clearAlert();
		}
	}
	
	/**
	 * @return boolean for whether the current player can re-roll the dice.
	 */
	public static boolean getCanReroll(){
		return canReroll;
	}
	
	/**
	 * Returns location based on position passed to it,
	 * @param pos
	 * @return
	 */
	public static Location getLocation(int pos){
		return locations.get(pos);
	}
	
	/**
	 * Returns the int array of the two dice values.
	 * @return
	 */
	public static int[] getCurrentDice() {
		return currentDice;
	}
	
	/**
	 * Moves player to the specified destination
	 * @param moveTo : index of new position.
	 */
	public static void moveTo(int moveTo) {
		players.get(currPlayer).changePos(moveTo);
		
	}

	/**
	 * @return : HashMap<Integer, Player> of game players.
	 */
	public static HashMap<Integer, Player> getPlayers(){
		return players;
	}
}