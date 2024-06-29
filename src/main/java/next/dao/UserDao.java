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
import next.model.User;

public class UserDao {
	public int insert(User user) throws SQLException {
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

		Map<Integer, String> parameterMap = new HashMap<>();
		parameterMap.put(1, user.getUserId());
		parameterMap.put(2, user.getPassword());
		parameterMap.put(3, user.getName());
		parameterMap.put(4, user.getEmail());

		return executeUpdate(sql, parameterMap);
	}

	public int update(User user) throws SQLException {
		String sql = "UPDATE USERS SET userId = ?, password = ?, name = ?, email = ? WHERE userId = ?";

		Map<Integer, String> parameterMap = new HashMap<>();
		parameterMap.put(1, user.getUserId());
		parameterMap.put(2, user.getPassword());
		parameterMap.put(3, user.getName());
		parameterMap.put(4, user.getEmail());
		parameterMap.put(5, user.getUserId());

		return executeUpdate(sql, parameterMap);
	}

	public List<User> findAll() throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS";

		return executeQuery(User.class, sql);
	}

	public User findByUserId(String userId) throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

		Map<Integer, String> parameterMap = new HashMap<>();
		parameterMap.put(1, userId);

		return executeQuery(User.class, sql, parameterMap).stream()
			.findFirst()
			.orElse(null);
	}

	private int executeUpdate(String sql, Map<Integer, String> parameterMap) {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			if (Objects.nonNull(parameterMap)) {
				for (Map.Entry<Integer, String> parameterEntry : parameterMap.entrySet()) {
					preparedStatement.setString(parameterEntry.getKey(), parameterEntry.getValue());
				}
			}

			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> List<T> executeQuery(Class<T> clazz, String sql) {
		return executeQuery(clazz, sql, null);
	}

	private <T> List<T> executeQuery(Class<T> clazz, String sql, Map<Integer, String> parameterMap) {
		ResultSet resultSet = null;

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			if (Objects.nonNull(parameterMap)) {
				for (Map.Entry<Integer, String> parameterEntry : parameterMap.entrySet()) {
					preparedStatement.setString(parameterEntry.getKey(), parameterEntry.getValue());
				}
			}

			resultSet = preparedStatement.executeQuery();

			List<T> resultList = new ArrayList<>();

			if (resultSet.next()) {
				T result = clazz.getDeclaredConstructor().newInstance();
				for (Method method : clazz.getDeclaredMethods()) {
					if (method.getName().startsWith("set")) {
						String columnName = method.getName().substring(3);
						columnName = Character.toLowerCase(columnName.charAt(0)) + columnName.substring(1);
						method.invoke(result, resultSet.getObject(columnName));
					}
				}

				resultList.add(result);
			}

			return resultList;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (Objects.nonNull(resultSet)) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
