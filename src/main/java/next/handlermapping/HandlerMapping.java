package next.handlermapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.Controller;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LoginFormController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;
import next.controller.UserCreateController;
import next.controller.UserCreateFormController;

public enum HandlerMapping {
	USER_CREATE_FORM_GET("/users/form", "GET", new UserCreateFormController()),
	USER_CREATE_POST("/users/create", "POST", new UserCreateController()),
	HOME_GET("/", "GET", new HomeController()),
	LIST_USER_GET("/users", "GET", new ListUserController()),
	LOGIN_FORM_GET("/users/loginForm", "GET", new LoginFormController()),
	LOGIN_POST("/users/login", "POST", new LoginController()),
	LOGOUT_GET("/users/logout", "GET", new LogoutController()),
	PROFILE_GET("/users/profile", "GET", new ProfileController()),
	UPDATE_USER_FORM_GET("/users/updateForm", "GET", new UpdateUserFormController()),
	UPDATE_USER_POST("/users/update", "POST", new UpdateUserController());

	private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

	private static final String GET_METHOD = "GET";
	private static final String POST_METHOD = "POST";

	private static final Map<String, Map<String, Controller>> HANDLER_MAP = Arrays.stream(values())
		.collect(Collectors.groupingBy(HandlerMapping::getMethod, Collectors.toMap(HandlerMapping::getPath, HandlerMapping::getController)));

	HandlerMapping(String path, String method, Controller controller) {
		this.path = path;
		this.method = method;
		this.controller = controller;
	}

	private final String path;
	private final String method;
	private final Controller controller;

	public String getPath() {
		return path;
	}

	public String getMethod() {
		return method;
	}

	public Controller getController() {
		return controller;
	}

	public static Controller getMapping(String url) {
		return getMethodMapping(GET_METHOD, url);
	}

	public static Controller postMapping(String url) {
		return getMethodMapping(POST_METHOD, url);
	}

	private static Controller getMethodMapping(String method, String url) {

		return HANDLER_MAP.getOrDefault(method, new HashMap<>())
			.get(url);
	}
}
