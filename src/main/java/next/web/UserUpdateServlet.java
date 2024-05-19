package next.web;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/update-form")
public class UserUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");

		User user = DataBase.findUserById(userId);

		if (Objects.isNull(user)) {
			resp.sendError(400);
			return;
		}

		HttpSession session = req.getSession(true);

		session.setAttribute("user", user);

		RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
		rd.forward(req, resp);
	}
}
