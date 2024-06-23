package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;

public class HomeController extends Controller {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("users", DataBase.findAll());

		return forward("index.jsp");
	}
}
