import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LibraryManagementSystem {

    public LibraryManagementSystem() {
        super();
    }

    public void addBook(Connection con, Scanner sc) throws SQLException {
        Statement st = con.createStatement();
        System.out.println("Enter Book ISBN: ");
        String isbn = sc.next();

        System.out.println("Enter Book Title: ");
        String title = sc.next();

        System.out.println("Enter Author Name: ");
        String author = sc.next();

        System.out.println("Enter Publication Year: ");
        int year = sc.nextInt();

        System.out.println("Enter Available Copies: ");
        int copies = sc.nextInt();

        // Assuming all books are initially available
        boolean availability = true;

        String insertQuery = String.format("insert into books values('%s', '%s', '%s', %d, %d, %b)", isbn, title, author,
                year, copies, availability);

        int rowsAffected = st.executeUpdate(insertQuery);
        System.out.println(rowsAffected + " book added to the library!!!");
    }

    public void displayBooks(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from books");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-70s | %-30s | %-4s | %-6s | %-12s |\n",
                "ISBN", "Title", "Author", "Year", "Copies", "Availability");
        System.out.println("--------------------------------------------------------------------------------------");
        while (rs.next()) {
            System.out.printf("| %-20s | %-70s | %-30s | %-4d | %-6d | %-12b |\n",
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6));
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }

    public void updateBook(Connection con, Scanner sc) throws SQLException {
        Statement st = con.createStatement();
        System.out.println("Enter Book ISBN to update: ");
        String isbn = sc.next();
        System.out.println("Enter New Title: ");
        String title = sc.next();
        System.out.println("Enter New Availability (true/false): ");
        boolean availability = sc.nextBoolean();

        String query = String.format("update books set title='%s', availability=%b where isbn = '%s'", title, availability, isbn);
        int rowsAffected = st.executeUpdate(query);
        System.out.println(rowsAffected + " book updated in the library!!!");
    }

    public void removeBook(Connection con, Scanner sc) throws SQLException {
        Statement st = con.createStatement();
        System.out.println("Enter Book ISBN to remove: ");
        String isbn = sc.next();
        int rowsAffected = st.executeUpdate("delete from books where isbn = '" + isbn + "'");
        System.out.println(rowsAffected + " book removed from the library!!!");
    }

    public void displayNumberOfBooks(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select count(*) from books");
        if (rs.next()) {
            System.out.println("Number of books in the library: " + rs.getInt(1));
        }
    }

    public void displayAvailableBooks(Connection con) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from books where availability = true");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-70s | %-30s | %-4s | %-6s | %-12s |\n",
                "ISBN", "Title", "Author", "Year", "Copies", "Availability");
        System.out.println("--------------------------------------------------------------------------------------");
        while (rs.next()) {
            System.out.printf("| %-20s | %-70s | %-30s | %-4d | %-6d | %-12b |\n",
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6));
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/surbhi", "root", "s123");
            System.out.println("Connected to the database: " + con.getCatalog());

            LibraryManagementSystem library = new LibraryManagementSystem();
            boolean exit = false;

            while (!exit) {
                System.out.println("Choose an option:");
                System.out.println("1. Add Book");
                System.out.println("2. Display Books");
                System.out.println("3. Update Book");
                System.out.println("4. Remove Book");
                System.out.println("5. Display Number of Books");
                System.out.println("6. Display Available Books");
                System.out.println("7. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        library.addBook(con, scanner);
                        break;
                    case 2:
                        library.displayBooks(con);
                        break;
                    case 3:
                        library.updateBook(con, scanner);
                        break;
                    case 4:
                        library.removeBook(con, scanner);
                        break;
                    case 5:
                        library.displayNumberOfBooks(con);
                        break;
                    case 6:
                        library.displayAvailableBooks(con);
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                        break;
                }
            }
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
