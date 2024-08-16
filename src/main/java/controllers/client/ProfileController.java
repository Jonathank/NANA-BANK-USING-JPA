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
import javafx.scene.control.Label;
import javafx.util.Duration;
import model.Account;
import model.CheckingAccount;
import model.Client;
import model.Model;
import model.SavingsAccount;

public class ProfileController implements Initializable {

	@FXML
	private Label payeeAdd,checkingNo,chk_bal,savingsNo,sav_bal,fname,lname,gender,dob;
	
	private String payeeAddress;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	public void setPayeeAddress(String payeeAddr) {
		this.payeeAddress = payeeAddr;
		if(payeeAddress != null) {
			payeeAdd.setText(payeeAddress);
			
			Client client = Model.getInstance().findClient(em, payeeAddress);
			fname.setText(client.getFname());
			lname.setText(client.getLname());
			gender.setText(client.getGender());
			dob.setText(client.getDateOfBirth().toString());
			//
			Account chk = Model.getInstance().getAccountObject(em, payeeAddress, CheckingAccount.class);
			checkingNo.setText(chk.getAccountNumber());
			chk_bal.setText(chk.getBalance().toString());
			Account sav = Model.getInstance().getAccountObject(em, payeeAddress, SavingsAccount.class);
			savingsNo.setText(sav.getAccountNumber());
			sav_bal.setText(sav.getBalance().toString());
		}
	}
	
	private void loadData() {
        // Load data from the database in a separate thread
        new Thread(() -> {
            try {
            	updateBalances();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void fetchData() {
    	 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
    	 loadData()),
         new KeyFrame(Duration.seconds(5)));
 clock.setCycleCount(Animation.INDEFINITE);
 clock.play();
        
    }
	public void updateBalances() {
		Account chk = Model.getInstance().getAccountObject(em, payeeAdd.getText(), CheckingAccount.class);
		chk_bal.setText(chk.getBalance().toString());
		Account sav = Model.getInstance().getAccountObject(em, payeeAdd.getText(), SavingsAccount.class);
		sav_bal.setText(sav.getBalance().toString());
	}
}
