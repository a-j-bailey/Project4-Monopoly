package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class PopUpController implements Initializable{
	
	private String type;
	
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
			
		}
	}
	
	@FXML
	private Label popUpTitle;
	@FXML
	private TreeView<String> propertyTree;
	@FXML
	private ChoiceBox<Integer> houseChanger;
	@FXML
	private Label cost;
	@FXML
	private Button cancel;
	@FXML
	private Button save;
	
}
