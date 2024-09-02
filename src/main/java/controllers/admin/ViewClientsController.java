package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.Duration;
import model.ClientView;
import model.Model;
import views.ClientCellFactory;
/**
 * @author KYEYUNE JONATHAN
 */
public class ViewClientsController implements Initializable{
//main view
	@FXML
	private ListView<ClientView> viewClientsLV;
	@FXML
	private Button btnsearch;
	@FXML
	private TextField txtsearch;

	EntityManager em = EntityMangerFactoryRepo.getEntityManager();
    
	 @Override
	    public void initialize(URL location, ResourceBundle resources) {
	        // Set custom cell factory
	        viewClientsLV.setCellFactory(new Callback<ListView<ClientView>, ListCell<ClientView>>() {
	            @Override
	            public ListCell<ClientView> call(ListView<ClientView> listView) {
	                return new ClientCellFactory();
	            }
	        });
	        
	     // Add a listener to the textProperty
	        txtsearch.textProperty().addListener(new ChangeListener<String>() {
	            @Override
	            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	                if(newValue != null) {
	    		         Platform.runLater(() -> {
	    		        	 viewClientsLV.getItems().clear();
	    			         viewClientsLV.setItems(Model.getInstance().loadClientViewDataFromDatabase(em,txtsearch.getText()));
	    		            });
	                }
	            }
	        });
	        
	        ObservableList<ClientView> clients = Model.getInstance().loadDataFromDatabase(em);
            // Update the ListView on the JavaFX Application Thread
            Platform.runLater(() -> {
            	 viewClientsLV.getItems().setAll(clients); // Update the ListView items
            });
            
	        // Initial load data from database
	        loadData();

	        fetchData();
			
	        btnsearch.setOnAction(event -> onSearch());
	    }

	    private void onSearch() {
	    	if(txtsearch.getText().isEmpty()) {
				showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER PAYEEADDRESS", null);
				return;
			}

			if(Model.getInstance().CheckIfClientExists(em,txtsearch.getText())) {
		     try {
		         Platform.runLater(() -> {
		        	 viewClientsLV.getItems().clear();
			         viewClientsLV.setItems(Model.getInstance().loadClientViewDataFromDatabase(em,txtsearch.getText()));
		            });
		        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
			}else {
				showAlert(AlertType.ERROR, "SOMETHING WENT WRONG", "CLIENT "+txtsearch.getText()+" DOES NOT EXIST!!", null);
				return;
			}
		
	}

		private void loadData() {
	        // Load data from the database in a separate thread
	        new Thread(() -> {
	        	ObservableList<ClientView> clients = Model.getInstance().loadDataFromDatabase(em);
	            // Update the ListView on the JavaFX Application Thread
	            Platform.runLater(() -> {
	                viewClientsLV.getItems().setAll(clients); // Update the ListView items
	            });
	        }).start();
	    }

	    private void fetchData() {
	    	 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
	    	 loadData()),
             new KeyFrame(Duration.seconds(7)));
     clock.setCycleCount(Animation.INDEFINITE);
     clock.play();
	       
	    }

	    // Ensure EntityManager is closed when the controller is no longer needed
	    public void cleanup() {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
	    
	  //alert
	  		private void showAlert(AlertType type, String title, String header, String content) {
	  		    Alert alert = new Alert(type);
	  		    alert.setTitle(title);
	  		    alert.setHeaderText(header);
	  		    alert.setContentText(content);
	  		    alert.showAndWait();
	  		}
}
