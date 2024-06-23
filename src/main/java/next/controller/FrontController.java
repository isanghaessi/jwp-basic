package next.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.handlermapping.HandlerMapping;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class FrontController extends HttpServlet {
	private static final Pattern REDIRECT_PATTERN = Pattern.compile("^redirect:(.*)");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		try {
			URI uri = new URI(url);
			String path = uri.getPath();

			Controller controllerClass = HandlerMapping.getMapping(path);
			process(req, resp, controllerClass);
		} catch (URISyntaxException e) {
			throw new RuntimeException("url 패턴이 이상합니다.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();

		Controller controller = HandlerMapping.postMapping(url);
		process(req, resp, controller);
	}

	private static void process(HttpServletRequest req, HttpServletResponse resp, Controller controller) throws IOException, ServletException {
		if (Objects.isNull(controller)) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String result = controller.execute(req, resp);
		Matcher redirectMatcher = REDIRECT_PATTERN.matcher(result);
		if (redirectMatcher.find()) {
			resp.sendRedirect(redirectMatcher.group(1));
			return;
		}

		RequestDispatcher requestDispatcher = req.getRequestDispatcher(result);
		requestDispatcher.forward(req, resp);
	}
}
