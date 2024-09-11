import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    final static String URL = "jdbc:mysql://localhost:3306";
    final static String USER = "root";
    final static String PASS = "root";

    public static Connection connect() {
        Connection conn;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected to the database.");
            return conn;
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }

    public static void createDB(Connection conn) {
        try {
            String query = "CREATE DATABASE IF NOT EXISTS HR;";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating the database: " + e.getMessage());
        }
    }

    public static void createTable(Connection conn, String name, String[] columns) {
        try {
            String columnStr = String.join(", ", columns);
            String query = "CREATE TABLE hr." + name + " (" + columnStr + ");";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating the table: " + e.getMessage());
        }
    }

    public static void insert(Connection conn, String query) {
        try {
            System.out.println(query);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            System.out.println("Data inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }

    public static void update(Connection conn, String query) {
        try {
            System.out.println(query);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            System.out.println("Data updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
    }

    public static void delete(Connection conn, String query) {
        try {
            System.out.println(query);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            System.out.println("Data deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting data: " + e.getMessage());
        }
    }

    public static void select(Connection conn, String query) {
        try {
            System.out.println(query);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error selecting data: " + e.getMessage());
        }
    }
}