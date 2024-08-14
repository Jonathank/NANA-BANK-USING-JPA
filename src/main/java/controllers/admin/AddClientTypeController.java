package controllers.admin;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CheckingAccount;
import model.Client;
import model.ClientView;
import model.Model;
import model.SavingsAccount;

public class AddClientTypeController implements Initializable{

	@FXML
	private Button btnupdate,btnclear,btnsearch;
	@FXML
	private TextField chk_bal,saving_bal, txtsearch;
	@FXML
	private Label chk_no,sav_no,txtfname,txtlname,txtpayee,txtgender;
	@FXML
	private CheckBox check_chk,check_sav;
	@FXML
	private TableView<ClientView> table;
	@FXML
	private TableColumn<ClientView,String>cp;
	@FXML
	private TableColumn<ClientView,String>cfn;
	@FXML
	private TableColumn<ClientView,String>cln;
	@FXML
	private TableColumn<ClientView,String>cat;
	@FXML
	private TableColumn<ClientView,String>can;
	@FXML
	private TableColumn<ClientView,String>cb;
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtsearch.textProperty().addListener((observable, oldValue, newValue) -> addResetListener(txtsearch));
		btnsearch.setOnAction(event -> onSearch(em));
		btnclear.setOnAction(event -> onClear());
		btnupdate.setOnAction(event -> onUpdate(em));
		updateTable(em);
		addListenersToCheckboxes();
	}

	private void onSearch(EntityManager em) {
		if(txtsearch.getText().isEmpty()) {
			showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE ENTER PAYEEADDRESS", null);
			return;
		}

		if(Model.getInstance().CheckIfClientExists(em,txtsearch.getText())) {
	     try {
		Client client = Model.getInstance().findClient(em,txtsearch.getText());
		txtfname.setText(client.getFname());
		txtlname.setText(client.getLname());
		txtpayee.setText(client.getPayeeAddress());
		txtgender.setText(client.getGender());
		table(em);
	     
		
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
      //  em.close(); // Ensure EntityManager is closed
        
    }
		}else {
			showAlert(AlertType.ERROR, "SOMETHING WENT WRONG", "CLIENT "+txtsearch.getText()+" DOES NOT EXIST!!", null);
			return;
		}
	}
	
	//button update
private void onUpdate( EntityManager em) {
	
     em.getTransaction().begin(); // Begin transaction

    try {
        // Create the client entity
        Client client = new Client();
        client.setPayeeAddress(txtpayee.getText());	

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
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); // Rollback on error
        }
        e.printStackTrace();
    } finally {
      //  emg.close(); // Ensure EntityManager is closed
       // EntityMangerFactoryRepo.shutdown(); // Shutdown factory after all operations
    }
	}
	
	

//updates table on new changes
	public void updateTable( EntityManager em) {
		if(!txtsearch.getText().isEmpty()) {
			table(em);
			//em1.close();
			//EntityMangerFactoryRepo.shutdown();
			}
	}
	
	//loads client data to table view
	public void table(EntityManager em ) {
		try {
			table.getItems().clear();
			table.setItems(Model.getInstance().LoadClientAccountDetails(em,txtsearch.getText()));
	
			cp.setCellValueFactory(new PropertyValueFactory<>("payeeAddress"));
			cfn.setCellValueFactory(new PropertyValueFactory<>("fname"));
			cln.setCellValueFactory(new PropertyValueFactory<>("lname"));
			cat.setCellValueFactory(new PropertyValueFactory<>("AccountType"));
			can.setCellValueFactory(new PropertyValueFactory<>("AccountNumber"));
			cb.setCellValueFactory(new PropertyValueFactory<>("balance"));
			
		}catch(Exception e) {
			
		}
	}
	
	private void addListenersToCheckboxes() {
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
	}
	
	//alert
		private void showAlert(AlertType type, String title, String header, String content) {
		    Alert alert = new Alert(type);
		    alert.setTitle(title);
		    alert.setHeaderText(header);
		    alert.setContentText(content);
		    alert.showAndWait();
		}
		
	//clears all fields once search field id cleared/empty
	private void addResetListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
            	table.getItems().clear();
            	txtfname.setText("");
        		txtlname.setText("");
        		txtpayee.setText("");
        		txtgender.setText("");
        		check_chk.setSelected(false);
        		check_sav.setSelected(false);
        		saving_bal.setText("");
        		chk_bal.setText("");
        		chk_no.setText("");
        		sav_no.setText("");
            }
        });
    }
	
	//clear button
	private void onClear() {
		table.getItems().clear();
		txtsearch.setText("");
    	txtfname.setText("");
		txtlname.setText("");
		txtpayee.setText("");
		txtgender.setText("");
		check_chk.setSelected(false);
		check_sav.setSelected(false);
		saving_bal.setText("");
		chk_bal.setText("");
		chk_no.setText("");
		sav_no.setText("");
	}
}
