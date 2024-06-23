package next.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Controller extends HttpServlet {
	protected String forward(String url) {
		return url;
	}

	protected String redirect(String url) {
		return String.format("redirect:%s", url);
	}

	public abstract String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
