package utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
}
