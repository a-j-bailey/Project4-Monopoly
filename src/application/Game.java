package application;

import java.io.IOException;

import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	private static int numPlayers;
	
	public void launchGUI(){
		Parent root;
		try {
            root = FXMLLoader.load(getClass().getResource("/MonopolyGUI.fxml"));
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
	
	/**
	 * Returns an int that is the number of players for the game
	 * @author adambailey
	 * @return
	 */
	public static int getNumPlayers(){
		return numPlayers;
	}
	
	/**
	 * Returns name of player at index
	 * @author adambailey
	 * @param index
	 * @return
	 */
	public static String getPlayerName(int index){
		return players.get(index).getPlayerName();
	}
	
	/**
	 * Returns the amount of money player at index __ has.
	 * 
	 * @param index
	 * @return
	 */
	public static int getPlayerMoney(int index){
		return players.get(index).getMoney();
	}
	
	/**
	 * Rolls dice, moves player, and handles all other actions related to a player's roll. 
	 * Calls methods accordingly
	 * Is triggered by player pressing the "Roll Dice" button
	 * @return
	 */
	public static void rollDice(){
		//TODO: this... duh
	}
	
	/**
	 * Is triggered by player selecting "Mortgage Properties" from the action menu.
	 * 
	 */
	public static void mortgageProperties(){
		
	}
}