package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerIntro {
	@FXML
	private Button returnMainScene;
	
	public void clickReturnMainScene(ActionEvent event) {
		(new AllScene()).setMainScene(event);
	}

}
