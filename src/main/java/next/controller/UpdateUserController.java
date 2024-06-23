package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;
import next.utils.UserSessionUtils;

public class UpdateUserController extends Controller {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		User user = DataBase.findUserById(httpServletRequest.getParameter("userId"));
		if (!UserSessionUtils.isSameUser(httpServletRequest.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}

		User updateUser = new User(httpServletRequest.getParameter("userId"), httpServletRequest.getParameter("password"), httpServletRequest.getParameter("name"),
			httpServletRequest.getParameter("email"));
		log.debug("Update User : {}", updateUser);
		user.update(updateUser);

		return redirect("/");
	}
}
