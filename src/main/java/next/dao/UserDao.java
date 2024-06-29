package next.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
	public int insert(User user) throws SQLException {
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

		Map<Integer, String> parameterMap = new HashMap<>();
		parameterMap.put(1, user.getUserId());
		parameterMap.put(2, user.getPassword());
		parameterMap.put(3, user.getName());
		parameterMap.put(4, user.getEmail());

		return JdbcTemplate.executeUpdate(sql, parameterMap);
	}

	public int update(User user) throws SQLException {
		String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE userId = ?";

		Map<Integer, String> parameterMap = new HashMap<>();
		parameterMap.put(1, user.getUserId());
		parameterMap.put(2, user.getPassword());
		parameterMap.put(3, user.getName());
		parameterMap.put(4, user.getEmail());
		parameterMap.put(5, user.getUserId());

		return JdbcTemplate.executeUpdate(sql, parameterMap);
	}

	public List<User> findAll() throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS";

		return JdbcTemplate.executeQuery(User.class, sql);
	}

	public User findByUserId(String userId) throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

		Map<Integer, String> parameterMap = new HashMap<>();
		parameterMap.put(1, userId);

		return JdbcTemplate.executeQuery(User.class, sql, parameterMap).stream()
			.findFirst()
			.orElse(null);
	}
}
