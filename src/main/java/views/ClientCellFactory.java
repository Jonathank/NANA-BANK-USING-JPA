package views;

import controllers.admin.ClientViewCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import model.ClientView;

public class ClientCellFactory extends ListCell<ClientView> {

	@Override
	protected void updateItem(ClientView client, boolean empty) {
		super.updateItem(client, empty);
		if(empty) {
			setText(null);
			setGraphic(null);
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/ClientViewCell.fxml"));
			ClientViewCellController controller = new ClientViewCellController(client);
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
