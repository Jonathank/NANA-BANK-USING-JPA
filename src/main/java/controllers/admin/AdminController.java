package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import model.Model;

public class AdminController implements Initializable{

	@FXML
	private BorderPane Admin_form;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observable, oldVal, newVal)->{
			switch(newVal) {
			case CLIENTS:
				Admin_form.setCenter(Model.getInstance().getViewFactory().getClientView());
				break;
			case DEPOSIT :
				Admin_form.setCenter(Model.getInstance().getViewFactory().getDepositView());
				break;
			case WITHDRAW :
				Admin_form.setCenter(Model.getInstance().getViewFactory().getWithdrawView());
				break;
			case ADDACCOUNT_TYPE :
				Admin_form.setCenter(Model.getInstance().getViewFactory().getAddAccountType());
				break;
			default :
				Admin_form.setCenter(Model.getInstance().getViewFactory().getCreateClientview());
				break;
			}
		});
	}

}
