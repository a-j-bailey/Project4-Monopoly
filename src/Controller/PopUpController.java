package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Game;
import application.Property;
import application.Residential;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PopUpController implements Initializable{
	
	private Property thisProperty;
	private int money;
	
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
	
	public void buildWindow(){
		money = 0;
		popUpTitle.setText("Build Houses");
		houseChanger.setOpacity(1);
		houseChanger.setDisable(false);
		houseChanger.setValue("0 Houses");
		houseChanger.setItems(FXCollections.observableArrayList("0 Houses","1 House", "2 Houses", "3 Houses", "4 Houses", "Hotel"));
		houseChanger.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				HashMap<String, Residential> changedProperties = new HashMap<>();
				
				System.out.println("Index: " + newValue);
				Residential resProperty = (Residential) thisProperty;
				int numHouses = (int) newValue;
				if(changedProperties.containsKey(thisProperty.getPropertyName())){
					changedProperties.get(thisProperty.getPropertyName()).buildHouse(numHouses);
				} else {
					resProperty.buildHouse(numHouses);
					changedProperties.put(resProperty.getPropertyName(), resProperty);
				}
				if(numHouses < resProperty.getNumHouses()){
					money -= (resProperty.getHouseCost() * numHouses);
				} else {
					money += (resProperty.getHouseCost() * numHouses);
				}
				cost.setText("$"+money);
			}
		});
		propertyList.getItems().addAll(loadPropertyList("Build"));
		propertyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			HashMap<Integer, ArrayList<Property>> propList = Game.getCurrPlayer().getProperties();
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        System.out.println("Selected item: " + newValue);
				for(int i=1; i<=8; i++){
					for(int j=0; j<propList.get(i).size(); j++){
						if(propList.get(i).get(j).getPropertyName().equals(propertyList.getSelectionModel().getSelectedItem())){
							thisProperty = propList.get(i).get(j);
						}
					}
				}
		    }
		});
	}

	public void mortgageWindow(){
		popUpTitle.setText("Mortgage Houses");
		mortgageCheck.setOpacity(1);
		propertyList.getItems().addAll(loadPropertyList("Mortgage"));
	}

	public ObservableList<String> loadPropertyList(String type){
		ObservableList<String> propList = FXCollections.<String>observableArrayList();
		HashMap<Integer, ArrayList<Property>> playerProperties = Game.getCurrPlayer().getProperties();
		if (type.equals("Build")){
			//TODO: Only add houses if there is a complete set. 
			for (int i=1; i<=8; i++){
				for(Property property : playerProperties.get(i)){
					propList.add(property.getPropertyName());
				}
			}
		} else {
			for (int i=1; i<=10; i++){
				for(Property property : playerProperties.get(i)){
					propList.add(property.getPropertyName());
				}
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
