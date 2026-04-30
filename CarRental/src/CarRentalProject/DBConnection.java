package CarRentalProject;

import java.sql.*;

public class DBConnection {
    static final String URL = "jdbc:mysql://localhost:3306/carrental";
    static final String USER = "root"; // change if needed
    static final String PASS = "root";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}