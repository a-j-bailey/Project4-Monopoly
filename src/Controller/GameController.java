package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
		managePropertiesButton.setDisable(false);
	}
	
	public void nextPlayer(){
		rollDiceButton.setDisable(false);
		managePropertiesButton.setDisable(true);
		endTurnButton.setDisable(true);
		this.currentTurn.setText(Game.getCurrPlayer().getPlayerName() + "'s Turn");
	}
	
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
	
	@FXML
	private Button rollDiceButton;
	public void rollDice(ActionEvent event){
		Game.rollDice();
	}
	
	@FXML
	private MenuButton managePropertiesButton;
	public void manageProperties(){
		//TODO: This
	}
	@FXML
	private Button endTurnButton;
	public void endTurn(){
		Game.endTurn();
	}
}