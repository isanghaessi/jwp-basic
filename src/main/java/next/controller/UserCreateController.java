package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;

public class UserCreateController extends Controller {
	private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		User user = new User(httpServletRequest.getParameter("userId"), httpServletRequest.getParameter("password"), httpServletRequest.getParameter("name"),
			httpServletRequest.getParameter("email"));
		log.debug("User : {}", user);

		DataBase.addUser(user);

		return redirect("/");
	}
}
