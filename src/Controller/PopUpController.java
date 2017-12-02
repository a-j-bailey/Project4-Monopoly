package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Game;
import application.Property;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PopUpController implements Initializable{
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(" -- PopUp Initialized -- ");
	}
	
	public void launch(){
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
	
	public void buildWindow(String type){
		System.out.println("\t" + type);
		if (type.equals("Build")){
			popUpTitle.setText("Build Houses");
			houseChanger.setOpacity(1);
			propertyList.getItems().addAll(loadPropertyList());
		} else {
			popUpTitle.setText("Mortgage Houses");
			mortgageCheck.setOpacity(1);
			propertyList.getItems().addAll(loadPropertyList());
		}
	}
	
	public ObservableList<String> loadPropertyList(){
		ObservableList<String> propList = FXCollections.<String>observableArrayList();

		HashMap<Integer, ArrayList<Property>> playerProperties = Game.getCurrPlayer().getProperties();
		
		for (int i=1; i<=playerProperties.size(); i++){
			for(Property property : playerProperties.get(i)){
				propList.add(property.getPropertyName());
			}
		}
		
		return propList;
	}
	
	@FXML
	private Label popUpTitle;
	@FXML
	private ListView<String> propertyList;
	@FXML
	private CheckBox mortgageCheck;
	@FXML
	private ChoiceBox<String> houseChanger;
	@FXML
	private Label cost;
	@FXML
	private Button cancel;
	@FXML
	private Button save;
	@FXML
	private TextFlow warningArea;
	
}
