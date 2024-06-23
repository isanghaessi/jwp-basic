package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.utils.UserSessionUtils;

public class LogoutController extends Controller {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);

		return redirect("/");
	}
}
