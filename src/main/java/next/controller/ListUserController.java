package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.utils.UserSessionUtils;

public class ListUserController extends Controller {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		if (!UserSessionUtils.isLogined(httpServletRequest.getSession())) {
			return redirect("/users/loginForm");
		}

		httpServletRequest.setAttribute("users", DataBase.findAll());

		return forward("/user/list.jsp");
	}
}
