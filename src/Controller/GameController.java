package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import application.Game;
import application.Location;
import application.Property;
import application.Residential;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;

public class GameController implements Initializable{
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		System.out.println("\tLoading...");
		int playerNum = Game.getNumPlayers();
		System.out.println("\tNum Players: " + playerNum);
				
		if(playerNum >= 1){
			this.pp1.setDisable(false);
			this.p1_token.setDisable(false);
			this.p1_token.setOpacity(1);
			this.pp1.setText(Game.getPlayer(0).getPlayerName());
			this.pp1_money.setText("$" + Game.getPlayer(0).getMoney());
		}
				
		if(playerNum >= 2){
			this.pp2.setDisable(false);
			this.p2_token.setDisable(false);
			this.p2_token.setOpacity(1);
			this.pp2.setText(Game.getPlayer(1).getPlayerName());
			this.pp2_money.setText("$" + Game.getPlayer(1).getMoney());
		}
				
		if(playerNum >= 3){
			this.pp3.setDisable(false);
			this.p3_token.setDisable(false);
			this.p3_token.setOpacity(1);
			this.pp3.setText(Game.getPlayer(2).getPlayerName());
			this.pp3_money.setText("$" + Game.getPlayer(2).getMoney());
		}
				
		if(playerNum >= 4){
			this.pp4.setDisable(false);
			this.p4_token.setDisable(false);
			this.p4_token.setOpacity(1);
			this.pp4.setText(Game.getPlayer(3).getPlayerName());
			this.pp4_money.setText("$" + Game.getPlayer(3).getMoney());
		}
				
		if(playerNum >= 5){
			this.pp5.setDisable(false);
			this.p5_token.setDisable(false);
			this.p5_token.setOpacity(1);
			this.pp5.setText(Game.getPlayer(4).getPlayerName());
			this.pp5_money.setText("$" + Game.getPlayer(4).getMoney());
		}
				
		if(playerNum >= 6){
			this.pp6.setDisable(false);
			this.p6_token.setDisable(false);
			this.p6_token.setOpacity(1);
			this.pp6.setText(Game.getPlayer(5).getPlayerName());
			this.pp6_money.setText("$" + Game.getPlayer(5).getMoney());
		}
		
		this.currentTurn.setText(Game.getPlayer(0).getPlayerName() + "'s Turn");
		updatePropertyInfo();
		
		Game.getLocation(Game.getCurrPlayer().getPos());
		loadGraph();
		
