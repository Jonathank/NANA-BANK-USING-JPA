package views;

import controllers.admin.ClientWithdrawCellViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import model.ClientView;

public class ClientWithdrawCellFactory extends ListCell<ClientView> {

	@Override
	protected void updateItem(ClientView client, boolean empty) {
		super.updateItem(client, empty);
		if(empty) {
			setText(null);
			setGraphic(null);
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/ClientWithdrawCellView.fxml"));
			ClientWithdrawCellViewController controller = new ClientWithdrawCellViewController(client);
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
