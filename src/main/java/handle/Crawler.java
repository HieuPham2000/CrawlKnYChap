package handle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler {
	
	// Vấn đề phát sinh: có nút "Xem thêm" nên :(((
	public static HashMap<String, Chap> getAllChaps(String url) throws IOException {
		HashMap<String, Chap> chaps = new HashMap<String, Chap>();
		
		Document document = Jsoup.connect(url).get();
		Elements elements = document.getElementsByClass("chapter-item");
		for (int i = 0; i < elements.size(); i++) {
			Elements eTag = elements.get(i).getElementsByTag("a");
			for (int j = 0; j < eTag.size(); j++) {
				String linkChap = eTag.first().absUrl("href");
				Chap chap = new Chap(linkChap);
				chaps.put(chap.getChapNumber(), chap);
			}
		}
		
		// khắc phục tạm thời
		Chap lastestChap = findLastestChap(url);
		int max = Integer.parseInt(lastestChap.getChapNumber());
		for(int i = 1; i <= max; i++) {
			String chapNum = Integer.toString(i);
			String linkChap = lastestChap.getUrl().replace(lastestChap.getChapNumber(), chapNum);
			Chap chap = new Chap(linkChap);
			chaps.put(chap.getChapNumber(), chap);
		}
		
		return chaps;
	}
	
	public static Chap findLastestChap (String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements elements = document.getElementsByClass("manga-latest");
		Elements eTag = elements.get(0).getElementsByTag("a");
		String linkChap = eTag.first().absUrl("href");
		Chap chap = new Chap(linkChap);
		
		return chap;
		
	}
		
	public static ArrayList<String> getAllImagesOnPage(String pageURL ) throws IOException {
		ArrayList<String> listImages = new ArrayList<String>();
		
		Document document = Jsoup.connect(pageURL).get();
		Elements elements = document.getElementsByClass("chapter-content");
		for (int i = 0; i < elements.size(); i++) {
			Elements eTag = elements.get(i).getElementsByTag("img");
			for (int j = 0; j < eTag.size(); j++) {
				String linkImage = eTag.get(j).absUrl("data-src"); // tại sao để "src" không được mà "data-src" lại được
				if(linkImage.equals("")) {
					continue;
				}
				listImages.add(linkImage);
			}
		}
		
		
		return listImages;	
	}
	
	
	public static void saveImage(String src, String name, String dir) {
		try {
			URL url = new URL(src);
			InputStream in = url.openStream();
		    
			OutputStream out = new BufferedOutputStream(new FileOutputStream(dir + "\\" + name)); // để + "/" cũng oke
			for (int i; (i = in.read()) != -1;) {
				out.write(i);
			}
			
			out.close();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void saveChapter(String srcChap, String dir) {
		try {
			ArrayList<String> listImgs = getAllImagesOnPage(srcChap);
			for (int i = 0; i < listImgs.size(); i++) {
				saveImage(listImgs.get(i), (i+1) + ".jpg" , dir);
			}
			JOptionPane.showMessageDialog(null, "Tải về hoàn tất! Giờ bạn có thể đọc offline rồi nha!");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hành động không thể hoàn tất, đã có lỗi xảy ra!");
		}
		
	}
	
	// tải trọn bộ
	// nguy hiểm: treo, lỗi không tải được hết (bị cấm vì crawl quá nhanh và nhiều???)
	// chú ý cả việc có JOptionPane thông báo ở saveChapter nữa, cần comment bỏ nó đi nếu không sẽ quá tải thông báo
//	public static void saveManga(String url, String dir) throws IOException {
//		HashMap<String, Chap> chaps = Crawler.getAllChaps(url);
//		(new File(dir + "\\Kimetsu_no_Yaiba")).mkdir(); // tạo thư mục
//		for(String k: chaps.keySet()) {
//			String name = "KnY_chap" + k;
//			(new File(dir + "\\Kimetsu_no_Yaiba\\" + name)).mkdir(); // tạo thư mục
//			saveChapter(chaps.get(k).getUrl(), dir + "\\Kimetsu_no_Yaiba\\" + name);
//		}
//	}
	
	
	// test
	public static void main(String[] args) throws IOException  {
		HashMap<String, Chap> chaps = getAllChaps("https://truyentranh24.com/kimetsu-no-yaiba");
		System.out.println(chaps.get("205").getUrl());
		System.out.println(chaps.get("204.1").getUrl());
		ArrayList<String> imgs = getAllImagesOnPage(chaps.get("205").getUrl());
		System.out.println(imgs.size());
	}
}

