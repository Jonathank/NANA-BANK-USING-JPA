package controllers.login;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Model;
import views.AccountType;

public class LoginController implements Initializable {

	@FXML
	private Button btnlogin;
	@FXML
	private Label txterror,userLabel;
	@FXML
	private TextField txtuser;
	@FXML
	private PasswordField txtpass;
	@FXML
	private ChoiceBox<AccountType> account_selector;
	 
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
		
	public void initialize(URL location, ResourceBundle resources) {
		account_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT,AccountType.ADMIN));
		account_selector.setValue(Model.getInstance().getViewFactory().getLoginType());
		account_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginType(account_selector.getValue()));
		btnlogin.setOnAction(event ->  onLogin());
		
		account_selector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	           if(Model.getInstance().getViewFactory().getLoginType() == AccountType.CLIENT) {
	        	   userLabel.setText("Payee Address");
	           }
	           else {
	        	   userLabel.setText("Username");
	           }
	        });
	}
	
	private void onLogin() {
		Stage stage =(Stage) btnlogin.getScene().getWindow();
		
		if(Model.getInstance().getViewFactory().getLoginType() == AccountType.CLIENT) {
		
		  if(txtuser.getText().isEmpty()) { 
			  showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER PAYEEADDRESS", null);
			  return; 
			  }
		  if(txtpass.getText().isEmpty()) { 
			  showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER PASSWORD", null);
			  return; 
			  }
		 
			if(Model.getInstance().validateCredentials(em,txtuser.getText(), txtpass.getText())) {

				Model.getInstance().getViewFactory().closeStage(stage);
				String payee = Model.getInstance().getClientPayeeAdress(em, txtuser.getText());
				Model.getInstance().getViewFactory().showClientWindow(payee);
				
			}else {
				txtuser.setText("");
				txtpass.setText("");
				txterror.setText("No Such Login Credentials.");
			}
			
		}else {
			userLabel.setText("Username");
			if(txtuser.getText().isEmpty()) { 
				  showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER USERNAME", null);
				  return; 
				  }
			  if(txtpass.getText().isEmpty()) { 
				  showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER PASSWORD", null);
				  return; 
				  }
			  String password = getEncryptedValue(txtpass.getText(), 8);
			if(Model.getInstance().validateAdminCredentials(em, txtuser.getText(), password)) {
			
				Model.getInstance().getViewFactory().closeStage(stage);
				Model.getInstance().getViewFactory().showAdminWindow();
			
			}
			}
	}
	
	
	  private String getEncryptedValue(String value, int secret_key) {
	  String encryptedValue = "";
	  
	  for(int i = 0; i < value.length(); i++) { char ch = value.charAt(i); ch +=
	  secret_key; encryptedValue = encryptedValue + ch; } return encryptedValue; }
	 
	//alert
	private void showAlert(AlertType type, String title, String header, String content) {
	    Alert alert = new Alert(type);
	    alert.setTitle(title);
	    alert.setHeaderText(header);
	    alert.setContentText(content);
	    alert.showAndWait();
	}
}
