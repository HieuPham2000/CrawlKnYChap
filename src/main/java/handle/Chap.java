package handle;

public class Chap {
	private String urlOfChap;
	private String chapNumber;
	
	public Chap(String url) {
		this.urlOfChap = url;
		chapNumber = findChapNumber(url);
	}

	public String getUrl() {
		return urlOfChap;
	}

	public String getChapNumber() {
		return chapNumber;
	}
	
	// dựa vào phân tích cấu trúc link
	// https://truyentranh24.com/kimetsu-no-yaiba/chap-205
	private String findChapNumber(String url) {
		String number ="";
		int count = 2;
		for (int i = 0; i< url.length(); i++) {
			if(Character.isDigit(url.charAt(i))) {
				count--;
				if (count >= 0) {
					continue;
				}
				number += url.charAt(i);
			}
		}
		// chap 204.1
		if (number.equals("2041")) {
			return "204.1";
		}
		return number;
	}
	
}
