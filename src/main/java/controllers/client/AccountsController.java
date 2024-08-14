package controllers.client;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.AccountView;
import model.CheckingAccount;
import model.Model;
import model.SavingsAccount;

public class AccountsController implements Initializable{

	@FXML
	private Button btnsavT,btnCheckT;
	@FXML
	private TextField txtCheck,txtsav;
	@FXML
	private Label trans_Limit,checkingNo,ch_date,ch_bal;
	@FXML
	private Label with_limit,saveNo,savdate,savBal;

	private String payeeAddress;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnsavT.setOnAction(event -> onTransferToChecking());
		btnCheckT.setOnAction(event -> onTransferToSavings());
	}

	private void onTransferToSavings() {
		try {
			if(txtCheck.getText().isEmpty()) {
				showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER AMOUNT", null);
				return;
			}
			Model.getInstance().deductSenderBalance(em, payeeAddress,new BigDecimal(txtCheck.getText()), CheckingAccount.class);
			Model.getInstance().updateBalance(em, payeeAddress, new BigDecimal(txtCheck.getText()), SavingsAccount.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//update UI
		updateAccountData();
		showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "The transaction has been successfully processed.", null);

	}

	private void onTransferToChecking() {
    try {
    	if(txtsav.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER AMOUNT", null);
			return;
		}
		Model.getInstance().deductSenderBalance(em, payeeAddress,new BigDecimal(txtsav.getText()), SavingsAccount.class);
		Model.getInstance().updateBalance(em, payeeAddress, new BigDecimal(txtsav.getText()), CheckingAccount.class);
	}catch(Exception e) {
		e.printStackTrace();
	}
	//update UI
	updateAccountData();
	showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "The transaction has been successfully processed.", null);

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
		if(payeeAddress != null) {
		updateAccountData();
		}
	}
	 //set accounts
    private void updateAccountData() {
        // Fetch the checking and savings accounts based on the payeeAddress
       AccountView checking = Model.getInstance().getAccountViewObject(em, payeeAddress, new String("checking"));
       AccountView savings = Model.getInstance().getAccountViewObject(em, payeeAddress, new String("savings"));
       
            if (checking != null) {
            	trans_Limit.setText(String.valueOf(checking.getTransaction_limit()));
            	checkingNo.setText(checking.getAccountNumber());
            	if(checking.getDateCreated().toString() != null) {
            	ch_date.setText(checking.getDateCreated().toString());
            	}
            	ch_bal.setText("Shs. "+checking.getBalance().toString());
            }
            if (savings != null) {
            	with_limit.setText(savings.getWithdrawLimit().toString());
            	saveNo.setText(savings.getAccountNumber());
            	if(savings.getDateCreated().toString() != null) {
            	savdate.setText(savings.getDateCreated().toString());
            	}
            	savBal.setText("Shs. "+savings.getBalance().toString());
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
}
