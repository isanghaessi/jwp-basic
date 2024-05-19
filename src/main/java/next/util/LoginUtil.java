package next.util;

import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import next.model.User;

public class LoginUtil {
	private static final String LOG_IN_SESSION_KEY = "log-in";

	private LoginUtil() {}

	public static boolean isLoggedIn(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		User user = (User)session.getAttribute(LOG_IN_SESSION_KEY);

		return Objects.nonNull(user);
	}
}
