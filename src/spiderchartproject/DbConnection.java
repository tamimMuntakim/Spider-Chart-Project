package spiderchartproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection conn;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spider", "root", "root");

        if (conn != null) {
            System.out.println("OK");
        } else {
            System.out.println("error");
        }
        return conn;
    }
}
