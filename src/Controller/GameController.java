package Controller;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import application.Game;
import application.Location;
import application.Property;
import application.Residential;
import application.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class GameController implements Initializable{
	
	@FXML
	private AnchorPane boardGame;
	@FXML
	private Accordion ppAccordion;
	
	private HashMap<Integer, ImageView> playerTokens;
	private HashMap<Integer, TitledPane> playerPanes;
	private HashMap<Integer, Label> playerMoneyLabels;
	private HashMap<Integer, TextFlow> playerPropertyLists;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		System.out.println("\tLoading...");
		int playerNum = Game.getNumPlayers();
		System.out.println("\tNum Players: " + playerNum);
		
		playerTokens = new HashMap<>();
		
		//Loads token image for each player
		// and adds them to the playerTokens HashMap
		for(int i=playerNum -1; i >= 0; i--){
			ImageView token = new ImageView();
			Image image = new Image("imgs/player" + (i+1) + "_token.png");
			token.setImage(image);
			token.setX(426);
			token.setY(408);
			token.setFitWidth(40);
			token.setFitHeight(40);
			token.toFront();
			boardGame.getChildren().add(token);
			playerTokens.put(i, token);
		}
		
		//Initializes HashMaps for other elements
		playerPanes = new HashMap<>();
		playerMoneyLabels = new HashMap<>();
		playerPropertyLists = new HashMap<>();
		
		//Loads other elements for players.
		for (int i=1; i<=playerNum; i++){
			//Token
			ImageView token = new ImageView();
			token.setImage(new Image("imgs/player" + i + "_token.png"));
			token.setFitWidth(50);
			token.setFitHeight(50);
			//Label (Money)
			Label money = new Label(" $" + Game.getPlayer(i).getMoney());
			playerMoneyLabels.put(i, money);
			//Properties Title
			Label properties = new Label(" Properties:");
			//PropertiesTextArea
			TextFlow propText = new TextFlow();
			propText.setPrefWidth(150);
			propText.setPrefHeight(280);
			playerPropertyLists.put(i, propText);
			ScrollPane sP = new ScrollPane(propText);
			sP.setPrefWidth(200);
			
			//Add All to VBOX
			VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.getChildren().add(token);
			vbox.getChildren().add(money);
			vbox.getChildren().add(properties);
			vbox.getChildren().add(sP);
			
			//Add VBOX to pane
			TitledPane pane = new TitledPane(Game.getPlayer(i).getPlayerName(), vbox);
			playerPanes.put(i, pane);
			ppAccordion.getPanes().add(pane);
		}
		
		//Sets the label for whos turn it currently is
		this.currentTurn.setText(Game.getPlayer(1).getPlayerName() + "'s Turn");
		updatePropertyInfo();
		
		//Loads rule page from rule file
		loadRules();
		
		System.out.println(" -- Game Initialized -- ");
	}
	
	/**
	 * Accepts player number and (x,y) coordinates and moves player to correct position
	 * @param player
	 * @param x
	 * @param y
	 */
	public void moveToken(int player, int x, int y){
		player--;
		//Sets player's token to the X Y coordinates
		playerTokens.get(player).setX(x);
		playerTokens.get(player).setY(y);
		
		//Hides dice button 
		if (!Game.getCanReroll()){
			rollDiceButton.setDisable(true);
			endTurnButton.setDisable(false);
		}
		
		//shows buttons for trading and managing properties
		manageProperties.setDisable(false);
		tradeButton.setDisable(false);
		updatePropertyInfo();
	}
	
	/**
	 * Sets up gui for next player
	 */
	public void nextPlayer(){
		rollDiceButton.setDisable(false);
		manageProperties.setDisable(true);
		tradeButton.setDisable(true);
		endTurnButton.setDisable(true);
		buyProperty.setOpacity(0);
		buyProperty.setDisable(true);
		
		if(Game.getCurrPlayer().isIncarcerated() && (Game.getCurrPlayer().getGetOutOfJailFreeCards() > 0)){
			useGetOutOfJailCard.setVisible(true);
			useGetOutOfJailCard.setDisable(false);
		}
		
		this.currentTurn.setText(Game.getCurrPlayer().getPlayerName() + "'s Turn");
	}
	
	/**
	 * Updates information for right panel based on current property.
	 */
	public void updatePropertyInfo(){
		Location thisLocation = Game.getLocation(Game.getCurrPlayer().getPos());
		//Sets pane title based on current player
		this.propertyInfo.setText(thisLocation.getPropertyName() + ":");
		
		System.out.println("\tCurrent Property: " + thisLocation.getPropertyName());
		
		//Populates Property Info table with information:
		if(thisLocation.getPropertyType().equals("Residential") ||
				thisLocation.getPropertyType().equals("Utility")){
			//Creates property instance of location
			Property property = (Property) thisLocation;
			if(property.isBought()){
				//hides buy button
				this.buyProperty.setOpacity(0);
				this.buyProperty.setDisable(true);
				int rent = 0;
				if(property.getPropertyType().equals("Utility")){
					//If it's a utility, creates Utility instance
					Utility util = (Utility) property;
					String name = util.getPropertyName();
					Scanner nameScn = new Scanner(name);
					//Get's first part of name so we know whether it's Water Works or Electric Company
					if (nameScn.next().equals("Water") || nameScn.next().equals("Electric")){
						//Calculates the number of utilities the player owns
						int numOwned = Game.getPlayer(util.getOwner()).getProperties().get(10).size();
						//Gets dice roll
						int dice = Game.getCurrentDice()[0] + Game.getCurrentDice()[1];
						//Calculates rent
						rent = util.calcUtilityRent(dice, numOwned);
					} else {
						//RAILROAD
						//Gets number of railroads the player owns
						int numOwned = Game.getPlayer(util.getOwner()).getProperties().get(9).size();
						//Calculates rent
						rent = util.calcRailroadRent(numOwned);
					}
					nameScn.close();
				} else {
					//Not utility, but property, therefore it's a Residential
					Residential prop = (Residential) property;
					rent = prop.getRent();
				}
				if(Game.getCurrPlayer() != Game.getPlayer(property.getOwner())){
					this.propertyInfoText.setText("This property is owned by " + 
							Game.getPlayer(property.getOwner()).getPlayerName() + 
							"\nYou paid " + rent + " in rent.");
				} else {
					this.propertyInfoText.setText("You own this property.");
				}
			} else {
				//Not owned, enable UI for buying the property.
				this.buyProperty.setOpacity(1);
				this.buyProperty.setDisable(false);
				this.propertyInfoText.setText("This property is for sale!"
						+ "\nYou can buy it for $" + property.getValue());
			}
		} else {
			//Not a property that can be owned
			this.buyProperty.setOpacity(0);
			this.buyProperty.setDisable(true);
			this.propertyInfoText.setText("");
		}
	}
	
	/**
	 * Updates the property list for the pNum passed to it.
	 * @param pNum
	 * @return
	 */
	public TextFlow updatePlayerPropertyList(int pNum){
		TextFlow list = new TextFlow();
		
		//Iterates through HashMap and constructs the TextFlow to display
		//Also displays houses and "HOTEL"
		for (int i=1; i<=Game.getPlayer(pNum).getProperties().size(); i++){
			for(Property property : Game.getPlayer(pNum).getProperties().get(i)){
				String houses = "\n";
				//
				if(property.getPropertyType().equals("Residential")){
					Residential resProp = (Residential) property;
					if (resProp.getNumHouses() == 5){
						houses = houses + "\t\tHOTEL\n";
					} else if(resProp.getNumHouses() > 0){
						houses = houses + "\t\t";
						for (int j=0; j<resProp.getNumHouses(); j++){
							houses = houses + "⌂ ";
						}
						houses = houses + "\n";
					}
				}
				//Constructs property string
				Text propName = new Text("\t" + property.getPropertyName() + houses);
				//Sets color based on property member variable
				propName.setFill(Color.web(property.getColor()));
				if(property.isMortgaged()){
					propName.setFill(Color.web("#78909C"));
				}
				list.getChildren().add(propName);
			}
			Text blankLine = new Text("\n");
			list.getChildren().add(blankLine);
		}
		
		return list;
	}
	
	/**
	 * Updates player Titled Pane on left side for the pNum passed to it
	 * @param pNum
	 */
	public void updatePlayerInfo(int pNum){
		System.out.println("\tUpdate Player Info " + pNum);
		//MONEY
		playerMoneyLabels.get(pNum).setText("$" + Game.getPlayer(pNum).getMoney());
		//PROPERTIES - CLEAR
		playerPropertyLists.get(pNum).getChildren().clear();
		//PROPERTIES - POPULATE
		playerPropertyLists.get(pNum).getChildren().addAll(updatePlayerPropertyList(pNum));
		
		//Makes sure player doesnt have less than $0
		if(Game.getCurrPlayer().getMoney() < 0){
			setAlert("If you end your turn with a negative balance you will automatically forfeit the game.");
		}
	}
	
	//RIGHT SIDEBAR
	@FXML
	private Label currentTurn;
	@FXML
	private TitledPane propertyInfo;
	@FXML
	private TextArea propertyInfoText;
	@FXML 
	private Button buyProperty;
	/**
	 * Gets called by pressing the "Buy Property" button 
	 * Buys property and charges player
	 */
	public void buyProperty(){
		Property location = (Property) Game.getLocation(Game.getCurrPlayer().getPos());
		int propValue = location.getValue();
		//Purchases property
		Game.getCurrPlayer().addProperty(location);
		Game.getCurrPlayer().changeMoney(-propValue);
		
		location.changeOwner(Game.getCurrPlayerNum());
		System.out.println("\tBought Property:"
				+ "\n\t$" + Game.getCurrPlayer().getMoney());
		//Hides button
		buyProperty.setOpacity(0);
		buyProperty.setDisable(true);
	}
	
	//GAME BOARD
	@FXML
	private TabPane windowTab;
	
	@FXML
	private Button rollDiceButton;
	public void rollDice(ActionEvent event){
		Game.rollDice();
	}
	
	@FXML
	private MenuButton manageProperties;
	@FXML
	private MenuItem buildHouses;
	@FXML
	private MenuItem mortgageProperties;
	public void buildHouses(){
		launchPopUp().buildWindow();
	}
	public void mortgageProperties(){
		launchPopUp().mortgageWindow();

	}
	/**
	 * Launches popup window for either Mortgaging property or building houses
	 * @return
	 */
	public PopUpController launchPopUp(){
		
		PopUpController pc = new PopUpController();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopUp.fxml"));
			Parent root = loader.load();
            Stage stage = new Stage();
            pc = loader.getController();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		return pc;
	}
	
	@FXML
	private Button endTurnButton;
	/**
	 * Calls methods for ending a players turn and transitioning to the next player
	 */
	public void endTurn(){
		useGetOutOfJailCard.setVisible(false);
		useGetOutOfJailCard.setDisable(true);
		updatePlayerInfo(Game.getCurrPlayer().getPNum());
		Game.endTurn();
		updatePropertyInfo();
	}
	
	@FXML
	private Label alerts;
	/**
	 * Sets alert text right above "End Turn" button.
	 * @param msg
	 */
	public void setAlert(String msg){
		alerts.setText(msg);
		alerts.setDisable(false);
	}
	
	/**
	 * Clears any message from the alert text area
	 */
	public void clearAlert(){
		alerts.setText("No Alerts:");
		alerts.setDisable(true);
	}
	
	@FXML
	private Button tradeButton;
	/**
	 * Calls method to launch Trade Window
	 */
	public void trade(){
		launchTrade().buildWindow();
	}
	
	/**
	 * Launches window for Trade PopUp
	 * @return
	 */
	public TradeController launchTrade(){
		
		TradeController tc = new TradeController();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/TradePropView.fxml"));
			Parent root = loader.load();
            Stage stage = new Stage();
            tc = loader.getController();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		return tc;
	}
	
	@FXML
	private TextFlow rulesArea;
	
	/**
	 * Loads text for Rules pane from Rules.txt
	 */
	private void loadRules(){
		File rulesFile = new File("src/Rules.txt");
		try {
			Scanner rulesScnr = new Scanner(rulesFile);
			rulesScnr.useDelimiter("");
			while(rulesScnr.hasNext()){
				Text next = new Text(rulesScnr.next());
				rulesArea.getChildren().add(next);
			}
			rulesScnr.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error loading rules file");
		}
	}
	
	/**
	 * Removes player's existence from the board.
	 * @param pNum
	 */
	public void removePlayerPanel(int pNum){
		System.out.println("REMOVE PLAYER PANEL " + pNum);
		//Removes pane
		ppAccordion.getPanes().remove(playerPanes.get(pNum));
		//Hides token
		playerTokens.get(pNum-1).setOpacity(0);
		//Removes reference to token
		playerTokens.remove(pNum);
		//Removes reference to pane
		playerPanes.remove(pNum);
		//Removes reference to money label
		playerMoneyLabels.remove(pNum);
		//Removes reference to property list
		playerPropertyLists.remove(pNum);
		System.out.println("PLAYER PANEL REMOVED " + pNum);
	}
	
	@FXML
	private TitledPane winnerBanner;
	@FXML
	private Label winnerLabel;
	@FXML
	private Button closeButton;
	/**
	 * Ends game and closes window.
	 */
	public void close(){		
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
	/*
	 * Shows the winner dialogue box. 
	 * Declares who won the game
	 */
	public void broadcastWinner(String name){
		winnerLabel.setText(name + " won!");
		winnerBanner.setOpacity(1);
		winnerBanner.setDisable(false);
	}
	
	/**
	 * Initializes remove player sequence. 
	 * Called when player forfeits game.
	 */
	public void removePlayer(){
		Game.removePlayer();
	}
	
	@FXML
	private Button useGetOutOfJailCard;
	/**
	 * Allows player to use getOutOfJailFreeCard
	 */
	public void useGetOutOfJailCard(){
		Game.getCurrPlayer().removeGetOutOfJailFreeCard();
		useGetOutOfJailCard.setVisible(false);
		useGetOutOfJailCard.setDisable(true);
		Game.getOutOfJailSucker(true);
	}
}