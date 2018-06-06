package abs1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abs1.beans.Abs1;
import utils.DBUtils;

@WebServlet("/result.html")
public class Result extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try{


			con = DBUtils.getConnection();

			sql = "select id, date, category_data, note, price "
					+ "from abs1 join list on category = category_id "
					+ "where case when ? = '' then date is not null else date >= ? end "
					+ "and case when ? = '' then date is not null else date <= ? end "
					+ "and category_data in(?, ?, ?) and note like ? order by id";

			ps = con.prepareStatement(sql);

			ps.setString(1, req.getParameter("start"));
			ps.setString(2, req.getParameter("start"));
			ps.setString(3, req.getParameter("end"));
			ps.setString(4, req.getParameter("end"));
			ps.setString(5, req.getParameter("eat"));
			ps.setString(6, req.getParameter("life"));
			ps.setString(7, req.getParameter("money"));
			ps.setString(8, "%" + req.getParameter("note") + "%");

			System.out.println(ps);

			rs = ps.executeQuery();

			if(!rs.next()) {
				List<String> errors = new ArrayList<>();
				errors.add("検索結果は1件もありません。");
				req.setAttribute("errors", errors);
				getServletContext().getRequestDispatcher("/search.html")
					.forward(req, resp);
				return;
			}

			List<Abs1> list = new ArrayList<Abs1>();
			while(rs.next()) {
				Abs1 t = new Abs1(rs.getInt("id"), rs.getDate("date"),
						rs.getString("category_data"), rs.getString("note"),
						rs.getInt("price"));
				list.add(t);
			}

			req.setAttribute("list", list);

		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}
		}
		//フォワード
		getServletContext().getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
	}
}
