package views;

import controllers.client.TransactionCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import model.TransactionView;

public class TransactionCellFactory extends ListCell<TransactionView> {
	@Override
	protected void updateItem(TransactionView transaction, boolean empty) {
		super.updateItem(transaction, empty);
		if(empty) {
			setText(null);
			setGraphic(null);
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/TransactionCell.fxml"));
			TransactionCellController controller = new TransactionCellController(transaction);
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
