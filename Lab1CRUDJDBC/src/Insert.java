import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException; 
import java.sql.Statement;

public class Insert {

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
				 String insertDataSQL = "INSERT INTO Products (Name, Price, Quantity) VALUES "
			                + "('Apple', 2.50, 100), "
			                + "('Banana', 1.50, 150)";
				int rowsAffected = statement.executeUpdate(insertDataSQL);
				System.out.println(rowsAffected + " row(s) inserted into the 'Products' table.");
			} catch (SQLException e) {
				System.out.println("Failed to insert data: " + e.getMessage());

			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}