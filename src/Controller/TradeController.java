package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

import application.Game;
import application.Player;
import application.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TradeController implements Initializable{

	HashSet<Property> currPlayerToTrade = new HashSet<>();
	HashSet<Property> otherPlayerToTrade = new HashSet<>();
	Player otherPlayer = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private Label currPlayer;
	@FXML
	private ChoiceBox<String> otherPlayerSelector;
	@FXML
	private VBox currPlayerListBox;
	@FXML
	private VBox otherPlayerListBox;
	@FXML
	private CheckBox currPlayerApproval;
	@FXML
	private CheckBox otherPlayerApproval;
	@FXML
	private Button save;


	/**
	 * What happens when the "save" botton is pushed
	 */
	public void save(){

		int otherPlayerNum = -1;

		HashMap<Integer, Player> players = Game.getPlayers();
		for(int i=1; i<=Game.getNumPlayers(); i++){
			if(players.containsKey(i)){							//Assigns the "other" player 
				if(Game.getPlayer(i) == otherPlayer){
					otherPlayerNum = i;
				}
			}
		}

		for(Property property : currPlayerToTrade){				//Finds the property that will be traded. 
			Game.getCurrPlayer().removeProperty(property);		//	and changes the owner
			otherPlayer.addProperty(property);
			property.changeOwner(otherPlayerNum);
		}
		for(Property property : otherPlayerToTrade){			//Finds the property from the "other" player that will be traded
			otherPlayer.removeProperty(property);				//	and changes the owner
			Game.getCurrPlayer().addProperty(property);
			property.changeOwner(Game.getCurrPlayer().getPNum());
		}

		Game.getController().updatePlayerInfo(Game.getCurrPlayer().getPNum());
		Game.getController().updatePlayerInfo(otherPlayerNum);

		Stage stage = (Stage) save.getScene().getWindow();
		stage.close();
	}


	/**
	 * Method that builds the trade window
	 */
	public void buildWindow(){												//Builds the trade window
		currPlayer.setText(Game.getCurrPlayer().getPlayerName());
		ObservableList<String> playerNames = FXCollections.<String>observableArrayList();
		HashMap<Integer, Player> players = Game.getPlayers();
		for(int i=1; i<=Game.getNumPlayers(); i++){
			if(players.containsKey(i)){
				if(Game.getCurrPlayer() != Game.getPlayer(i)){
					playerNames.add(Game.getPlayer(i).getPlayerName());
				}
			}
		}
		otherPlayerSelector.setItems(playerNames);
		otherPlayerSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				currPlayerToTrade = new HashSet<>();
				otherPlayerToTrade = new HashSet<>();
				HashMap<Integer, Player> players = Game.getPlayers();
				for(int i=1; i<=Game.getNumPlayers(); i++){
					if(players.containsKey(i)){
						if(Game.getPlayer(i).getPlayerName().equals(newValue)){
							otherPlayer = Game.getPlayer(i);
						}
					}
				}
				HashMap<Integer, ArrayList<Property>> otherPlayerProperties = otherPlayer.getProperties();
				for (int i=1; i<=10; i++){
					for(Property property : otherPlayerProperties.get(i)){
						CheckBox propCheck = new CheckBox(property.getPropertyName());
						propCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
									Boolean newValue) {
								if(newValue){
									otherPlayerToTrade.add(property);
								} else {
									otherPlayerToTrade.remove(property);
								}
							}
						});
						otherPlayerListBox.getChildren().add(propCheck);
					}
				}
			}
		});
		HashMap<Integer, ArrayList<Property>> currPlayerProperties = Game.getCurrPlayer().getProperties();	//Lists the possible properties a player can trade
		for (int i=1; i<=10; i++){
			for(Property property : currPlayerProperties.get(i)){
				CheckBox propCheck = new CheckBox(property.getPropertyName());
				propCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if(newValue){
							currPlayerToTrade.add(property);
						} else {
							currPlayerToTrade.remove(property);
						}
					}
				});
				currPlayerListBox.getChildren().add(propCheck);
			}
		}
		currPlayerApproval.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				checkEnableTrade();
			}
		});
		otherPlayerApproval.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				checkEnableTrade();
			}
		});		
	}

	/**
	 * Checks to see if both players are trading a property and both players approve
	 */
	public void checkEnableTrade(){													//Checks for player approval
		if(currPlayerApproval.isSelected() && otherPlayerApproval.isSelected()		//	and that each player is trading something
				&& (currPlayerToTrade.size() > 0) && (otherPlayerToTrade.size() > 0)){
			save.setDisable(false);
		}
	}
}
