package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC API implementation.
 * Only one connection needed for program, all static members.
 * Since connection is maintained during the life of the program, try with clauses not used.
 * Static methods within abstract classes can be called without inheritance in a subclass.
 */
public abstract class JDBC {
    private static String protocol = "jdbc";
    private static String vendor = ":mysql:";
    private static String location = "//localhost/";
    private static String databaseName = "client_schedule";
    private static String query = "?connectionTimeZone = SERVER"; // Local machine.
    private static String url = protocol + vendor + location + databaseName + query;
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String userName = "sqlUser";
    private static String password = "Passw0rd!";
    private static Connection connection;
    /**
     * Open JDBC connection.
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Java Database Connection opened.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver exception:" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("JDBC open SQL exception:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return Current JDBC instance.
     */
    public static Connection getConnection() {
        return connection;
    }
    /**
     * Close JDBC connection.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("JDBC closed.");
        } catch(SQLException e) {
            System.out.println("JDBC close SQL exception:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
