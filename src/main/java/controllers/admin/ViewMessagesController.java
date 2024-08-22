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
import model.Model;
import views.MessageCellFactory;

public class ViewMessagesController implements Initializable{
//main view
	@FXML
	private ListView<MessageView> viewmessages;

	EntityManager em;
    
	//TODO finish this modification tomorrow
	
	 @Override
	    public void initialize(URL location, ResourceBundle resources) {
		 em = EntityMangerFactoryRepo.getEntityManager();
	        // Set custom cell factory
		 viewmessages.setCellFactory(new Callback<ListView<MessageView>, ListCell<MessageView>>() {
	            @Override
	            public ListCell<MessageView> call(ListView<MessageView> listView) {
	                return new MessageCellFactory();
	            }
	        });
		 
		 loadData();
	     fetchData();
			
	    }

	    private void loadData() {
	        // Load data from the database in a separate thread
	        new Thread(() -> {
	        	ObservableList<MessageView> messages = Model.getInstance().loadMessageDataFromDatabase(em);
	            // Update the ListView on the JavaFX Application Thread
	            Platform.runLater(() -> {
	            	viewmessages.getItems().setAll(messages);
	            });
	        }).start();
	    }

	    private void fetchData() {
	    	 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
	    	 loadData()),
             new KeyFrame(Duration.seconds(3)));
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
