package next.web;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/login")
public class LogInServlet extends HttpServlet {
	private static final String LOG_IN_FAILED_PATH = "/user/login_failed.html";
	private static final String LOG_IN_SESSION_KEY = "log-in";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");

		if (Objects.isNull(userId) || Objects.isNull(password)) {
			resp.sendRedirect(LOG_IN_FAILED_PATH);
		}

		User user = DataBase.findUserById(userId);

		if (Objects.isNull(user) || !StringUtils.equals(user.getPassword(), password)) {
			resp.sendRedirect(LOG_IN_FAILED_PATH);
		}

		HttpSession session = req.getSession(true);
		session.setAttribute(LOG_IN_SESSION_KEY, user);

		resp.sendRedirect("/user/list");
	}
}
