package Controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PopUpController implements Initializable{
	
	private String type;
	private Property thisProperty;
	private int money;
	private HashMap<Property, Integer> changedProperties = new HashMap<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(" -- PopUp Initialized -- ");
	}
	
	/**
	 * Launches window for building houses
	 */
	public void buildWindow(){
		money = 0;
		popUpTitle.setText("Build House"); 
		type = "Build";
		//Formats houseChanger
		houseChanger.setOpacity(1);
		houseChanger.setDisable(false);
		houseChanger.setValue("0 Houses");
		//Adds house options to house changer
		houseChanger.setItems(FXCollections.observableArrayList("0 Houses","1 House", "2 Houses", "3 Houses", "4 Houses", "Hotel"));
		//Adds selection listener
		houseChanger.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				System.out.println("Index: " + newValue);
				Residential resProperty = (Residential) thisProperty;
				int oldHouses = 0;
				int numHouses = (int) newValue;
				//Checks to see if property already exists, if so, replaces it.
				if(changedProperties.containsKey(thisProperty)){
					oldHouses = changedProperties.get(resProperty);
					changedProperties.put(resProperty, numHouses);
				} else {
					//if it doesnt exists it gets old houses from property itself, and puts it in HashMap
					oldHouses = resProperty.getNumHouses();
					changedProperties.put(resProperty, numHouses);
				}
				//Adds or subtracts money from total money for display
				if(numHouses < changedProperties.get(resProperty)){
					money -= ((0.5)*resProperty.getHouseCost() * (oldHouses - numHouses));
				} else {
					money += (resProperty.getHouseCost() * (numHouses - oldHouses));
				}
				//sets display
				cost.setText("$"+((-1)*money));
			}
		});
		//adds properties that can be built on
		propertyList.getItems().addAll(loadPropertyList("Build"));
		//adds event listener
		propertyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			HashMap<Integer, ArrayList<Property>> propList = Game.getCurrPlayer().getProperties();
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        System.out.println("Selected item: " + newValue);
		        //finds property that matches the selected String
				for(int i=1; i<=8; i++){
					for(int j=0; j<propList.get(i).size(); j++){
						if(propList.get(i).get(j).getPropertyName().equals(propertyList.getSelectionModel().getSelectedItem())){
							//sets property to thisProperty.
							thisProperty = (Residential) propList.get(i).get(j);
						}
					}
				}
				
				Residential resProp = (Residential) thisProperty;
				int numHouses = 0;
				//Checks to see if this property has already been changed and gets numHouses from that
				if(changedProperties.containsKey(resProp)){
					numHouses = changedProperties.get(resProp);
				} else {
					numHouses = resProp.getNumHouses();
				}
				//Sets houseChanger display to match the numHouses already on the property.
				if(numHouses == 5){
					houseChanger.setValue("Hotel");
				} else if(numHouses == 1) {
					houseChanger.setValue(numHouses + " House");
				} else {
					houseChanger.setValue(numHouses + " Houses");
				}
		    }
		});
	}

	/**
	 * Builds window for mortgaging properties
	 */
	public void mortgageWindow(){
		popUpTitle.setText("Mortgage Houses");
		type = "Mortgage";
		//Sets up mortgageCheck
		mortgageCheck.setOpacity(1);
		mortgageCheck.setDisable(false);
		mortgageCheck.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				//sets val based on boolen so that we can use the same ChangedProperties HashMap
				int val = -1;
				if(newValue){
					val = 1;
				} else {
					val = 0;
				}
				
				Boolean mortgageable = null;
				
				//Makes sure there arent any houses on the proerty before mortgaging it
				if(thisProperty.getPropertyType().equals("Residential")){
					Residential resProp = (Residential) thisProperty;
					if(resProp.getNumHouses() > 0){
						mortgageable = false;
					} else {
						mortgageable = true;
					}
				} else {
					mortgageable = true;
				}
				
				//Updates money value for mortgaging 
				if(mortgageable){
					changedProperties.put(thisProperty, val);
					if(newValue){
						money += thisProperty.getMortgageValue();
					} else {
						money -= thisProperty.getMortgageValue();
					}
					cost.setText("$"+money);
				} else {
					//If property is not mortgageable sets and displays warning
					warningArea.getChildren().clear();
					Text text = new Text("You can't mortgage a property that has houses."
							+ "\nPlease sell the houses on " + thisProperty.getPropertyName() 
							+ " before mortgaging this property.");
					text.setFill(Color.RED);
					warningArea.getChildren().add(text);
					warningArea.setOpacity(1);
					mortgageCheck.setSelected(false);
				}
			}
		});
		//Adds property list for properties to be mortgaged
		propertyList.getItems().addAll(loadPropertyList("Mortgage"));
				propertyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			HashMap<Integer, ArrayList<Property>> propList = Game.getCurrPlayer().getProperties();
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        System.out.println("Selected item: " + newValue);
				for(int i=1; i<=8; i++){
					for(int j=0; j<propList.get(i).size(); j++){
						if(propList.get(i).get(j).getPropertyName().equals(propertyList.getSelectionModel().getSelectedItem())){
							thisProperty = (Residential) propList.get(i).get(j);
						}
					}
				}
				//Updates mortgageCheck with mortgage value of property.
				if(thisProperty.isMortgaged()){
					mortgageCheck.setSelected(true);
				} else {
					mortgageCheck.setSelected(false);
				}
		    }
		});
	}

	/**
	 * Loads property ObservableList to display properties. 
	 * @param type
	 * @return
	 */
	public ObservableList<String> loadPropertyList(String type){
		ObservableList<String> propList = FXCollections.<String>observableArrayList();
		HashMap<Integer, ArrayList<Property>> playerProperties = Game.getCurrPlayer().getProperties();
		if (type.equals("Build")){
			//for properties that can be built on
			for (int i=1; i<=8; i++){
				//Checks to make sure the player has a complete set of each property.
				if((i == 1 && playerProperties.get(i).size() == 2) || 
						(i == 2 && playerProperties.get(i).size() == 3) || 
						(i == 3 && playerProperties.get(i).size() == 3) ||
						(i == 4 && playerProperties.get(i).size() == 3) || 
						(i == 5 && playerProperties.get(i).size() == 3) || 
						(i == 6 && playerProperties.get(i).size() == 3) ||
						(i == 7 && playerProperties.get(i).size() == 3) ||
						(i == 8 && playerProperties.get(i).size() == 2)) {
					//Gets name and adds it to list
					for(Property property : playerProperties.get(i)){
						propList.add(property.getPropertyName());
					}
					
				}
				
			}
		} else {
			//For properties to be mortgaged
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
	/**
	 * closes window
	 */
	public void cancel(){
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}
	@FXML
	private Button save;
	/**
	 * Saves changes for game
	 */
	public void save(){
		//Iterates through properties and applies changes to the rest of the game.
		if(type.equals("Build")){
			for(Property property : changedProperties.keySet()){
				Residential resProp = (Residential) property;
				if (changedProperties.get(property) == 0){
					//if there are now no houses
					Game.getCurrPlayer().changeMoney(resProp.getHouseCost() * resProp.getNumHouses());
				} else if (changedProperties.get(property) < resProp.getNumHouses()){
					// if there are fewer houses
					Game.getCurrPlayer().changeMoney((0.5)*resProp.getHouseCost() * (resProp.getNumHouses() - changedProperties.get(property)));
				} else if (changedProperties.get(property) > resProp.getNumHouses()) {
					// if there are now more houses
					Game.getCurrPlayer().changeMoney((-1)*resProp.getHouseCost() * (changedProperties.get(property) - resProp.getNumHouses()));
				}
				//Calls buildHouses on property that changed for number of houses
				resProp.buildHouse(changedProperties.get(property));
			}
		} else if(type.equals("Mortgage")){
			//Iterates through properties that were changed 
			for(Property property : changedProperties.keySet()){
				//Changes player's money based on whether or not the property was mortgaged/unMortgaged
				if(changedProperties.get(property) == 0){
					property.setIsMortgaged(false);
					Game.getCurrPlayer().changeMoney((-1)*property.getMortgageValue());
				} else {
					property.setIsMortgaged(true);
					Game.getCurrPlayer().changeMoney(property.getMortgageValue());
				}
			}
		}
		//Closes window
		Stage stage = (Stage) save.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private TextFlow warningArea;
}
