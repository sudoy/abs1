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
import javax.servlet.http.HttpSession;

import utils.DBUtils;
import utils.HTMLUtils;

@WebServlet("/delete.html")
public class DeleteServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

			HttpSession session = req.getSession();
			req.setCharacterEncoding("utf-8");

			Connection con = null;
			PreparedStatement ps = null;
			String sql = null;
			ResultSet rs = null;

			String date = "";
			String category = "";
			String price = "";

			try {
				con = DBUtils.getConnection();

				sql = "select  date, category_data, price from abs1 join list on category = category_id where id = ?";

				ps = con.prepareStatement(sql);

				ps.setString(1, req.getParameter("id"));

				rs = ps.executeQuery();


				rs.next();

				date = rs.getString("date");
				category = rs.getString("category_data");
				price = rs.getString("price");

				DBUtils.close(ps);

				sql = "DELETE FROM abs1 WHERE id = ?";

				//準備
				ps = con.prepareStatement(sql);

				//データをセット
				ps.setString(1, req.getParameter("id"));

				//実行
				ps.executeUpdate();



			}catch(Exception e){
				throw new ServletException(e);

			}finally{

				try{
					DBUtils.close(ps);
					DBUtils.close(con);

				}catch(Exception e){

				}
			}

			List<String> successes = new ArrayList<>();
			String success = "「" + HTMLUtils.dateFormat(date) + " "
					+ category + " "
					+ price + "円」を削除しました。";
			successes.add(success);

			session.setAttribute("successes", successes);
			resp.sendRedirect("index.html");


		}
}
