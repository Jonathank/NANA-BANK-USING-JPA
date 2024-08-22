package views;

import controllers.admin.MessageCellViewController;
import controllers.admin.MessageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class MessageCellFactory extends ListCell<MessageView> {

	@Override
	protected void updateItem(MessageView message, boolean empty) {
		super.updateItem(message, empty);
		if(empty) {
			setText(null);
			setGraphic(null);
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/MessageViewCell.fxml"));
			MessageCellViewController controller = new MessageCellViewController(message);
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
