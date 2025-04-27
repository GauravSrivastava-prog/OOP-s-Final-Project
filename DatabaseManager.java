import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/LiteraryWorkspaceDB";
    private static final String USER = "root";
    private static final String PASS = "rootuser";

    private static Connection connection;

    public static void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database.");
        }
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from the database.");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    public static boolean createUser(String username, String password, String role) {
        Connection conn = null;
        PreparedStatement checkstmt = null;
        PreparedStatement insertstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String checkUser = "SELECT * FROM users WHERE username = ?";
            checkstmt = conn.prepareStatement(checkUser);
            checkstmt.setString(1, username);
            rs = checkstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Username already exists.");
                return false; // Username already exists, return false
            }

            String insertUser = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            insertstmt = conn.prepareStatement(insertUser);
            insertstmt.setString(1, username);
            insertstmt.setString(2, password);
            insertstmt.setString(3, role);
            insertstmt.executeUpdate();

            System.out.println("User created successfully.");
            return true; // User created successfully

        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
            return false;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (checkstmt != null)
                    checkstmt.close();
                if (insertstmt != null)
                    insertstmt.close();
                if (conn != null)
                    conn.close(); // Close connection to prevent resource leakage
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try {
            connect();
            String authuser = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement authstmt = connection.prepareStatement(authuser);
            authstmt.setString(1, username);
            authstmt.setString(2, password);
            ResultSet rs = authstmt.executeQuery();

            if (rs.next()) {
                System.out.println("User authenticated successfully.");
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error authenticating user");
            return false;
        }
    }
}