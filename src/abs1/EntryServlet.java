package abs1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBUtils;


@WebServlet("/entry.html")
public class EntryServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/entry.jsp")
		.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try{

			con = DBUtils.getConnection();

			sql = "INSERT INTO abs1 (date, category, note, price) VALUES (?,?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, req.getParameter("date"));
			ps.setString(2, req.getParameter("category"));
			if(req.getParameter("note").equals("")) {
				ps.setString(3, null);
			}else {
				ps.setString(3, req.getParameter("note"));
			}
			ps.setString(4, req.getParameter("price"));

			ps.executeUpdate();
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
			}catch(Exception e){}
		}

		resp.sendRedirect("index.html");
	}
}
