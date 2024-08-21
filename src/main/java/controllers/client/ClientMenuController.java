package controllers.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Model;
import views.ClientMenuOptions;

public class ClientMenuController implements Initializable {

	@FXML
	private Button btndash,btntrans,btnacct,btnprofile,btnlogout,btnreport;

	public void initialize(URL location, ResourceBundle resources) {
		addListeners();
	}

	private void addListeners() {
		btndash.setOnAction(event -> onDashboard());
		btntrans.setOnAction(event -> onTransaction());
		btnacct.setOnAction(event -> onAccount());
		btnprofile.setOnAction(event -> onProfile());
	    btnlogout.setOnAction(event -> onLogout());
	    btnreport.setOnAction(event -> onReport());
	}

	private void onReport() {
		Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.REPORT);
	}

	private void onLogout() {
        Stage stage =(Stage) btnlogout.getScene().getWindow();
		
		Model.getInstance().getViewFactory().closeStage(stage);
		Model.getInstance().getViewFactory().showLoginWindow();
	}

	private void onDashboard() {
		Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
	}
	private void onTransaction() {
		Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
	}
	private void onAccount() {
		Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
	}
	private void onProfile() {
		Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.PROFILE);
	}
}
