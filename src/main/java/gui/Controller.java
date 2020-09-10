package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import handle.Chap;
import handle.Crawler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Controller implements Initializable {
	@FXML
	private Button intro, read, download;
//	@FXML
//	private Button readFromChap1, downloadAll;
	@FXML
	private TextField numberRead, numberDownload, newestChapter;
	@FXML
	private CheckBox checkBox;
	
	// giải pháp tình thế do chưa thể fix lỗi null pointer exception khi tranfer data giữa 2 controller
	public static String messageToReadScene;
	

	private static final String LINK_WEB = "https://truyentranh24.com/kimetsu-no-yaiba";
		
	
	public void initialize(URL location, ResourceBundle resources) {
		read.setDisable(true);
		download.setDisable(true);
		
		try {
			// là hash map nên khả năng không làm như bên dưới được
			// String number = Crawler.getAllChaps(LINK_WEB).get(0).getChapNumber();
			// Có thời gian thì viết 1 hàm tìm chap mới, hoặc viết 1 hàm trả về ArrayList<chap>
			String number = Crawler.findLastestChap(LINK_WEB).getChapNumber();
			newestChapter.setText("Thông báo: Chap mới nhất là chap " + number);

			
			numberRead.textProperty().addListener((obs, oldValue, newValue) -> {
				try {
					// có thể phát sinh lỗi ở đây, nếu newValue trống
					// double vì có chap 204.1
					double value = Double.parseDouble(newValue);
					double max = Double.parseDouble(number);
					if(value >= 1 && value <= max ) {
						read.setDisable(false);
					} else {
						read.setDisable(true);
					}
				} catch (Exception e) {
					read.setDisable(true);
				}
			});
			
			numberDownload.textProperty().addListener((obs, oldValue, newValue) -> {
				try {
					// có thể phát sinh lỗi ở đây, nếu newValue trống
					double value = Double.parseDouble(newValue);
					double max = Double.parseDouble(number);
					if(value >= 1 && value <= max ) {
						download.setDisable(false);
					} else {
					// phải có else
						download.setDisable(true);
					}
				} catch (Exception e) {
					download.setDisable(true);
				}
			});
			
		} catch (IOException e) {
			newestChapter.setText("Lỗi kết nối!");
			e.printStackTrace();
		}
	}
	
	public void clickIntro(ActionEvent event) {
		(new AllScene()).setIntroScene(event);
	}

	
	public void clickRead(ActionEvent event) throws IOException {
		messageToReadScene = numberRead.getText();
		(new AllScene()).setReadScene(event, numberRead.getText());
	}
	
//	public void clickReadFromChap1(ActionEvent event) {
//		(new AllScene()).setReadScene(event, "1");
//	}
	
	public void clickDownload(ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		String chapNum = numberDownload.getText();
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Lưu truyện KnY - Chapter " + chapNum);
		File selectedDirectory = chooser.showDialog(stage);
		
		Chap chap = Crawler.getAllChaps(LINK_WEB).get(chapNum);
		String path = selectedDirectory.getAbsolutePath();
		if(checkBox.isSelected()) {
			path += "//KnY_chap" + chapNum;
			(new File(path)).mkdir();
		}
		Crawler.saveChapter(chap.getUrl(), path);
	}
	
//	public void clickDownloadAll(ActionEvent event) throws IOException {
//		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//		DirectoryChooser chooser = new DirectoryChooser();
//		chooser.setTitle("Lưu truyện KnY - Trọn bộ");
//		File selectedDirectory = chooser.showDialog(stage);
//		
//		Crawler.saveManga(LINK_WEB, selectedDirectory.getAbsolutePath());
//	}
	
	// truyền dữ liệu giữa 2 controller nhưng không hiểu sao bị lỗi null pointer exception
//	public String transferMessageToReadScene() {
//		return numberRead.getText();
//	}	
}
