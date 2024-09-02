package controllers.dashboard;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Account;
import model.CheckingAccount;
import model.Client;
import model.Model;
import model.SavingsAccount;
import model.Transaction;
import model.TransactionView;
import views.TransactionCellFactory;
/**
 * @author KYEYUNE JONATHAN
 */
public class DashBoardController implements Initializable {

	@FXML
	private Button btnsend;
	@FXML
	private Label txtdate,check_bal,check_acc_no,saving_bal,saving_acc_no,
	txtincome,txtexpense,txtusername;
	@FXML
	private TextField txtpayee,txtamount;
	@FXML
	private ListView<TransactionView> transaction_listview;
	@FXML
	private TextArea txtmessage;
	
	private String payeeAddress;
	
	EntityManager em = EntityMangerFactoryRepo.getEntityManager(); // Open EntityManager once
	
	// TODO transaction limit
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set custom cell factory
        transaction_listview.setCellFactory(new Callback<ListView<TransactionView>, ListCell<TransactionView>>() {
            @Override
            public ListCell<TransactionView> call(ListView<TransactionView> listView) {
                return new TransactionCellFactory();
            }
        });

        // Button send
        btnsend.setOnAction(event -> onSend());
        setTime();
        
        fetchData();
    }

    public void onSend() {
        if (Model.getInstance().CheckIfClientExists(em, txtpayee.getText())) {
            BigDecimal amount = new BigDecimal(txtamount.getText());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert(AlertType.ERROR, "SOMETHING WENT WRONG", "AMOUNT MUST BE GREATER THAN ZERO", null);
                return;
            }
            try {
                // Updates sender's balance - amount
                Model.getInstance().deductSenderBalance(em, payeeAddress, amount, CheckingAccount.class);
                // Updates receiver's balance
                Model.getInstance().updateBalance(em, txtpayee.getText(), amount, CheckingAccount.class);

                em.getTransaction().begin(); // Start the transaction
                Client client = new Client();
                client.setPayeeAddress(payeeAddress);
                Transaction transaction = new Transaction(txtpayee.getText(), amount.doubleValue(), txtmessage.getText());
                transaction.setClient(client);
                em.persist(transaction);

                em.getTransaction().commit(); // Commit transaction
                em.clear();

                // Clear input fields
                txtpayee.clear();
                txtamount.clear();
                txtmessage.clear();

                showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "The transaction has been successfully processed.", null);

            } catch (Exception e) {
                em.getTransaction().rollback(); // Rollback transaction
                e.printStackTrace();
                showAlert(AlertType.ERROR, "TRANSACTION FAILED", "An error occurred while processing the transaction. Please try again.", null);
            }
        } else {
            showAlert(AlertType.ERROR, "SOMETHING WENT WRONG", "CLIENT DOES NOT EXIST", null);
        }
        //update balance 
        updateAccountBalances();
        //update list view
        transaction_listview.getItems().clear();
        transaction_listview.setItems(Model.getInstance().loadTransactionDataLimit(em, txtusername.getText(), txtusername.getText()));
       
    }

    // Set running time
    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                txtdate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss a")))),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //set accounts
    private void updateAccountData() {
        // Fetch the checking and savings accounts based on the payeeAddress
       Account checking = Model.getInstance().getAccountObject(em, payeeAddress, CheckingAccount.class);
       Account savings = Model.getInstance().getAccountObject(em, payeeAddress, SavingsAccount.class);
       
            if (checking != null) {
                check_bal.setText(checking.getBalance().toString());
                check_acc_no.setText(checking.getAccountNumber());
            }
            if (savings != null) {
                saving_bal.setText(savings.getBalance().toString());
                saving_acc_no.setText(savings.getAccountNumber());
            }
    }

    // Alert
    private void showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setPayeeAddress(String payeeAdd) {
        this.payeeAddress = payeeAdd;

        if (payeeAddress != null && !payeeAddress.trim().isEmpty()) {
                txtusername.setText(payeeAddress);
             // Update UI with the initial account data and list view
                transaction_listview.setItems(Model.getInstance().loadTransactionDataLimit(em, payeeAddress, payeeAddress));
                updateAccountData();
        } 
    }

    private void loadData() {
        // Load data from the database in a separate thread
        new Thread(() -> {
            try {
                // Fetch data from the model
                updateAccountBalances();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void fetchData() {
    	 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
    	 loadData()),
         new KeyFrame(Duration.seconds(7)));
 clock.setCycleCount(Animation.INDEFINITE);
 clock.play();
        
    }

    private void updateAccountBalances() {
        Platform.runLater(() -> {
            try {
                // Fetch checking and savings accounts
                Account checking = Model.getInstance().getAccountObject(em, txtusername.getText(), CheckingAccount.class);
                Account savings = Model.getInstance().getAccountObject(em, txtusername.getText(), SavingsAccount.class);

                // Log fetched account data for debugging
                if (checking != null) {
                    check_bal.setText(checking.getBalance().toString());
                    System.out.println("Checking Balance: " + checking.getBalance());
                } else {
                    System.err.println("No checking account found for user: " + txtusername.getText());
                }

                if (savings != null) {
                    saving_bal.setText(savings.getBalance().toString());
                    System.out.println("Savings Balance: " + savings.getBalance());
                } else {
                    System.err.println("No savings account found for user: " + txtusername.getText());
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exceptions appropriately
            }
        });
    }

}
