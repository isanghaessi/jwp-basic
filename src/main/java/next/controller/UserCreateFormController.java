package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCreateFormController extends Controller {
	private static final Logger log = LoggerFactory.getLogger(UserCreateFormController.class);

	@Override
	public String execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return forward("/user/form.jsp");
	}
}
