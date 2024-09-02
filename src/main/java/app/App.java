package app;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;

/**
 * @author KYEYUNE JONATHAN
 */
public class App extends Application {

	@Override
	public void start(Stage stage)  {
		Model.getInstance().getViewFactory().showLoginWindow();
	}

}
