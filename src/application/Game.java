package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game{
	final static String chanceCardFile = "ChanceCards.txt";
	final static String communityChestCardFile = "communityChestCards.txt";
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static int numPlayers;
	private static int currPlayer = 0;
	private static GameController gc;
	private static boolean canReroll = false;
	private static HashMap<Integer, Location> locations = new HashMap<Integer, Location>();
	
	
	
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
		for(String name : playerNames){
			Player thisPlayer = new Player(name);
			players.add(thisPlayer);
		}
		numPlayers = playerNames.size();
		System.out.println("\tLoading Properties");
		loadProperties();
		System.out.println("\tProperties Loaded"
				+ "\nLoading Cards");
		
		launchGUI();
	}
	
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
			scnr.close();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open property file");
			System.err.println("\t" + e.getMessage());
		}
		
	}
	
	/**
	 * Returns an int that is the number of players for the game
	 * @author adambailey
	 * @return
	 */
	public static int getNumPlayers(){
		return numPlayers;
	}
	
	public static Player getPlayer(int index){
		return players.get(index);
	}
	
	/**
	 * Returns current player.
	 * @return
	 */
	public static Player getCurrPlayer(){
		return players.get(currPlayer);
	}
	
	public static int getCurrPlayerNum(){
		return currPlayer;
	}

	/**
	 * Rolls dice, moves player, and handles all other actions related to a player's roll. 
	 * Calls methods accordingly
	 * Is triggered by player pressing the "Roll Dice" button
	 * @return
	 */
	public static void rollDice(){
		Random rand = new Random();
		int d1 = rand.nextInt(6)+1;
		int d2 = rand.nextInt(6)+1;
		if (d1 == d2){
			canReroll = true;
		} else {
			canReroll = false;
		}
		players.get(currPlayer).changePos(d1 + d2);
		System.out.println(Game.getCurrPlayer().getPos());
	}
	
	/**
	 * Is triggered by player selecting "Mortgage Properties" from the action menu.
	 * 
	 */
	public static void mortgageProperties(){
		//TODO: this
	}
	
	/**
	 * Moves current player to given position.
	 */
	public static void moveTo(int newPos){
		//TODO: This (for go to jail or cards that say
		// "go to nearest RR" or "Take a walk on Boardwalk" 
	}
	
	/**
	 * Sends player to jail and LOCKS 'EM UP
	 */
	public static void goToJailSucker(){
		players.get(currPlayer).changeIncarceration();
		players.get(currPlayer).setPos(10);
	}
	
	/**
	 * Use a negative amount if the current player is gaining money. 
	 * And a positive amount if the current player is losing money
	 * @param amount
	 */
	public static void manageAllPlayersMoney(int amount){
		for(int i=0; i<numPlayers; i++){
			if(i != currPlayer){
				players.get(i).changeMoney(amount);
			}
			players.get(currPlayer).changeMoney((-1)*amount*numPlayers);
		}
	}
	
	public static GameController getController(){
		return gc;
	}
	
	public static void endTurn(){
		currPlayer = (currPlayer + 1) % numPlayers;
		gc.nextPlayer();
	}
	
	public static boolean getCanReroll(){
		return canReroll;
	}
	
	/**
	 * Returns location based on position passed to it,
	 * 
	 * @param pos
	 * @return
	 */
	public static Location getLocation(int pos){
		return locations.get(pos);
	}
}