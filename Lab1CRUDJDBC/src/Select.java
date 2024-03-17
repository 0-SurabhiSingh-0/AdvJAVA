import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Select {

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
				String selectQuery = "SELECT * FROM Products";
				ResultSet resultSet = statement.executeQuery(selectQuery);

				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String Name = resultSet.getString("Name");
					String Price = resultSet.getString("Price");
					String Quantity = resultSet.getString("Quantity");

					System.out.println("ID: " + id + ", Name: " + Name + ", Price: " + Price + ", Quantity: "
							+ Quantity);
				}
			} catch (SQLException e) {
				System.out.println("Failed to execute query: " + e.getMessage());
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
