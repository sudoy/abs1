package abs1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search.html")
public class Search extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


		req.setAttribute("divisions", "on");

		//フォワード
		getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//フォワード
		getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
	}

}
