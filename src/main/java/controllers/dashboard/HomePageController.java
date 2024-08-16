package controllers.dashboard;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class HomePageController implements Initializable {

	@FXML
	private Label txtdate;
	
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTime();
       
    }

  

    // Set running time
    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                txtdate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss a")))),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

   
}
