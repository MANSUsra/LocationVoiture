package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagement {
    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "";

    public void createDatabase(String databaseName) {
        try {
        	
            conn = DriverManager.getConnection(url, username, password);
            Statement statement = conn.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            statement.executeUpdate(sql);
            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    public void connect(String databaseName) {
        String fullUrl = url + databaseName;
        
        try {
            conn = DriverManager.getConnection(fullUrl, username, password);
            System.out.println("Connected to database: " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

//    public static void main(String[] args) {
//        DatabaseManagement dbManagement = new DatabaseManagement();
//        
//        // Create a new database
//        String databaseName = "your_database_name";
//        dbManagement.createDatabase(databaseName);
//        
//        // Connect to the database
//        dbManagement.connect(databaseName);
//        
//        // Use the connection for further database operations
//        Connection connection = dbManagement.getConnection();
//        // Perform your database operations using the 'connection' object
//        
//        // Close the connection
//        dbManagement.closeConnection();
//    }
//}