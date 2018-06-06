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

@WebServlet("/update.html")
public class Update extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;


		try {
			//データベース接続
			con = DBUtils.getConnection();

			//GETパラメータを取得
			String id = req.getParameter("id");

			//SQL
			sql = "SELECT id, date, category, note, price FROM abs1 WHERE id = ?";

			//準備
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setString(1, id);

			//実行
			rs = ps.executeQuery();

			rs.next();

			Abs1 t = new Abs1(
					rs.getInt("id"),
					rs.getDate("date"),
					rs.getString("category"),
					rs.getString("note"),
					rs.getInt("price"));

			req.setAttribute("data", t);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/update.jsp").forward(req, resp);

		}catch(Exception e){
			throw new ServletException(e);
		}finally{

			try{
				DBUtils.close(rs);
				DBUtils.close(ps);
				DBUtils.close(con);
			}catch(Exception e){

			}
		}


	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		List<String> errors = validate(req);
		if(errors.size() != 0) {
			req.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/entry.jsp")
			.forward(req, resp);
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;

		try {
			con = DBUtils.getConnection();

			sql = "UPDATE abs1 SET date = ?, category = ?, note = ?, price = ? WHERE id = ?";

			//準備
			ps = con.prepareStatement(sql);

			//データをセット
			ps.setString(1, req.getParameter("date"));
			ps.setString(2, req.getParameter("category"));
			ps.setString(3, req.getParameter("note"));
			if(req.getParameter("division").equals("支出")) {
				ps.setString(4, "-" + req.getParameter("price"));
			}else if(req.getParameter("division").equals("収入")){
				ps.setString(4, req.getParameter("price"));
			}
			ps.setString(5, req.getParameter("id"));

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

	private List<String> validate(HttpServletRequest req){
		List<String> list = new ArrayList<>();
		if(req.getParameter("date").equals("")) {
			list.add("日付は必須入力です。");
		}
		if(req.getParameter("category").equals("0")) {
			list.add("カテゴリーは必須入力です。");
		}
		if(req.getParameter("price").equals("")) {
			list.add("金額は必須入力です。");
		}
		try {
			int a = Integer.parseInt(req.getParameter("price"));
		}catch(Exception e) {
			list.add("金額は数字を入力してください。");
		}
		return list;
	}

}
