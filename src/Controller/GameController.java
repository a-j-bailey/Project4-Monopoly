package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;

public class GameController implements Initializable{
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		System.out.println(" -- Initializing Game -- ");
		int playerNum = Game.getNumPlayers();
		System.out.println("\tNum Players: " + playerNum);
				
		if(playerNum >= 1){
			this.pp1.setDisable(false);
			this.p1_token.setDisable(false);
			this.pp1.setText(Game.getPlayerName(0));
			this.pp1_money.setText("$" + Game.getPlayerMoney(0));
		}
				
		if(playerNum >= 2){
			this.pp2.setDisable(false);
			this.p2_token.setDisable(false);
			this.pp2.setText(Game.getPlayerName(1));
			this.pp2_money.setText("$" + Game.getPlayerMoney(1));
		}
				
		if(playerNum >= 3){
			this.pp3.setDisable(false);
			this.p3_token.setDisable(false);
			this.pp3.setText(Game.getPlayerName(2));
			this.pp3_money.setText("$" + Game.getPlayerMoney(2));
		}
				
		if(playerNum >= 4){
			this.pp4.setDisable(false);
			this.p4_token.setDisable(false);
			this.pp4.setText(Game.getPlayerName(3));
			this.pp4_money.setText("$" + Game.getPlayerMoney(3));
		}
				
		if(playerNum >= 5){
			this.pp5.setDisable(false);
			this.p5_token.setDisable(false);
			this.pp5.setText(Game.getPlayerName(4));
			this.pp5_money.setText("$" + Game.getPlayerMoney(4));
		}
				
		if(playerNum >= 6){
			this.pp6.setDisable(false);
			this.p6_token.setDisable(false);
			this.pp6.setText(Game.getPlayerName(5));
			this.pp6_money.setText("$" + Game.getPlayerMoney(5));
		}
		
		this.currentTurn.setText(Game.getPlayerName(0) + "'s Turn");
		
		System.out.println(" -- Game Initialized -- ");
	}
	
	// MONOPOLY GAME
	
	@FXML
	private TitledPane pp1;
	@FXML
	private Label pp1_money;
	
	@FXML
	private TitledPane pp2;
	@FXML
	private Label pp2_money;
	
	@FXML
	private TitledPane pp3;
	@FXML
	private Label pp3_money;
	
	@FXML
	private TitledPane pp4;
	@FXML
	private Label pp4_money;
	
	@FXML
	private TitledPane pp5;
	@FXML
	private Label pp5_money;
	
	@FXML
	private TitledPane pp6;
	@FXML
	private Label pp6_money;
	
	@FXML
	private Label currentTurn;
	
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
}