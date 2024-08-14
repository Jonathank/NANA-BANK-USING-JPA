package controllers.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import entitymanagerFactory.EntityMangerFactoryRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.ClientView;
import model.Model;
import views.ClientCellFactory;

public class ViewClientsController implements Initializable{
//main view
	@FXML
	private ListView<ClientView> viewClientsLV;

	static EntityManager em = EntityMangerFactoryRepo.getEntityManager();
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 // Set custom cell factory
		viewClientsLV.setCellFactory(new Callback<ListView<ClientView>, ListCell<ClientView>>() {
            @Override
            public ListCell<ClientView> call(ListView<ClientView> listView) {
                return new ClientCellFactory();
            }
        });

        // Load data from database
		viewClientsLV.setItems(Model.getInstance().loadDataFromDatabase(em));
	}
	
	
}
