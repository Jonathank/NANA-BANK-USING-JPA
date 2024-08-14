package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import model.Model;

public class ClientController implements Initializable{

	@FXML
	private BorderPane client_form;
	
	private String payeeAddress;
	
	 @Override
	    public void initialize(URL location, ResourceBundle resources) {
	        
			  if (payeeAddress != null) {
			  client_form.setCenter(Model.getInstance().getViewFactory().getDashboardView(payeeAddress));
			  } else {
				  System.out.println("Payee address is not set.");
				  }
		
	        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
	            switch (newVal) {
	                case TRANSACTIONS:
	                    client_form.setCenter(Model.getInstance().getViewFactory().getTransactionview(payeeAddress));
	                    break;
	                case ACCOUNTS:
	                    client_form.setCenter(Model.getInstance().getViewFactory().getAccountsview(payeeAddress));
	                    break;
	                case DASHBOARD :
	                    client_form.setCenter(Model.getInstance().getViewFactory().getDashboardView(payeeAddress));
	                    break;
	                case PROFILE :
	                	client_form.setCenter(Model.getInstance().getViewFactory().getProfile(payeeAddress));
	                	break;
	            }
	        });
	    }

	    public String getPayeeAddress() {
	        return payeeAddress;
	    }

	    public void setPayeeAddress(String payeeAddress) {
	        this.payeeAddress = payeeAddress;
	    }
}