		System.out.println(" -- Game Initialized -- ");
	}
	
	/**
	 * Accepts player number and (x,y) coordinates and moves player to correct position
	 * @param player
	 * @param x
	 * @param y
	 */
	public void moveToken(int player, int x, int y){
		if (player == 0){
			p1_token.setX(x);
			p1_token.setY(y);
		} else if (player == 1){
			p2_token.setX(x);
			p2_token.setY(y);
		} else if (player == 2){
			p3_token.setX(x);
			p3_token.setY(y);
		} else if (player == 3){
			p4_token.setX(x);
			p4_token.setY(y);
		} else if (player == 4){
			p5_token.setX(x);
			p5_token.setY(y);
		} else if (player == 5){
			p6_token.setX(x);
			p6_token.setY(y);
		}
		//TODO: Update sidepanel and inform user of dice roll and move
		//TODO: Update side panel with info on current position
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
		
		//Populates Property Info table with information:
		this.propertyInfoText.setText("This will contain some nifty info about this property."
				+ "\nOnce Stephan finishes that part. ;)");
		if(thisLocation.getPropertyType().equals("Residential") || 
				thisLocation.getPropertyType().equals("Utility")){
			Property propLocation = (Property) thisLocation;
			if(!propLocation.isBought()){
				this.buyProperty.setOpacity(1);
				this.propertyInfoText.setText("This property is for sale!"
						+ "\nYou can buy it for $" + propLocation.getValue());
			}
		}
	}
	
	public TextFlow updatePlayerPropertyList(int pNum){
		TextFlow list = new TextFlow();
		pNum--;
		for(Property property : Game.getPlayer(pNum).getProperties()){
			Text propName = new Text("\t" + property.getPropertyName() + "\n");
			System.out.println("\t" + property.getPropertyName());
			propName.setFill(Color.web(property.getColor()));
			list.getChildren().add(propName);
		}
		
		return list;
	}
	
	public void updatePlayerInfo(int pNum){
		System.out.println("\tUpdate Player Info " + pNum);
		switch (pNum){
		case 1:
			this.pp1_money.setText("$" + Game.getPlayer(0).getMoney());
			pp1_propertyArea.getChildren().clear();
			pp1_propertyArea.getChildren().addAll(updatePlayerPropertyList(1));
			break;
		case 2:
			this.pp2_money.setText("$" + Game.getPlayer(0).getMoney());
			pp2_propertyArea.getChildren().clear();
			pp2_propertyArea.getChildren().addAll(updatePlayerPropertyList(2));
			break;
		case 3:
			this.pp3_money.setText("$" + Game.getPlayer(0).getMoney());
			pp3_propertyArea.getChildren().clear();
			pp3_propertyArea.getChildren().addAll(updatePlayerPropertyList(3));
			break;
		case 4:
			this.pp4_money.setText("$" + Game.getPlayer(0).getMoney());
			pp4_propertyArea.getChildren().clear();
			pp4_propertyArea.getChildren().addAll(updatePlayerPropertyList(4));
			break;
		case 5:
			this.pp5_money.setText("$" + Game.getPlayer(0).getMoney());
			pp5_propertyArea.getChildren().clear();
			pp5_propertyArea.getChildren().addAll(updatePlayerPropertyList(5));
			break;
		case 6:
			this.pp1_money.setText("$" + Game.getPlayer(0).getMoney());
			pp6_propertyArea.getChildren().clear();
			pp6_propertyArea.getChildren().addAll(updatePlayerPropertyList(6));
			break;

		}
	}
	
	//LEFT SIDEBAR
	@FXML
	private TitledPane pp1;
	@FXML
	private Label pp1_money;
	@FXML
	private TextFlow pp1_propertyArea;
	@FXML
	private TitledPane pp2;
	@FXML
	private Label pp2_money;
	@FXML
	private TextFlow pp2_propertyArea;
	@FXML
	private TitledPane pp3;
	@FXML
	private Label pp3_money;
	@FXML
	private TextFlow pp3_propertyArea;
	@FXML
	private TitledPane pp4;
	@FXML
	private Label pp4_money;
	@FXML
	private TextFlow pp4_propertyArea;
	@FXML
	private TitledPane pp5;
	@FXML
	private Label pp5_money;
	@FXML
	private TextFlow pp5_propertyArea;
	@FXML
	private TitledPane pp6;
	@FXML
	private Label pp6_money;
	@FXML
	private TextFlow pp6_propertyArea;

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
		location.changeOwner(Game.getCurrPlayerNum() + 1);
		System.out.println("\tBought Property:"
				+ "\n\t$" + Game.getCurrPlayer().getMoney());
		buyProperty.setOpacity(0);
	}
	
	//GAME BOARD
	@FXML
	private TabPane windowTab;
	@FXML
	private StackedBarChart<String, Number> leaderBoard;
	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	private XYChart.Series<String, Number> money = new XYChart.Series<>();
	public void loadGraph(){
		int numPlayers = Game.getNumPlayers();
		//MONEY
		money.setName("Money");
		for(int i=0; i<numPlayers; i++){
			money.getData().add(new XYChart.Data<String, Number>(
					Game.getPlayer(i).getPlayerName(),
					Game.getPlayer(i).getMoney()));
		}
		leaderBoard.getData().add(money);
		System.out.println("\tLoaded Graph");
	}
	public void updateGraph(){
		//Game.getCurrPlayer().changeMoney(100);
		money.getData().set(Game.getCurrPlayerNum(), new XYChart.Data<String, Number>(
				Game.getCurrPlayer().getPlayerName(),
				Game.getCurrPlayer().getMoney()));
	}
	
	@FXML
	private ImageView p1_token;
	@FXML
	private ImageView p2_token;
	@FXML
	private ImageView p3_token;
	@FXML
	private ImageView p4_token;
	@FXML
	private ImageView p5_token;
	@FXML
	private ImageView p6_token;
	
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
		launchPopUp("Build");
	}
	public void mortgageProperties(){
		launchPopUp("Mortgage");
	}
	
	@FXML
	private Button endTurnButton;
	public void endTurn(){
		updatePropertyInfo();
		updatePlayerInfo(Game.getCurrPlayerNum() + 1);
		Game.endTurn();
	}
	
	//POPUP:
	public void launchPopUp(String type){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/PopUp.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}