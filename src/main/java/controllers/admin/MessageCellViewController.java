package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Model;

public class MessageCellViewController implements Initializable {

	@FXML
	private Label status;
	@FXML
	private Text payee,type, date;
	@FXML
	private TextArea textarea;
	@FXML
	private AnchorPane pane;

	private final MessageView message;

	EntityManager em;
	public MessageCellViewController(MessageView mes) {
		this.message = mes;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once

		if (message.getMessages() != null) {
			textarea.setText(message.getMessages());
		}
		if (message.getPayeeAddress() != null) {
			payee.setText(message.getPayeeAddress());
		}
		if (message.getDateCreated() != null) {
			date.setText(message.getDateCreated().toString());
		}
		
		if (message.getMessageType()!= null) {
			type.setText(message.getMessageType());
		}
		
		 // Update the status label based on the message status
        updateStatusLabelStyle(message.getStatus());

        // Add a listener to update the style whenever the status changes
        status.textProperty().addListener((observable, oldValue, newValue) -> {
            updateStatusLabelStyle(newValue);
        });
        
     // Add mouse event handlers to the AnchorPane
		  pane.setOnMouseClicked(event -> { 
		  textarea.setPrefHeight(150);
		  pane.setPrefHeight(200);
		  });
		 
        pane.setOnMouseReleased(event -> {
            textarea.setPrefHeight(30);
            pane.setPrefHeight(85);
            Model.getInstance().updateStatus(em, message.getPayeeAddress(),message.getMessageType());
            updateStatusLabelStyle(message.getStatus());
        });
    }

    private void updateStatusLabelStyle(String statusValue) {
        if (statusValue.equals("New")) {
        	status.setText(statusValue);
            status.setStyle(
                "-fx-background-color: red; " + // Color for "New" status
                "-fx-background-radius: 50%; " + // Circular background
                "-fx-text-fill: white; " + // Text color
                "-fx-font-size: 14px; " + // Font size
                "-fx-alignment: center; " // Center text alignment
            );
        } else if(statusValue.equals("Viewed")){
        	status.setText(statusValue);
            status.setStyle(
                "-fx-background-color: #132A13; " + // Default color
                "-fx-background-radius: 50%; " + // Circular background
                "-fx-text-fill: white; " + // Text color
                "-fx-font-size: 14px; " + // Font size
                "-fx-alignment: center; " // Center text alignment
            );
        }
    }

}
