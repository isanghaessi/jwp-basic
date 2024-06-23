package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;
import next.utils.UserSessionUtils;

public class UpdateUserFormController extends Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserFormController.class);

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String userId = httpServletRequest.getParameter("userId");
		User user = DataBase.findUserById(userId);
		if (!UserSessionUtils.isSameUser(httpServletRequest.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}

		httpServletRequest.setAttribute("user", user);

		return forward("/user/updateForm.jsp");
	}
}
