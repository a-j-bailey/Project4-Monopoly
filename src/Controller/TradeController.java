package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Game;
import application.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TradeController implements Initializable{

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
	public void save(){
		Stage stage = (Stage) save.getScene().getWindow();
		stage.close();
	}
	
	public void buildWindow(){
		currPlayer.setText(Game.getCurrPlayer().getPlayerName());
		ObservableList<String> playerNames = FXCollections.<String>observableArrayList();
		for(int i=0; i<Game.getNumPlayers(); i++){
			if(Game.getCurrPlayer() != Game.getPlayer(i)){
				playerNames.add(Game.getPlayer(i).getPlayerName());
			}
		}
		otherPlayerSelector.setItems(playerNames);
		HashMap<Integer, ArrayList<Property>> currPlayerProperties = Game.getCurrPlayer().getProperties();
		for (int i=1; i<=10; i++){
			for(Property property : currPlayerProperties.get(i)){
				CheckBox propCheck = new CheckBox(property.getPropertyName());
				currPlayerListBox.getChildren().add(propCheck);
			}
		}
	}
}
