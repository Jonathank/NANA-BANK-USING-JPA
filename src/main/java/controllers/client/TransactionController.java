package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Model;
import model.TransactionView;
import views.TransactionClientCellFactory;

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

}
