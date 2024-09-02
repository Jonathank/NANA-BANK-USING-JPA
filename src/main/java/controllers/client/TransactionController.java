package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Model;
import model.TransactionView;
import views.TransactionClientCellFactory;
/**
 * @author KYEYUNE JONATHAN
 */
public class TransactionController implements Initializable{

	@FXML
	private ListView<TransactionView>tlistview;
	
	private String payeeAddress;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tlistview.setCellFactory(new Callback<ListView<TransactionView>, ListCell<TransactionView>>() {
	            @Override
	            public ListCell<TransactionView> call(ListView<TransactionView> listView) {
	                return new TransactionClientCellFactory();
	            }
	        });
		
		fetchData();
		
	}
	/**
	 * @return the payeeAddress
	 */
	public String getPayeeAddress() {
		return payeeAddress;
	}
	/**
	 * @param payeeAddress the payeeAddress to set
	 */
	public void setPayeeAddress(String payeeAddress) {
		this.payeeAddress = payeeAddress;
		if (payeeAddress != null && !payeeAddress.trim().isEmpty()) {
            
			tlistview.setItems(Model.getInstance().loadTransactionData(em, payeeAddress, payeeAddress));
    } 
	}
	
	 private void loadData() {
	        // Load data from the database in a separate thread
	        new Thread(() -> {
	            try {
	                // Fetch data from the model
	            	tlistview.setItems(Model.getInstance().loadTransactionData(em, payeeAddress, payeeAddress));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }).start();
	    }

	    private void fetchData() {
	    	 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
	    	 loadData()),
	         new KeyFrame(Duration.seconds(4)));
	 clock.setCycleCount(Animation.INDEFINITE);
	 clock.play();
	        
	    }

}
