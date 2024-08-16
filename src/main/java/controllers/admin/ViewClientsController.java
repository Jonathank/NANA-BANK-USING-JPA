package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Duration;
import model.ClientView;
import model.Model;
import views.ClientCellFactory;

public class ViewClientsController implements Initializable{
//main view
	@FXML
	private ListView<ClientView> viewClientsLV;

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

	        // Initial load data from database
	        loadData();

	        fetchData();
			
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
             new KeyFrame(Duration.seconds(5)));
     clock.setCycleCount(Animation.INDEFINITE);
     clock.play();
	       
	    }

	    // Ensure EntityManager is closed when the controller is no longer needed
	    public void cleanup() {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
}
