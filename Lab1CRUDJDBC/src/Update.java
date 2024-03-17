import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Update {

	public static Connection connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/surbhi";
		String username = "root";
		String password = "s123";

		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}

	
		public static void updateData() {
	        try (Connection conn = connect();
	             Statement statement = conn.createStatement()) {
	            String updateQuery = "UPDATE Products SET Name = 'Updated Product', Price = 15.99, Quantity = 200 WHERE id = 1";
	            int rowsAffected = statement.executeUpdate(updateQuery);
	            System.out.println(rowsAffected + " row(s) updated in the 'Products' table.");

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
	            System.out.println("Error: " + e.getMessage());
	        }
	    }

	    public static void main(String[] args) {
	        // Update data in the "Products" table
	        updateData();
	    }
	}
