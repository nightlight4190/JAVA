import java.sql.*;

public class EmployeeDatabase {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeeDB";
    private static final String USER = "root"; // MySQL username
    private static final String PASSWORD = "root"; // MySQL password

    // JDBC variables for opening and managing connection
    private static Connection conn;

    public static void main(String[] args) {
        try {
            // Step 1: Open a connection to MySQL database
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");

            // Step 2: Create a statement object
            Statement stmt = conn.createStatement();

            // Step 3: Execute multiple SQL queries

            // Insert a new employee
            String insertQuery = "INSERT INTO Employees (name, city, gender, salary) " +
                    "VALUES ('John Doe', 'Kathmandu', 'Male', 50000)";
            stmt.executeUpdate(insertQuery);
            System.out.println("Employee inserted.");

            // Retrieve all employees
            String selectQuery = "SELECT * FROM Employees";
            ResultSet rs = stmt.executeQuery(selectQuery);

            // Step 4: Process the result set
            System.out.println("Employee List:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String city = rs.getString("city");
                String gender = rs.getString("gender");
                double salary = rs.getDouble("salary");

                System.out.println(id + ": " + name + ", " + city + ", " + gender + ", " + salary);
            }

            // Update an employee's salary
            String updateQuery = "UPDATE Employees SET salary = 55000 WHERE name = 'John Doe'";
            stmt.executeUpdate(updateQuery);
            System.out.println("Employee salary updated.");

            // Delete an employee
            String deleteQuery = "DELETE FROM Employees WHERE name = 'John Doe'";
            stmt.executeUpdate(deleteQuery);
            System.out.println("Employee deleted.");

            // Step 5: Close the connection
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
