package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Model;
import views.AdminMenuOptions;

public class AdminMenuController implements Initializable {

	@FXML
	private Button btnAccType,btnwithd,btncreateC,btnclients,btndepo,btnlogout;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AddListeners();
	}

	private void AddListeners() {
		btncreateC.setOnAction(event -> onCreateClient());
		btnclients.setOnAction(event -> onViewClient());
		btndepo.setOnAction(event -> onDeposit());
		btnAccType.setOnAction(event -> onAddAccountType());
		btnwithd.setOnAction(event -> onWithdraw());
		btnlogout.setOnAction(event -> onLogout());
	}
	
	private void onLogout() {
		Stage stage =(Stage) btnlogout.getScene().getWindow();
		
		Model.getInstance().getViewFactory().closeStage(stage);
		Model.getInstance().getViewFactory().showLoginWindow();
	}

	private void onWithdraw() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.WITHDRAW);
	}

	private void onAddAccountType() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADDACCOUNT_TYPE);
	}

	private void onDeposit() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DEPOSIT);
	}

	private void onViewClient() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CLIENTS);
	}

	private void onCreateClient() {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
	}
}
