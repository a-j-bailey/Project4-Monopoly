package Controller;

import java.util.ResourceBundle;
import java.util.Scanner;

import javax.imageio.ImageIO;

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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class GameController implements Initializable{
	
	@FXML
	private AnchorPane boardGame;
	@FXML
	private Accordion ppAccordion;
	
	private ImageView[] playerTokens;
	private TitledPane[] playerPanes;
	private Label[] playerMoneyLabels;
	private TextFlow[] playerPropertyLists;
		

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		System.out.println("\tLoading...");
		int playerNum = Game.getNumPlayers();
		System.out.println("\tNum Players: " + playerNum);
		
		playerTokens = new ImageView[playerNum];
		
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
			this.playerTokens[i] = token;
		}
		
		playerPanes = new TitledPane[playerNum];
		playerMoneyLabels = new Label[playerNum];
		playerPropertyLists = new TextFlow[playerNum];
		for (int i=0; i<playerNum; i++){
			
			//Token
			ImageView token = new ImageView();
			token.setImage(new Image("imgs/player" + (i+1) + "_token.png"));
			token.setFitWidth(50);
			token.setFitHeight(50);
			//Label (Money)
			Label money = new Label(" $" + Game.getPlayer(i).getMoney());
			playerMoneyLabels[i] = money;
			//Properties Title
			Label properties = new Label(" Properties:");
			//PropertiesTextArea
			TextFlow propText = new TextFlow();
			propText.setPrefWidth(160);
			propText.setPrefHeight(280);
			playerPropertyLists[i] = propText;
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
			playerPanes[i] = pane;
		}
		ppAccordion.getPanes().addAll(playerPanes);
		ppAccordion.setExpandedPane(playerPanes[0]);
		
		this.currentTurn.setText(Game.getPlayer(0).getPlayerName() + "'s Turn");
		updatePropertyInfo();
		
		Game.getLocation(Game.getCurrPlayer().getPos());
		
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
		playerTokens[player].setX(x);
		playerTokens[player].setY(y);
		
		if (!Game.getCanReroll()){
			rollDiceButton.setDisable(true);
			endTurnButton.setDisable(false);
		}
		manageProperties.setDisable(false);
		updatePropertyInfo();
	}
	
	public void nextPlayer(){
		rollDiceButton.setDisable(false);
		manageProperties.setDisable(true);
		endTurnButton.setDisable(true);
		
		//TODO: THIS WILL BE REMOVED
		buyProperty.setOpacity(0);
		
		this.currentTurn.setText(Game.getCurrPlayer().getPlayerName() + "'s Turn");
	}
	
	public void updatePropertyInfo(){
		Location thisLocation = Game.getLocation(Game.getCurrPlayer().getPos());
		//Sets pane title based on current player
		this.propertyInfo.setText(thisLocation.getPropertyName() + ":");
		
		System.out.println("\tCurrent Player: " + Game.getCurrPlayer().getPlayerName());
		System.out.println("\tCurrent Property: " + thisLocation.getPropertyName());
		
		//Populates Property Info table with information:
		if(thisLocation.getPropertyType().equals("Residential") ||
				thisLocation.getPropertyType().equals("Utility")){
			Property property = (Property) thisLocation;
			if(property.isBought()){
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
						Game.getPlayer(property.getOwner() - 1).getPlayerName() + 
						"\nYou paid " + rent + " in rent.");
			} else {
				this.buyProperty.setOpacity(1);
				this.propertyInfoText.setText("This property is for sale!"
						+ "\nYou can buy it for $" + property.getValue());
			}
		} else {
			this.buyProperty.setOpacity(0);
			this.propertyInfoText.setText("");
		}
		
		
	}
	
	public TextFlow updatePlayerPropertyList(int pNum){
		TextFlow list = new TextFlow();
		//pNum--;
		
		for (int i=1; i<=Game.getPlayer(pNum).getProperties().size(); i++){
			for(Property property : Game.getPlayer(pNum).getProperties().get(i)){
				Text propName = new Text("\t" + property.getPropertyName() + "\n");
				System.out.println("\t" + property.getPropertyName());
				propName.setFill(Color.web(property.getColor()));
				list.getChildren().add(propName);
			}
			Text blankLine = new Text("\n");
			list.getChildren().add(blankLine);
		}
		
		return list;
	}
	
	public void updatePlayerInfo(int pNum){
		System.out.println("\tUpdate Player Info " + pNum);
		pNum--;
		playerMoneyLabels[pNum].setText("$" + Game.getPlayer(pNum).getMoney());
		playerPropertyLists[pNum].getChildren().clear();
		playerPropertyLists[pNum].getChildren().addAll(updatePlayerPropertyList(pNum));
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
		launchPopUp().buildWindow("Build");
		//launchPopUp("Build");
	}
	public void mortgageProperties(){
		//new PopUpController("Mortgage");

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
		updatePlayerInfo(Game.getCurrPlayerNum());
		Game.endTurn();
		updatePropertyInfo();
	}
}