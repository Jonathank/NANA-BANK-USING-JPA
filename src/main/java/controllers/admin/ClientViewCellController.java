package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.ClientView;
import model.Model;

public class ClientViewCellController implements Initializable{
	
	@FXML
	private Label fname,lname,date,Acc_type,acc_no,paddress;
	@FXML
	private Button btndelete;
	
	private final ClientView client;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager();
	
	public ClientViewCellController(ClientView client) {
		this.client = client;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    if (client.getFname()!= null) {
	        fname.setText(client.getFname());
	    }
	    if (client.getLname()!= null) {
	        lname.setText(client.getLname());
	    }
	    if (client.getDateCreated() != null) {
	        date.setText(client.getDateCreated().toString());
	    }
	    if (client.getPayeeAddress() != null) {
	        paddress.setText(client.getPayeeAddress());
	    }
	    if(client.getAccountType() != null) {
	    	Acc_type.setText(client.getAccountType());
	    }
	    if(client.getAccountNumber() != null) {
	    	acc_no.setText(client.getAccountNumber());
	    }
	   
	    btndelete.setOnAction(event -> onDelete(em,client.getPayeeAddress()));
	}

	private void onDelete(EntityManager em,String payeeAddress) {
		try {
			Model.getInstance().deleteClient(em, payeeAddress);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
