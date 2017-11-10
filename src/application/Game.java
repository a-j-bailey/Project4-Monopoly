package application;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game {
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	private static int numPlayers;
	
	private static int currPlayer = 0;
	
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
	 * Returns name of player at index
	 * @author adambailey
	 * @param index
	 * @return
	 */
	/*
	public static String getPlayerName(int index){
		return players.get(index).getPlayerName();
	}
	*/
	/**
	 * Returns the amount of money player at index __ has.
	 * 
	 * @param index
	 * @return
	 */
	/*
	public static int getPlayerMoney(int index){
		return players.get(index).getMoney();
	}
	*/
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
}