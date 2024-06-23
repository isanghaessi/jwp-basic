package next.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;
import next.utils.UserSessionUtils;

public class LoginController extends Controller {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String userId = httpServletRequest.getParameter("userId");
		String password = httpServletRequest.getParameter("password");
		User user = DataBase.findUserById(userId);
		if (Objects.isNull(user)) {
			httpServletRequest.setAttribute("loginFailed", true);

			return forward("/user/login.jsp");
		}

		if (user.matchPassword(password)) {
			HttpSession session = httpServletRequest.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);

			return redirect("/");
		}

		httpServletRequest.setAttribute("loginFailed", true);

		return forward("/user/login.jsp");
	}
}
