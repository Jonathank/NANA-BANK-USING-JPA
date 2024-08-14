package views;

import controllers.admin.ClientDepositCellViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import model.ClientView;

public class ClientDepositCellFactory extends ListCell<ClientView>  {

	@Override
	protected void updateItem(ClientView client, boolean empty) {
		super.updateItem(client, empty);
		if(empty) {
			setText(null);
			setGraphic(null);
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/ClientDepositCellView.fxml"));
			ClientDepositCellViewController controller = new ClientDepositCellViewController(client);
			loader.setController(controller);
			setText(null);
			try {
				setGraphic(loader.load());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
