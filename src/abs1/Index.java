package abs1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import abs1.beans.Abs1;
import abs1.beans.Category;
import utils.DBUtils;
import utils.ServletUtils;


@WebServlet("/index.html")
public class Index extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();

		Connection con = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;

		int beforeIncome = 0;
		int beforeSpend = 0;
		int currentIncome = 0;
		int currentSpend = 0;

		LocalDate today = LocalDate.now();
		session.setAttribute("today", today);

		try{
			String jump = "";
			String jumpY = "";
			LocalDate now = null;

			if(req.getParameter("now") != null) {
				now = ServletUtils.stringParse(req.getParameter("now"));
			}else {
				now = LocalDate.now();
			}

			if(req.getParameter("jump") != null) {
				 jump = req.getParameter("jump");
			}
			if(jump.equals("0")) {
				now = now.plusMonths(-1);
			}else if(jump.equals("1")) {
				now = now.plusMonths(1);
			}

			if(req.getParameter("jumpY") != null) {
				 jumpY = req.getParameter("jumpY");
			}
			if(jumpY.equals("0")) {
				now = now.plusYears(-1);
			}else if(jumpY.equals("1")) {
				now = now.plusYears(1);
			}

			req.setAttribute("now", now);
			LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()); // 末日
			LocalDate ldolm = lastDayOfMonth.plusMonths(-1);
			LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth()); // 初日
			LocalDate fdolm = firstDayOfMonth.plusMonths(-1);


			con = DBUtils.getConnection();

			sql = "select sum(price) as price from abs1 where date between ? and ? group by price >= 0, price < 0 order by sum(price) desc";

			ps = con.prepareStatement(sql);

			ps.setString(1, firstDayOfMonth.toString());
			ps.setString(2, lastDayOfMonth.toString());

			rs = ps.executeQuery();

			if(rs.next()) {
				if(rs.getInt("price") >= 0) {
					currentIncome = rs.getInt("price");
					req.setAttribute("currentIncome", currentIncome);
					if(rs.next()) {
						currentSpend = rs.getInt("price");
						req.setAttribute("currentSpend", currentSpend);
					}else {
						currentSpend = 0;
						req.setAttribute("currentSpend", currentSpend);
					}

				}else {
					currentIncome = 0;
					req.setAttribute("currentIncome", currentIncome);
					currentSpend = rs.getInt("price");
					req.setAttribute("currentSpend", currentSpend);
				}
			}else {
				List<String> errors = new ArrayList<>();
				errors.add("データがありません。");

				session.setAttribute("errors", errors);

				req.setAttribute("currentIncome", "0");
				req.setAttribute("currentSpend", "0");

			}



			try{
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}

			ps = con.prepareStatement(sql);

			ps.setString(1, fdolm.toString());
			ps.setString(2, ldolm.toString());

			rs = ps.executeQuery();

			if(rs.next()) {
				if(rs.getInt("price") < 0) {
					beforeSpend = rs.getInt("price");
					beforeIncome = 0;
				}else {
					beforeIncome = rs.getInt("price");
				}
				if(rs.next()) {
					beforeSpend = rs.getInt("price");
				}else {
					beforeSpend = 0;
				}
			}else {
				beforeSpend = 0;

				beforeIncome = 0;
			}
			int compareIncome = currentIncome - beforeIncome;
			req.setAttribute("compareIncome", compareIncome);
			int compareSpend = currentSpend - beforeSpend;
			req.setAttribute("compareSpend", compareSpend);

			try{
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}

			sql = "select id, date, category_data, note, price from abs1 join list on category = category_id where date between ? and ? order by date";

			ps = con.prepareStatement(sql);

			ps.setString(1, firstDayOfMonth.toString());
			ps.setString(2, lastDayOfMonth.toString());

			rs = ps.executeQuery();

			List<Abs1> list = new ArrayList<Abs1>();
			while(rs.next()) {
				Abs1 t = new Abs1(rs.getInt("id"), rs.getDate("date"),
						rs.getString("category_data"), rs.getString("note"),
						rs.getInt("price"));
				list.add(t);
			}

			req.setAttribute("list", list);


			//カテゴリー表示
			try{
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}

			sql = "SELECT category_id, category_data FROM list ORDER BY category_id";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			List<Category> categories = new ArrayList<>();

			while(rs.next()) {
				Category c = new Category(
						rs.getInt("category_id"),
						rs.getString("category_data")
						);
				categories.add(c);
			}

			session.setAttribute("categories", categories);

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
