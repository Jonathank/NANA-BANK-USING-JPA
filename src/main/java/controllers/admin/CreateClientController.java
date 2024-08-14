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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.CheckingAccount;
import model.Client;
import model.Model;
import model.SavingsAccount;

public class CreateClientController implements Initializable {

	@FXML
	private Button btncreate,btnclear;
	@FXML
	private TextField txtfname,txtlname,txtpass,txtconfirmpass,saving_bal,chk_bal;
	@FXML
	private CheckBox checkpayee,check_chk,check_sav;
	@FXML
	private Label sav_no,labelpayee,error,chk_no;
	@FXML
	private ComboBox<String>combogender;
	@FXML
	private DatePicker datepicker;
	
	 EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		combogender.setItems(FXCollections.observableArrayList("MALE","FEMALE"));
		addListeners();
		btncreate.setOnAction(event -> onCreateClient(em));
		btnclear.setOnAction(event -> onClear());
	}
private void onClear() {
	txtfname.setText("");
	txtlname.setText("");
	txtpass.setText("");
	txtconfirmpass.setText("");
	saving_bal.setText("");
	chk_bal.setText("");
	checkpayee.setSelected(false);
	check_chk.setSelected(false);
	check_sav.setSelected(false);
	sav_no.setText("");
	labelpayee.setText("");
	error.setText("");
	chk_no.setText("");
	combogender.setValue(null);
	datepicker.setValue(null);
	}
	//create client
	private void onCreateClient(EntityManager em) {
	    checkNullFields();
	    
	    	if (!txtpass.getText().equals(txtconfirmpass.getText())) {
	            // Set borders to red
	            setTextFieldBorderColor(txtpass, Color.RED);
	            setTextFieldBorderColor(txtconfirmpass, Color.RED);
	            error.setText("Passwords do not match!");
	        } else {
	            // Reset borders if they match
	        	setTextFieldBorderColorONDELETE(txtpass);
	        	setTextFieldBorderColorONDELETE(txtconfirmpass);
	            error.setText("");
	            
	     
	     em.getTransaction().begin(); // Begin transaction

	    try {
	        // Create the client entity
	        Client client = new Client(txtfname.getText(), txtlname.getText(), 
	                                    combogender.getValue().toString(), 
	                                    datepicker.getValue().toString(), 
	                                    txtpass.getText());

	        // Persist the client first to ensure it's managed
	        em.persist(client);

	        // Check if a savings account needs to be created
	        if (check_sav.isSelected()) {
	            SavingsAccount myaccount = new SavingsAccount(sav_no.getText(), 
	                                new BigDecimal(saving_bal.getText()), 
	                                BigDecimal.valueOf(2000.0));
	            // Set the client and persist
	            myaccount.setClient(client);
	            em.persist(myaccount); // Persist account
	        }

	        // Check if a checking account needs to be created
	        if (check_chk.isSelected()) {
	            CheckingAccount myaccount = new CheckingAccount(chk_no.getText(), 
	                                new BigDecimal(chk_bal.getText()), 5);
	            // Set the client and persist
	            myaccount.setClient(client);
	            em.persist(myaccount); // Persist account
	        }

	        em.getTransaction().commit(); // Commit transaction
	    } catch (Exception e) {
	       
	        e.printStackTrace();
	    } finally {
	        //em.close(); // Ensure EntityManager is closed
	      //  EntityMangerFactoryRepo.shutdown(); // Shutdown factory after all operations
	    }
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
	
	private void checkNullFields() {
		if(txtfname.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER FIRST-NAME", null);
			return;
		}
		if(txtlname.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER LAST-NAME", null);
			return;
		}
		if(txtpass.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE SET PASSWORD", null);
			return;
		}
		if(txtconfirmpass.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE CONFIRM PASSWORD", null);
			return;
		}
		if(combogender.getValue()==null) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE SELECT GENDER", null);
			return;
		}
		
		if(datepicker.getValue()==null) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER DATE OF BIRTH", null);
			return;
		}
		
	}
	
	//listeners for check boxes
	public void addListeners() {
		 checkpayee.selectedProperty().addListener((observable, oldValue, newValue) -> {
			 if (checkpayee.isSelected() && txtfname.getText().isEmpty()) {
				 checkpayee.setSelected(false);  
				 showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER FIRST-NAME", null);
			        return;
			    }
			    if (checkpayee.isSelected() && txtlname.getText().isEmpty()) {
			    	checkpayee.setSelected(false);
			        showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER LAST-NAME", null);
			        return;
			    }
			    
			 // Generate payee address only if both names are non-empty
			 if(checkpayee.isSelected() && (!txtfname.getText().isEmpty() && !txtlname.getText().isEmpty())) {
		            String payeeAdd = Model.getInstance().generatePayeeAddress(txtfname.getText(), txtlname.getText());
		            labelpayee.setText(newValue ? payeeAdd : "");
		        } else {
		        	
		            labelpayee.setText(""); // Clear if the checkbox is unchecked or fields are empty
		        }
		    });
		 //checking account check box
	    check_chk.selectedProperty().addListener((observable, oldValue, newValue) -> {
	    	 if(check_chk.isSelected()) {
			        	chk_no.setText(Model.getInstance().generateAccountNumber("245"));
			        } else {
			        	chk_no.setText(""); // Clear if the checkbox is unchecked or fields are empty
			        }
	    });
	    //savings Account check box
	    check_sav.selectedProperty().addListener((observable, oldValue, newValue) -> {
	    	 if(check_sav.isSelected()) {
	    		 sav_no.setText(Model.getInstance().generateAccountNumber("255"));
			        } else {
			        	sav_no.setText(""); // Clear if the checkbox is unchecked or fields are empty
			        }
	    });
	    
	    //text fields
	    txtpass.textProperty().addListener((observable, oldValue, newValue) -> addResetListener(txtpass));
        txtconfirmpass.textProperty().addListener((observable, oldValue, newValue) -> addResetListener(txtconfirmpass));
		
	}//end listeners
	
    private void setTextFieldBorderColor(TextField textField, Color color) {
        textField.setStyle("-fx-border-color: " + toHexString(color) + "; -fx-border-width: 2;");
    }
    private void setTextFieldBorderColorONDELETE(TextField textField) {
        textField.setStyle("-fx-pref-width:250;\r\n"
        		+ "	-fx-pref-height:35;\r\n"
        		+ "	-fx-background-color:#FFFFFF;\r\n"
        		+ "	-fx-font-size:1.3em;-fx-effect:dropshadow(three-pass-box, #AAAAAA,3,0,0,3);");
    }
    private void addResetListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
            	setTextFieldBorderColorONDELETE(textField); // Reset to default when empty
            	error.setText("");
            }
        });
    }

   
    private String toHexString(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

}
