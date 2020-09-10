package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("Gui.css").toExternalForm());
			
			primaryStage.setTitle("Truyện tranh Kimetsu no Yaiba - Lưỡi gươm diệt quỷ");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			// để icon ở gui thì không lỗi, để chỗ khác và dẫn địa chỉ tuyệt đối lại lỗi???
			primaryStage.getIcons().add(new Image("/gui/icon.jpg"));
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
