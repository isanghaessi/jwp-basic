package next.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.model.User;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOG_IN_SESSION_KEY = "log-in";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = new ArrayList<>();

		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(LOG_IN_SESSION_KEY);
		if (Objects.nonNull(user)) {
			users.add((User)session.getAttribute(LOG_IN_SESSION_KEY));
		}

		req.setAttribute("users", users);
		RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
		rd.forward(req, resp);
	}
}
