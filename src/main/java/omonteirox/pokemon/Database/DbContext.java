package omonteirox.pokemon.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import omonteirox.pokemon.Exceptions.DatabaseException;


public class DbContext {
	private static final String driver = "org.postgresql.Driver";
	private static final String username = "postgres";
	private static final String password = "1234";
	private static final String url = "jdbc:postgresql://localhost:5432/pokemon_api_test";
	
	public static Connection Connect() {
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		return connection;
	}
	public static void Disconnect(Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	public static void ClosePreparedStatement(PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	public static void CloseResultSet(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
		
	}
}
