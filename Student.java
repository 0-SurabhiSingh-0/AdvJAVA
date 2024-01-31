import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Student {

	public Student() {
		super();
	}

	public void insertStudent(Connection con, Scanner sc) throws SQLException, ClassNotFoundException {
		Statement st = con.createStatement();
		System.out.println("Enter student Id: ");
		int id = sc.nextInt();

		System.out.println("Enter Student Name: ");
		String name = sc.next();

		System.out.println("Enter Student Age: ");
		int age = sc.nextInt();

		System.out.println("Enter Student Marks: ");
		double marks = sc.nextDouble();

		System.out.println("Enter Student Gender: ");
		String gender = sc.next();

		// insert into students values (555, 'Manphar', 22, 98.0, 'male')
		String query = String.format("insert into students values(%d, '%s', %d, %f, '%s')", id, name, age, marks,
				gender);

		int rowsAffected = st.executeUpdate(query);
		System.out.println(rowsAffected + " record inserted!!!");
	}

	public void displayStudents(Connection con) throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from students");
		while (rs.next()) {
			System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getInt(3) + "  " + rs.getDouble(4)
					+ "  " + rs.getString(5));
		}
	}

	public void updateStudent(Connection con, Scanner sc) throws SQLException {
		Statement st = con.createStatement();
		System.out.println("Enter Student's Id: ");
		int id = sc.nextInt();
		System.out.println("Enter Student new Name: ");
		String name = sc.next();
		String query = String.format("update students set name='%s' where id = %d", name, id);
		int rowsAffected = st.executeUpdate(query);
		System.out.println(rowsAffected + " record updated!!!");
	}

	public void removeStudent(Connection con, Scanner sc) throws SQLException {
		Statement st = con.createStatement();
		System.out.println("Enter Student Id: ");
		int id = sc.nextInt();
		int rowsAffected = st.executeUpdate("delete from students where id = " + id);
		System.out.println(rowsAffected + " record deleted!!!");
	}

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/surbhi", "root", "s123");

			Student student = new Student();
			boolean exit = false;

			while (!exit) {
				System.out.println("Choose an option:");
				System.out.println("1. Insert Student");
				System.out.println("2. Display Students");
				System.out.println("3. Update Student");
				System.out.println("4. Remove Student");
				System.out.println("5. Exit");
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					student.insertStudent(con, scanner);
					break;
				case 2:
					student.displayStudents(con);
					break;
				case 3:
					student.updateStudent(con, scanner);
					break;
				case 4:
					student.removeStudent(con, scanner);
					break;
				case 5:
					exit = true;
					break;
				default:
					System.out.println("Invalid choice. Please enter a number between 1 and 5.");
					break;
				}
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
