package next.web;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.model.User;

@WebServlet("/user/logout")
public class LogOutServlet extends HttpServlet {
	private static final String LOG_IN_SESSION_KEY = "log-in";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();

		if (Objects.isNull(session)) {
			resp.sendRedirect("/user/list");
			return;
		}

		session.removeAttribute(LOG_IN_SESSION_KEY);

		resp.sendRedirect("/user/list");
	}
}
