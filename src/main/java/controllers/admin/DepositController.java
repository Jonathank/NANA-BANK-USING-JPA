package controllers.admin;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.CheckingAccount;
import model.ClientView;
import model.Model;
import model.SavingsAccount;
import views.ClientDepositCellFactory;

public class DepositController implements Initializable{

	@FXML
	private Button btnsearch,btndeposit;
	@FXML
	private ListView<ClientView> resultLV;
	@FXML
	private ChoiceBox<String> choice;
	@FXML
	private TextField txtsearch,txtamount;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setcellfactory();
		choice.setItems(FXCollections.observableArrayList("savings","checking"));
		btnsearch.setOnAction(event -> onSearch(em));
		btndeposit.setOnAction(event -> onDeposit(em));
		
	}
	 
	private void onDeposit(EntityManager em) {
		try {
		if(choice.getValue().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE CHOOSE ACCOUNT", null);
			return;
		}
		if(txtsearch.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER PAYEEADDRESS", null);
			return;
		}
		if(txtamount.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER AMOUNT TO DEPOSIT", null);
			return;
		}
		//UPDATE BASING ON ACCOUNT TYPE AND PAYEEADDRESS
		if(choice.getValue().toString().equals("savings")) {
			Model.getInstance().updateBalance(em, txtsearch.getText(), new BigDecimal(txtamount.getText()), SavingsAccount.class);
		}
		
		if(choice.getValue().toString().equals("checking")) {
			Model.getInstance().updateBalance(em, txtsearch.getText(), new BigDecimal(txtamount.getText()), CheckingAccount.class);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		resultLV.getItems().clear();
		resultLV.setItems(Model.getInstance().LoadClientAccountDetails(em,txtsearch.getText()));
		showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "The transaction has been successfully processed.", null);
	}
	
	private void onSearch(EntityManager em) {
		if(txtsearch.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER PAYEEADDRESS", null);
			return;
		}
		
		if(Model.getInstance().CheckIfClientExists(em,txtsearch.getText())) {
	     try {
		// Load data from database
		resultLV.setItems(Model.getInstance().LoadClientAccountDetails(em,txtsearch.getText()));
		
	     }catch(Exception e){
			e.printStackTrace();
		}finally {
			//em.close();
			//EntityMangerFactoryRepo.shutdown();
		}
	     }
	}

	public void setcellfactory() {
		resultLV.setCellFactory(new Callback<ListView<ClientView>, ListCell<ClientView>>() {
            @Override
            public ListCell<ClientView> call(ListView<ClientView> listView) {
               
            	return new ClientDepositCellFactory();
            }
        });

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
