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

@WebServlet("/delete.html")
public class DeleteServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

			Connection con = null;
			PreparedStatement ps = null;
			String sql = null;

			try {
				con = DBUtils.getConnection();

				sql = "DELETE FROM abs1 WHERE id = ?";

				//準備
				ps = con.prepareStatement(sql);

				//データをセット
				ps.setString(1, req.getParameter("id"));

				//実行
				ps.executeUpdate();

				//リダイレクト
				resp.sendRedirect("index.html");


			}catch(Exception e){
				throw new ServletException(e);

			}finally{

				try{
					DBUtils.close(ps);
					DBUtils.close(con);

				}catch(Exception e){

				}
			}

		}
}
