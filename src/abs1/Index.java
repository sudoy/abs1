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

import abs1.beans.Abs1;
import utils.DBUtils;
import utils.ServletUtils;


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
			String jump = "";
			LocalDate now = null;
			if(req.getParameter("now") != null) {
				now = ServletUtils.stringParse(req);
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

			req.setAttribute("now", now);
			LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()); // 末日
			LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth()); // 初日


			con = DBUtils.getConnection();

			sql = "select sum(price) as price from abs1 where date between ? and ? group by price >= 0, price < 0 order by sum(price) desc";

			ps = con.prepareStatement(sql);

			ps.setString(1, firstDayOfMonth.toString());
			ps.setString(2, lastDayOfMonth.toString());

			rs = ps.executeQuery();


			if(rs.next()) {
				int currentIncome = rs.getInt("price");
				req.setAttribute("currentIncome", currentIncome);
			}else {
				int currentIncome = 0;
				req.setAttribute("currentIncome", currentIncome);
			}

			if(rs.next()) {
				int currentSpend = rs.getInt("price");
				req.setAttribute("currentSpend", currentSpend);
			}else {
				int currentSpend = 0;
				req.setAttribute("currentSpend", currentSpend);
			}

			try{
				DBUtils.close(ps);
				DBUtils.close(rs);
			}catch(Exception e){}


			sql = "select id, date, category_data, note, price from abs1 join list on category = category_id where date between ? and ? order by id";

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
