package next.controller;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.model.User;

public class ProfileController extends Controller {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		User user = DataBase.findUserById(userId);
		if (user == null) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}
		req.setAttribute("user", user);
		RequestDispatcher rd = req.getRequestDispatcher("/user/profile.jsp");
		rd.forward(req, resp);
	}

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String userId = httpServletRequest.getParameter("userId");
		User user = DataBase.findUserById(userId);
		if (Objects.isNull(user)) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}

		httpServletRequest.setAttribute("user", user);

		return forward("/user/profile.jsp");
	}
}
