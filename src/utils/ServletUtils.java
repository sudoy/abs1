package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

	public static LocalDate stringParse(HttpServletRequest req) {
		return LocalDate.parse(req.getParameter("now"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public static String categoryCatch(HttpServletRequest req) {

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		String name = "";

		try {
			con = DBUtils.getConnection();

			sql = "select category, category_name from abs1 join list on category = category_id order where category = ?  by category";

			ps = con.prepareStatement(sql);

			ps.setString(1, req.getParameter("category"));

			rs = ps.executeQuery();

			rs.next();

			name = rs.getString("category_name");

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return name;
	}
}
