package utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HTMLUtils {

	public static String dateFormat(Date limitDate) {
		String s = "";
		if(limitDate == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		s = sdf.format(limitDate);

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
}
