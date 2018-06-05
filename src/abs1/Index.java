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


@WebServlet("/index.html")
public class Index extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		try{


			con = DBUtils.getConnection();

			sql = "select id, date, category_data, note, price from abs1 join list on category = category_id order by id";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

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
		getServletContext().getRequestDispatcher("/WEB-INF/index.jsp")
		.forward(req, resp);


	}
}
