package Controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Game;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LauncherController implements Initializable{
	
	@FXML
	private VBox launcherVbox;
	
	private RadioButton[] buttons = new RadioButton[4];
	private TextField[] textFields = new TextField[4];
	private HBox[] addPlayers = new HBox[4];
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		for(int i=0; i<4; i++){
			TextField text = new TextField();
			text.setPromptText("Name");
			text.setDisable(true);
			
			RadioButton button = new RadioButton(" Player " + (i+3));
			button.selectedProperty().addListener(new ChangeListener<Boolean>() {
			    @Override
			    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
			        text.setDisable(!text.isDisable());
			    }
			});
			
			buttons[i] = button;
			textFields[i] = text;
			
			HBox box = new HBox();
			box.getChildren().add(button);
			box.getChildren().add(text);
			box.setAlignment(Pos.CENTER);
			box.setSpacing(20);
			addPlayers[i] = box;
		}
		
		launcherVbox.setPadding(new Insets(10, 0, 0, -32));
		launcherVbox.setSpacing(10);
		launcherVbox.getChildren().addAll(addPlayers);
		
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
	private Button play;
	public void launchGame(ActionEvent event){
		
		ArrayList<String> playerNames = new ArrayList<String>();

		if(!p1Name.getText().equals("")){
			playerNames.add(p1Name.getText());
			p1Name.setStyle("-fx-control-inner-background: #FFFFFF");
		} else {
			p1Name.setStyle("-fx-control-inner-background: #FF8A80");
		}
		if(!p2Name.getText().equals("")){
			playerNames.add(p2Name.getText());
			p2Name.setStyle("-fx-control-inner-background: #FFFFFF");
		} else {
			p2Name.setStyle("-fx-control-inner-background: #FF8A80");
		}
		if(playerNames.size() == 2){
			for(int i=0; i<4; i++){
				if(buttons[i].isSelected()){
					if(!textFields[i].getText().equals("")){
						playerNames.add(textFields[i].getText());
					}
				}
			}
			
			Stage stage = (Stage) play.getScene().getWindow();
			stage.close();
			
			System.out.println(" -- Initializing Game -- ");
			
			try {
				new Game(playerNames);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
