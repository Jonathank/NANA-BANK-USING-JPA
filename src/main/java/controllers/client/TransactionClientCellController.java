package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.TransactionView;

public class TransactionClientCellController implements Initializable {

	@FXML
	private FontAwesomeIconView incoming,outgoing;
	@FXML
	private Label Tdate,txtsender,txtreciever,txtamount;

	private String payeeaddress;
	
	private final TransactionView transaction;
	
	public TransactionClientCellController(TransactionView transaction) {
		this.transaction = transaction;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (transaction.getDateCreated() != null) {
	        Tdate.setText(transaction.getDateCreated().toString());
	    }
		if (transaction.getSender() != null) {
			txtsender.setText(transaction.getSender());
	    }
		if (transaction.getReciever() != null) {
	        txtreciever.setText(transaction.getReciever());
	    }
		if (transaction.getAmount() != null) {
	        txtamount.setText(transaction.getAmount().toString());
	    }
	}
	/**
	 * @return the payeeaddress
	 */
	public String getPayeeaddress() {
		return payeeaddress;
	}
	/**
	 * @param payeeaddress the payeeaddress to set
	 */
	public void setPayeeaddress(String payeeaddress) {
		this.payeeaddress = payeeaddress;
	}
	
	

}
