package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import model.Client;
import model.Message;

public class MessageController implements Initializable {

	@FXML
	private TextArea suggest,report;
	@FXML
	private Button btnsuggest,btnreport;
	
	private String payeeAddress;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnsuggest.setOnAction(event -> onSuggest());
		btnreport.setOnAction(event -> onReport());
	}

	private void onReport() {
		if(report.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE WRITE YOUR BUG", null);
			return;
		}
		em.getTransaction().begin();
	    try {
	        
	        Client client = new Client();
	        client.setPayeeAddress(payeeAddress);
	        
	        Message message = new Message(report.getText());
	        message.setMessageType("Bug");
	       
	        message.setClient(client);
	        em.persist(message); 
	            
	        em.getTransaction().commit();
	        
	        report.clear();
	        showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "Bug SUBMITTED", null);
			
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
	}

	private void onSuggest() {
		if(suggest.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE WRITE YOUR SUGGESTION", null);
			return;
		}
		em.getTransaction().begin();
	    try {
	        
	        Client client = new Client();
	        client.setPayeeAddress(payeeAddress);
	        
	        Message message = new Message(suggest.getText());
	        message.setMessageType("Suggestion");
	       
	        message.setClient(client);
	        em.persist(message); 
	            
	        em.getTransaction().commit();
	        
	        suggest.clear();
	        showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "SUGGESTION SUBMITTED", null);
	        
	    } catch (Exception e) {
	       
	        e.printStackTrace();
	    } finally {
	        
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

	/**
	 * @return the payeeAddress
	 */
	public String getPayeeAddress() {
		return payeeAddress;
	}

	/**
	 * @param payeeAddress the payeeAddress to set
	 */
	public void setPayeeAddress(String payeeAdd) {
		this.payeeAddress = payeeAdd;
	}
	
	
}
