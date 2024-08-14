package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.ClientView;

public class ClientWithdrawCellViewController implements Initializable{

	@FXML
	private Label fname,lname,paddress,Acc_type,acc_no,balance;
	
	private final ClientView client;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		  if (client.getFname()!= null) {
		        fname.setText(client.getFname());
		    }
		    if (client.getLname()!= null) {
		        lname.setText(client.getLname());
		    }
		    if (client.getPayeeAddress() != null) {
		        paddress.setText(client.getPayeeAddress());
		    }
		    if (client.getAccountType() != null) {
		        Acc_type.setText(client.getAccountType());
		    }
		    if (client.getAccountNumber() != null) {
		    	acc_no.setText(client.getAccountNumber());
		    }
		    if (client.getBalance() != null) {
		       balance.setText(client.getBalance().toString());
		    }
	}
	

	public ClientWithdrawCellViewController(ClientView client) {
		this.client = client;
	}

}
