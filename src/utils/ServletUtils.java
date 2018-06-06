package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
	public static LocalDate stringParse(HttpServletRequest req) {


		return LocalDate.parse(req.getParameter("now"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));	}
}
