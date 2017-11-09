package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LauncherController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(" -- Launcher Initialized -- ");
	}
		
	// NEW GAME LAUNCHER
	@FXML
	private Pane newGameWindow;
	
	@FXML
	private TextField p1Name;
	
	@FXML
	private TextField p2Name;
	
	@FXML
	private RadioButton p3;
	@FXML
	private TextField p3Name;
	public void changeP3(ActionEvent event){
		p3Name.setDisable(!p3Name.isDisabled());
	}
	
	@FXML
	private RadioButton p4;
	@FXML
	private TextField p4Name;
	public void changeP4(ActionEvent event){
		p4Name.setDisable(!p4Name.isDisabled());
	}
	
	@FXML
	private RadioButton p5;
	@FXML
	private TextField p5Name;
	public void changeP5(ActionEvent event){
		p5Name.setDisable(!p5Name.isDisabled());
	}
	
	@FXML
	private RadioButton p6;
	@FXML
	private TextField p6Name;
	public void changeP6(ActionEvent event){
		p6Name.setDisable(!p6Name.isDisabled());
	}
	
	@FXML
	private Button play;
	public void launchGame(ActionEvent event){
		
		//TODO: what if user doesn't enter names or leaves places empty?

		ArrayList<String> playerNames = new ArrayList<String>();
		playerNames.add(p1Name.getText());
		playerNames.add(p2Name.getText());
		if(!p3Name.isDisabled()){
			playerNames.add(p3Name.getText());
		}
		if(!p4Name.isDisabled()){
			playerNames.add(p4Name.getText());
		}
		if(!p5Name.isDisabled()){
			playerNames.add(p5Name.getText());
		}
		if(!p6Name.isDisabled()){
			playerNames.add(p6Name.getText());
		}
		
		Stage stage = (Stage) play.getScene().getWindow();
		stage.close();
		
		new Game(playerNames);
	}
}
