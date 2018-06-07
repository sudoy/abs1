package utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HTMLUtils {

	public static String dateFormat(Date date) {
		String s = "";
		if(date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		s = sdf.format(date);

		return s;
	}

	public static String dateFormat(String date) {
		String s = "";
		if(date == null) {
			return "";
		}
		s = date.replace("-", "/");
		return s;
	}

	public static String dateFormat(LocalDate now) {
		String s = "";
		if(now == null) {
			return "";
		}
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		s = now.format(sdf);

		return s;
	}

	public static String nowDateFormat(LocalDate now) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");

		return now.format(formatter);
	}
	public static String color(int price) {
		String s = "";
		if(price >= 0) {
			s = "info";
		}else {
			s = "danger";
		}
		return s;
	}
	public static int priceFormat(int price) {
		int p = 0;
		if(price < 0) {
			p = price * -1;
		}else {
			p = price;
		}

		return p;

	}

	public static String checkDivision(int price) {
		String s = "";
		if(price < 0) {
			s = "支出";
		}else if(0 <= price) {
			s = "収入";
		}else {
			s = "未判定";
		}

		return s;
	}

	public static String addPlus(Integer price) {
		String s = String.valueOf(price);
		if(price >= 0) {
			s = "+" + s;
		}
		return s;
	}
}
