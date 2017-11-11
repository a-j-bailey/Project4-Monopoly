package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game{
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static int numPlayers;
	private static int currPlayer = 0;
	private static GameController gc;
	private static boolean canReroll = false;
	private static ArrayList<Location> locations;
	
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
		
		launchGUI();
	}
	
	public static void loadProperties(){
		try{
			File propertyFile = new File("/Properties.txt");
			Scanner scnr = new Scanner(propertyFile);
			
			while (scnr.hasNextLine()){
				String nextLine = scnr.nextLine();
				Scanner lnScn = new Scanner(nextLine);
				String id = lnScn.next();
				if (id.equals("Residential")){
					Residential property = new Residential(nextLine);
					locations.add(property);
				} else if (id.equals("Tax")){
					Tax tax = new Tax(nextLine);
					locations.add(tax);
				} else if (id.equals("Utility")){
					Utility utility = new Utility(nextLine);
					locations.add(utility);
				} else if (id.equals("CardLocation")){
					cardLocation cardLocation = new cardLocation(nextLine);
					locations.add(cardLocation);
				} else if (id.equals("Location")){
					Location location = new Location(nextLine);
					locations.add(location);
				}
				
			}
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open property file");
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
		System.out.println("\tRoll Dice:\tD1: " + d1 + " D2: " + d2);
		players.get(currPlayer).changePos(d1 + d2);
	}
	
	/**
	 * Is triggered by player selecting "Mortgage Properties" from the action menu.
	 * 
	 */
	public static void mortgageProperties(){
		
	}
	
	/**
	 * Moves current player to given position.
	 */
	public static void moveTo(int newPos){
		
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
}