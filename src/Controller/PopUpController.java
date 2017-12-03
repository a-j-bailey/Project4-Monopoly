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
		//⌂
		//
		money = 0;
		popUpTitle.setText("Build House"); 
		type = "Build";
		houseChanger.setOpacity(1);
		houseChanger.setDisable(false);
		houseChanger.setValue("0 Houses");
		houseChanger.setItems(FXCollections.observableArrayList("0 Houses","1 House", "2 Houses", "3 Houses", "4 Houses", "Hotel"));
		houseChanger.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				System.out.println("Index: " + newValue);
				Residential resProperty = (Residential) thisProperty;
				int oldHouses = 0;
				int numHouses = (int) newValue;
				if(changedProperties.containsKey(thisProperty)){
					oldHouses = changedProperties.get(resProperty);
					changedProperties.put(resProperty, numHouses);
				} else {
					oldHouses = resProperty.getNumHouses();
					changedProperties.put(resProperty, numHouses);
				}
				if(numHouses < changedProperties.get(resProperty)){
					money -= (resProperty.getHouseCost() * (oldHouses - numHouses));
				} else {
					money += (resProperty.getHouseCost() * (numHouses - oldHouses));
				}
				cost.setText("$"+((-1)*money));
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
							thisProperty = (Residential) propList.get(i).get(j);
						}
					}
				}
				Residential resProp = (Residential) thisProperty;
				int numHouses = 0;
				if(changedProperties.containsKey(resProp)){
					numHouses = changedProperties.get(resProp);
				} else {
					numHouses = resProp.getNumHouses();
				}
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

	public void mortgageWindow(){
		popUpTitle.setText("Mortgage Houses");
		type = "Mortgage";
		mortgageCheck.setOpacity(1);
		mortgageCheck.setDisable(false);
		mortgageCheck.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				int val = -1;
				if(newValue){
					val = 1;
				} else {
					val = 0;
				}
				Property prop = thisProperty;
				changedProperties.put(prop, val);
								
				if(newValue){
					System.out.println(money);
					System.out.println(prop.getPropertyName());
					money += prop.getMortgageValue();
				} else {
					money -= prop.getMortgageValue();
				}
				cost.setText("$"+money);
			}
			
		});
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
				if(thisProperty.isMortgaged()){
					mortgageCheck.setSelected(true);
				} else {
					mortgageCheck.setSelected(false);
				}
		    }
		});
	}

	public ObservableList<String> loadPropertyList(String type){
		ObservableList<String> propList = FXCollections.<String>observableArrayList();
		HashMap<Integer, ArrayList<Property>> playerProperties = Game.getCurrPlayer().getProperties();
		if (type.equals("Build")){
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
	public void cancel(){
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}
	@FXML
	private Button save;
	public void save(){
		if(type.equals("Build")){
			for(Property property : changedProperties.keySet()){
				Residential resProp = (Residential) property;
				if (changedProperties.get(property) == 0){
					Game.getCurrPlayer().changeMoney(resProp.getHouseCost() * resProp.getNumHouses());
				} else if (changedProperties.get(property) < resProp.getNumHouses()){
					Game.getCurrPlayer().changeMoney(resProp.getHouseCost() * (resProp.getNumHouses() - changedProperties.get(property)));
				} else if (changedProperties.get(property) > resProp.getNumHouses()) {
					Game.getCurrPlayer().changeMoney((-1)*resProp.getHouseCost() * (changedProperties.get(property) - resProp.getNumHouses()));
				}
				resProp.buildHouse(changedProperties.get(property));
			}
		} else if(type.equals("Mortgage")){
			
		}
		
		Stage stage = (Stage) save.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private TextFlow warningArea;
}
