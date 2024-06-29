package core.jdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JdbcTemplate {
	private JdbcTemplate() {}

	public static int executeUpdate(String sql, Map<Integer, String> parameterMap) {
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

	public static <T> List<T> executeQuery(Class<T> clazz, String sql) {
		return executeQuery(clazz, sql, null);
	}

	public static <T> List<T> executeQuery(Class<T> clazz, String sql, Map<Integer, String> parameterMap) {
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
