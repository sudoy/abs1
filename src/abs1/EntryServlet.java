package abs1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.DBUtils;
import utils.ServletUtils;


@WebServlet("/entry.html")
public class EntryServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/entry.jsp")
		.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();


		List<String> errors = validate(req);
		if(errors.size() != 0) {
			session.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/entry.jsp")
			.forward(req, resp);
			return;
		}

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
			if(req.getParameter("division").equals("支出")) {
				ps.setString(4, "-" + req.getParameter("price"));
			}else if(req.getParameter("division").equals("収入")){
				ps.setString(4, req.getParameter("price"));
			}
			ps.executeUpdate();
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
			}catch(Exception e){}
		}
		List<String> successes = new ArrayList<>();
		String success = "「" + req.getParameter("date") + " "
				+ ServletUtils.categoryCatch(req) + " "
				+ req.getParameter("price") + "円」を登録しました。";
		successes.add(success);

		session.setAttribute("successes", successes);
		resp.sendRedirect("index.html");
	}

	private List<String> validate(HttpServletRequest req){
		List<String> list = new ArrayList<>();
		if(req.getParameter("date").equals("")) {
			list.add("日付は必須入力です。");
		}else{
			//形式の判定
			try {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				df.setLenient(false);
			    String s1 = req.getParameter("date");
			    String s2 = df.format(df.parse(s1));

			    if(s1.equals(s2)) {

			    }else {
			    	list.add("期限は「YYYY/MM/DD」形式で入力して下さい。");
			    }

			}catch(ParseException p) {

				list.add("期限は「YYYY/MM/DD」形式で入力して下さい。");

			}
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
