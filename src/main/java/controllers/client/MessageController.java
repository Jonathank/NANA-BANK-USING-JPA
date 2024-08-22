package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import model.Client;
import model.Message;

public class MessageController implements Initializable {

    @FXML
    private TextArea suggest, report;
    @FXML
    private Button btnsuggest,btnreport;

    private String payeeAddress;

    private EntityManager em;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        em = EntityMangerFactoryRepo.getEntityManager(); 
        btnsuggest.setOnAction(event -> onSuggest());
        btnreport.setOnAction(event -> onReport());
    }

    private void onReport() {
        if (report.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE WRITE YOUR BUG", null);
            return;
        }

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Client client = createClient();
            Message message = createMessage(report.getText(), "Bug", client);

            em.persist(message);
            transaction.commit();

            report.clear();
            showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "Bug SUBMITTED", null);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            showAlert(AlertType.ERROR, "TRANSACTION FAILED", "An error occurred while submitting the bug.", null);
        } finally {
            em.clear(); // Clear the persistence context
        }
    }

    private void onSuggest() {
        if (suggest.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "FIELD CAN'T BE EMPTY", "PLEASE WRITE YOUR SUGGESTION", null);
            return;
        }

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Client client = createClient();
            Message message = createMessage(suggest.getText(), "Suggestion", client);

            em.persist(message);
            transaction.commit();

            suggest.clear();
            showAlert(AlertType.INFORMATION, "TRANSACTION SUCCESSFUL", "SUGGESTION SUBMITTED", null);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            showAlert(AlertType.ERROR, "TRANSACTION FAILED", "An error occurred while submitting the suggestion.", null);
        } finally {
            em.clear(); // Clear the persistence context
        }
    }

    private Client createClient() {
        Client client = new Client();
        client.setPayeeAddress(payeeAddress);
        return client;
    }

    private Message createMessage(String text, String type, Client client) {
        Message message = new Message(text);
        message.setMessageType(type);
        message.setClient(client);
        return message;
    }

    private void showAlert(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }
}
