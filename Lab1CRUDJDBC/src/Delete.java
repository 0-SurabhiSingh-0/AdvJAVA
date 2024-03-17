import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {

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
	                String deleteQuery = "DELETE FROM Products WHERE id = 2";
	                int rowsAffected = statement.executeUpdate(deleteQuery);
	                System.out.println(rowsAffected + " row(s) deleted from the 'Products' table.");

	                String selectQuery = "SELECT * FROM Products";
	                ResultSet resultSet = statement.executeQuery(selectQuery);
	                while (resultSet.next()) {
	                    int id = resultSet.getInt("id");
	                    String name = resultSet.getString("Name");
	                    double price = resultSet.getDouble("Price");
	                    int quantity = resultSet.getInt("Quantity");
	                    System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity: " + quantity);
	                }
	            } catch (SQLException e) {
	                System.out.println("Failed to execute query: " + e.getMessage());
	            }
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}}