import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateConnection {
	public static Connection connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/surbhi";
		String username = "root";
		String password = "s123";

		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}

	public static void main(String[] args) {
		try {
			Connection connection = connect();
			System.out.println("Connected to the database: " + connection.getCatalog());

			try (Statement statement = connection.createStatement()) {
				String createTableSQL = "CREATE TABLE IF NOT EXISTS Products ("
		                + "id INT AUTO_INCREMENT PRIMARY KEY,"
		                + "Name VARCHAR(255),"
		                + "Price DECIMAL(10, 2),"
		                + "Quantity INT"
		                + ")";

				statement.executeUpdate(createTableSQL);
				System.out.println("Table 'Products' created successfully.");
			} catch (SQLException e) {
				System.out.println("Failed to create table: " + e.getMessage());
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}