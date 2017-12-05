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
		
		this.currentTurn.setText(Game.getPlayer(1).getPlayerName() + "'s Turn");
		updatePropertyInfo();
		
		Game.getLocation(Game.getCurrPlayer().getPos());
		
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
		playerTokens.get(player).setX(x);
		playerTokens.get(player).setY(y);
		
		if (!Game.getCanReroll()){
			rollDiceButton.setDisable(true);
			endTurnButton.setDisable(false);
		}
		manageProperties.setDisable(false);
		tradeButton.setDisable(false);
		updatePropertyInfo();
	}
	
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
	
	public void updatePropertyInfo(){
		Location thisLocation = Game.getLocation(Game.getCurrPlayer().getPos());
		//Sets pane title based on current player
		this.propertyInfo.setText(thisLocation.getPropertyName() + ":");
		
		System.out.println("\tCurrent Property: " + thisLocation.getPropertyName());
		
		//Populates Property Info table with information:
		if(thisLocation.getPropertyType().equals("Residential") ||
				thisLocation.getPropertyType().equals("Utility")){
			Property property = (Property) thisLocation;
			if(property.isBought()){
				this.buyProperty.setOpacity(0);
				this.buyProperty.setDisable(true);
				int rent = 0;
				if(property.getPropertyType().equals("Utility")){
					Utility util = (Utility) property;
					String name = util.getPropertyName();
					Scanner nameScn = new Scanner(name);
					if (nameScn.next().equals("Water") || nameScn.next().equals("Electric")){
						int numOwned = Game.getPlayer(util.getOwner()).getProperties().get(10).size();
						int dice = Game.getCurrentDice()[0] + Game.getCurrentDice()[1];
						rent = util.calcUtilityRent(dice, numOwned);
					} else {
						int numOwned = Game.getPlayer(util.getOwner()).getProperties().get(9).size();
						rent = util.calcRailroadRent(numOwned);
						System.out.println("\tRR Rent: " + rent);
					}
					nameScn.close();
				} else {
					Residential prop = (Residential) property;
					rent = prop.getRent();
				}
				this.propertyInfoText.setText("This property is owned by " + 
						Game.getPlayer(property.getOwner()).getPlayerName() + 
						"\nYou paid " + rent + " in rent.");
			} else {
				this.buyProperty.setOpacity(1);
				this.buyProperty.setDisable(false);
				this.propertyInfoText.setText("This property is for sale!"
						+ "\nYou can buy it for $" + property.getValue());
			}
		} else {
			this.buyProperty.setOpacity(0);
			this.buyProperty.setDisable(true);
			this.propertyInfoText.setText("");
		}
	}
	
	public TextFlow updatePlayerPropertyList(int pNum){
		TextFlow list = new TextFlow();
		//pNum--;
		
		for (int i=1; i<=Game.getPlayer(pNum).getProperties().size(); i++){
			for(Property property : Game.getPlayer(pNum).getProperties().get(i)){
				String houses = "\n";
				if(property.getPropertyType().equals("Residential")){
					Residential resProp = (Residential) property;
					if (resProp.getNumHouses() == 5){
						houses = houses + "\t\tHOTEL";
					} else if(resProp.getNumHouses() > 0){
						houses = houses + "\t\t";
						for (int j=0; j<resProp.getNumHouses(); j++){
							houses = houses + "⌂ ";
						}
						houses = houses + "\n";
					}
				}
				Text propName = new Text("\t" + property.getPropertyName() + houses);
				propName.setFill(Color.web(property.getColor()));
				if(property.isMortgaged()){
					propName.setOpacity(0.5);
				}
				list.getChildren().add(propName);
			}
			Text blankLine = new Text("\n");
			list.getChildren().add(blankLine);
		}
		
		return list;
	}
	
	public void updatePlayerInfo(int pNum){
		System.out.println("\tUpdate Player Info " + pNum);
		playerMoneyLabels.get(pNum).setText("$" + Game.getPlayer(pNum).getMoney());
		playerPropertyLists.get(pNum).getChildren().clear();
		playerPropertyLists.get(pNum).getChildren().addAll(updatePlayerPropertyList(pNum));
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
	public void buyProperty(){
		Property location = (Property) Game.getLocation(Game.getCurrPlayer().getPos());
		int propValue = location.getValue();
		Game.getCurrPlayer().addProperty(location);
		Game.getCurrPlayer().changeMoney(-propValue);
		location.changeOwner(Game.getCurrPlayerNum());
		System.out.println("\tBought Property:"
				+ "\n\t$" + Game.getCurrPlayer().getMoney());
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
	public void endTurn(){
		useGetOutOfJailCard.setVisible(false);
		useGetOutOfJailCard.setDisable(true);
		updatePlayerInfo(Game.getCurrPlayer().getPNum());
		Game.endTurn();
		updatePropertyInfo();
	}
	
	@FXML
	private Label alerts;
	public void setAlert(String msg){
		alerts.setText(msg);
		alerts.setDisable(false);
	}
	public void clearAlert(){
		alerts.setText("No Alerts:");
		alerts.setDisable(true);
	}
	
	@FXML
	private Button tradeButton;
	public void trade(){
		launchTrade().buildWindow();
	}
	
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
	
	public void removePlayerPanel(int pNum){
		System.out.println("REMOVE PLAYER PANEL " + pNum);
		ppAccordion.getPanes().remove(playerPanes.get(pNum));
		playerTokens.get(pNum-1).setOpacity(0);
		playerTokens.remove(pNum);
		playerPanes.remove(pNum);
		playerMoneyLabels.remove(pNum);
		playerPropertyLists.remove(pNum);
		System.out.println("PLAYER PANEL REMOVED " + pNum);
	}
	
	@FXML
	private TitledPane winnerBanner;
	@FXML
	private Label winnerLabel;
	@FXML
	private Button closeButton;
	public void close(){		
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
	
	public void broadcastWinner(String name){
		winnerLabel.setText(name + " won!");
		winnerBanner.setOpacity(1);
		winnerBanner.setDisable(false);
	}
	
	public void removePlayer(){
		Game.removePlayer();
	}
	
	@FXML
	private Button useGetOutOfJailCard;
	public void useGetOutOfJailCard(){
		useGetOutOfJailCard.setVisible(false);
		useGetOutOfJailCard.setDisable(true);
		Game.getOutOfJailSucker(true);
	}
}