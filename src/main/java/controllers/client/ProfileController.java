package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import model.Account;
import model.CheckingAccount;
import model.Client;
import model.Model;
import model.SavingsAccount;

public class ProfileController implements Initializable {

	@FXML
	private Label checkingNo,chk_bal,savingsNo,sav_bal,fname,lname;
	@FXML
	private Text payeeAdd;
	private String payeeAddress;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
	public void setPayeeAddress(String payeeAddr) {
		this.payeeAddress = payeeAddr;
		if(payeeAddress != null) {
			payeeAdd.setText(payeeAddress);
			
			Client client = Model.getInstance().findClient(em, payeeAddress);
			fname.setText(client.getFname());
			lname.setText(client.getLname());
			//
			Account chk = Model.getInstance().getAccountObject(em, payeeAddress, CheckingAccount.class);
			checkingNo.setText(chk.getAccountNumber());
			chk_bal.setText(chk.getBalance().toString());
			Account sav = Model.getInstance().getAccountObject(em, payeeAddress, SavingsAccount.class);
			savingsNo.setText(sav.getAccountNumber());
			sav_bal.setText(sav.getBalance().toString());
		}
	}
	
	
}
