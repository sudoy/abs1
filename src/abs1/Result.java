package abs1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import abs1.beans.Abs1;
import utils.DBUtils;

@WebServlet("/result.html")
public class Result extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.sendRedirect("index.html");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");


		HttpSession session = req.getSession();
		List<String> errors = validate(req);

		if(errors.size() != 0) {
			session.setAttribute("errors", errors);

			getServletContext().getRequestDispatcher("/WEB-INF/search.jsp")
			.forward(req, resp);
			return;

		}

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


			rs = ps.executeQuery();

			List<Abs1> list = new ArrayList<Abs1>();

			while(rs.next()) {
				Abs1 t = new Abs1(rs.getInt("id"), rs.getDate("date"),
						rs.getString("category_data"), rs.getString("note"),
						rs.getInt("price"));
				list.add(t);
			}

			req.setAttribute("list", list);

			if(list.isEmpty()) {

				errors = new ArrayList<>();
				errors.add("検索結果は1件もありません。");
				session.setAttribute("errors", errors);
				getServletContext().getRequestDispatcher("/WEB-INF/search.jsp")
					.forward(req, resp);
				return;
			}

			List<String> conditions = new ArrayList<>();
			if(req.getParameter("eat") != null)conditions.add(req.getParameter("eat"));
			if(req.getParameter("life") != null)conditions.add(req.getParameter("life"));
			if(req.getParameter("money") != null)conditions.add(req.getParameter("money"));

			String condition = "";

			for(int i = 0; i < conditions.size(); i++) {
				if(i == conditions.size() - 1) {
					condition = condition.concat(conditions.get(i));
				}else {
					condition = condition.concat(conditions.get(i) + ", ");
				}
			}

			req.setAttribute("condition", condition);

			//フォワード
			getServletContext().getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);

		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{
				DBUtils.close(con);
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}
		}

	}

	private List<String> validate(HttpServletRequest req){
		List<String> list = new ArrayList<>();
		if(!req.getParameter("start").equals("")) {
			//形式の判定
			try {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				df.setLenient(false);
			    String s1 = req.getParameter("start");
			    String s2 = df.format(df.parse(s1));

			    if(s1.equals(s2)) {

			    }else {
			    	list.add("期限は「YYYY/MM/DD」形式で入力して下さい。");
			    }

			}catch(ParseException p) {

				list.add("期限は「YYYY/MM/DD」形式で入力して下さい。");

			}
		}
		if(!req.getParameter("end").equals("")) {
			//形式の判定
			try {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				df.setLenient(false);
			    String s1 = req.getParameter("end");
			    String s2 = df.format(df.parse(s1));

			    if(s1.equals(s2)) {

			    }else {
			    	list.add("期限は「YYYY/MM/DD」形式で入力して下さい。");
			    }

			}catch(ParseException p) {

				list.add("期限は「YYYY/MM/DD」形式で入力して下さい。");

			}
		}

		if(!req.getParameter("end").equals("") && !req.getParameter("start").equals("")) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
			fmt.setLenient(false);
			try {
				java.util.Date fs = fmt.parse(req.getParameter("start"));
				java.util.Date fe = fmt.parse(req.getParameter("end"));
				if(!fs.before(fe)) {
					list.add("日付入力の順序が間違っています。");
				}
			} catch (Exception e1) {

			}
		}

		return list;
	}

}
