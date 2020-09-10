package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ControllerRead implements Initializable {
	@FXML
	private Button nextImg, preImg, nextChap, preChap, returnMainScene, find;
	@FXML
	private Label numberImg, numberChap;
	@FXML
	private ImageView imageView;
	@FXML
	private TextField chapNumSearch;
	
	
	private int indexImg = 0, indexChap = 1;
	
	
	private static final String LINK_WEB = "https://truyentranh24.com/kimetsu-no-yaiba";
	private static final int MAX_CHAP_NUM = 205;
	private static final int MIN_CHAP_NUM = 1;
	private HashMap<String, Chap> chaps;
	private Chap chap;
	private ArrayList<String> listImgs;
	
	
// Test xem có url có bị lỗi khi kết nối không (vd lỗi 403)
//	public void initialize(URL location, ResourceBundle resources) {
//		Image image = new Image("https://i.imgur.com/N5e9QCI.jpg");
//		Image image2 = new Image("https://images2.imgbox.com/9b/ff/2yj6uzBQ_o.jpg");
//		Image image3 = new Image("https://icdn.truyentranh24.com/tt24/2020/03/10/529a23eb9eed3706_d9b15692e5df3900_190319158385459451.jpg");
//		imageView.setImage(image3);
//		
//	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
//		try {
//			setChapNumber();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		// khởi tạo ban đầu
		setLabelNumberChap(Controller.messageToReadScene);
		indexChap = Integer.parseInt(numberChap.getText()); // có 1 vấn đề là chap 204 sẽ không nhảy sang 204.1
		indexImg = 0;
		setLabelNumberImg();
		
		try {
			chaps = Crawler.getAllChaps(LINK_WEB);
			//setNewChap();
			chap = chaps.get(numberChap.getText());
			listImgs = Crawler.getAllImagesOnPage(chap.getUrl());
			setImageView();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		chapNumSearch.textProperty().addListener((obs, oldValue, newValue) -> {
			try {
				// có thể phát sinh lỗi ở đây, nếu newValue trống
				// double vì có chap 204.1
				double value = Double.parseDouble(newValue);
				if(value >= MIN_CHAP_NUM && value <= MAX_CHAP_NUM ) {
					find.setDisable(false);
				} else {
					find.setDisable(true);
				}
			} catch (Exception e) {
				find.setDisable(true);
			}
		});
		
		// set nút next và pre ảnh
		preImg.setDisable(true);
		// vẫn lỗi khi next, pre chapter. Sau khi nextImg kịch (bị set disable true) thì khi next/pre Chap nó không set lại
		// giải pháp tình thế => xem method setNewChap
		numberImg.textProperty().addListener((obs, oldValue, newValue) -> {
				int value = Integer.parseInt(newValue) - 1;
				if(value == 0) {
					preImg.setDisable(true);
				} else if (value == listImgs.size() - 1 ) {
					nextImg.setDisable(true);
				}
				else {
					preImg.setDisable(false);
					nextImg.setDisable(false);
				}
				
			
		});
		
		// set nút next và pre chap
		numberChap.textProperty().addListener((obs, oldValue, newValue) -> {
			try {
				double value = Double.parseDouble(newValue);
				if(value == MIN_CHAP_NUM) {
					preChap.setDisable(true);
				} else {
					preChap.setDisable(false);
				}
				
				if(value == MAX_CHAP_NUM) {
					nextChap.setDisable(true);
				} else {
					nextChap.setDisable(false);
				}
				
			} catch (Exception e) {
				preChap.setDisable(true);
				nextChap.setDisable(true);
			}
		});
	}
	
	
	
	// tranfer data between 2 conntroller nhưng bị lỗi null pointer exception ở dòng set Text
//	public void setChapNumber() throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("Gui.fxml"));
//		Controller controller = loader.<Controller>getController();
//		Parent root = loader.load();
//		numberChap.setText(controller.transferMessageToReadScene());
//	}
	
	
	
	public void clickReturnMainScene(ActionEvent event) {
		(new AllScene()).setMainScene(event);
	}
	
//	public void setTitle() {
//		Stage stage = (Stage)(numberChap.getScene().getWindow());
//		stage.setTitle("Đọc truyện KnY  - Chapter " + numberChap.getText());
//	}
	
	public void setTitle(ActionEvent event) {
	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	stage.setTitle("Đọc truyện KnY  - Chapter " + numberChap.getText());
	}
	
	public void setLabelNumberChap(String value) {
		numberChap.setText(value);
	}
	public void setLabelNumberImg() {
		numberImg.setText("" + (indexImg + 1));
	}
	
	public void setImageView() {
		Image img = new Image(listImgs.get(indexImg), true); // có true và không có true sẽ có sự khác biệt khi load ảnh
		setLabelNumberImg();
		imageView.setImage(img);
	}
	
	public void setNewChap(ActionEvent event) throws IOException {
		chap = chaps.get(numberChap.getText());
		listImgs = Crawler.getAllImagesOnPage(chap.getUrl());
		indexImg = 0;
		setTitle(event);
		setImageView();
		
		// giải pháp tình thế cho việc set Disable
		nextImg.setDisable(false);
	}
	
	public void clickNextImg(ActionEvent event) {
		if(indexImg < listImgs.size() - 1) {
			indexImg++;
			setImageView();
		}
	}
	
	public void clickPreImg(ActionEvent event) {
		if(indexImg > 0) {
			indexImg--;
			setImageView();
		}
	}
	
	
	public void clickNextChap(ActionEvent event) throws IOException {
		if(indexChap < MAX_CHAP_NUM) {
			indexChap++;
			setLabelNumberChap(Integer.toString(indexChap)); // đáng lẽ nên để setLabelNumberChap trong setNewChap
			setNewChap(event);
		}
	}
	
	public void clickPreChap(ActionEvent event) throws IOException {
		if(indexChap > MIN_CHAP_NUM) {
			indexChap--;
			setLabelNumberChap(Integer.toString(indexChap)); // đáng lẽ nên để setLabelNumberChap trong setNewChap
			setNewChap(event);
		}
	}
	
	public void clickFind(ActionEvent event) {
		try {
		indexChap = Integer.parseInt(chapNumSearch.getText());
		setLabelNumberChap(chapNumSearch.getText()); // đáng lẽ nên để setLabelNumberChap trong setNewChap
		setNewChap(event);
		chapNumSearch.setText(""); // xóa text trong ô tìm kiếm
		} catch (Exception e) {
			
		}
	}
	
	
}
