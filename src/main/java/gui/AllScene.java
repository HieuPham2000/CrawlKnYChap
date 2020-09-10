package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AllScene {
	public void setReadScene(ActionEvent event, String chapNum) {
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("GuiRead.fxml"));
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("GuiRead.css").toExternalForm());
			
			stage.setTitle("Đọc truyện KnY  - Chapter " + chapNum);
			stage.setScene(scene);
			
			// set màn hình
//			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//			stage.setX(primaryScreenBounds.getMinX());
//			stage.setY(primaryScreenBounds.getMinY());
//			stage.setMaxWidth(primaryScreenBounds.getWidth());
//			stage.setMinWidth(primaryScreenBounds.getWidth());
//			stage.setMaxHeight(primaryScreenBounds.getHeight());
//			stage.setMinHeight(primaryScreenBounds.getHeight());
			
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setIntroScene(ActionEvent event) {
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("GuiIntro.fxml"));
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("GuiIntro.css").toExternalForm());
			
			stage.setTitle("Truyện tranh Kimetsu no Yaiba - Giới thiệu");
			stage.setScene(scene);
			stage.setResizable(false); 
			
			stage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setMainScene(ActionEvent event) {
		try {
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
			Scene scene = new Scene(root);
			

			scene.getStylesheets().add(getClass().getResource("Gui.css").toExternalForm());
			
			stage.setTitle("Truyện tranh Kimetsu no Yaiba - Lưỡi gươm diệt quỷ");
			stage.setScene(scene);
			stage.setResizable(false); 
			
			stage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
