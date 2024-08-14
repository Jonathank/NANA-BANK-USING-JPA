package views;

import controllers.client.TransactionClientCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import model.TransactionView;

public class TransactionClientCellFactory extends ListCell<TransactionView> {

	@Override
	protected void updateItem(TransactionView transaction, boolean empty) {
		super.updateItem(transaction, empty);
		if(empty) {
			setText(null);
			setGraphic(null);
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/TransactionClientCell.fxml"));
			TransactionClientCellController controller = new TransactionClientCellController(transaction);
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
