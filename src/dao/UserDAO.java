package dao;

import helper.JDBC;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Data access object for users table.
 */
public class UserDAO {
    private static User currentUser;
    private static HashMap<Integer, User> users = new HashMap<>();

    /**
     * Populate user database data into a hashmap.
     */
    public void setUsersHashMap(){
        int id;
        String name;
        users.clear();
        String query = "SELECT User_ID, User_Name FROM users;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("User_ID");
                name = rs.getString("User_Name");
                User user = new User(id, name);
                users.put(id, user);
            }
        } catch (SQLException e) {
            System.out.println("UserDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** TODO Contains 1 method reference (lambda expression) and 1 lambda expression.
     * @param name User name.
     * @return User ID.
     */
    public Integer getUserId(String name) {
        User[] userMatch =
                users.values().stream().filter(uObj -> uObj.getName().equals(name)).toArray(User[]::new);
        Integer id = userMatch[0].getId(); //TODO rewrite.
        return id;
    }

    /**
     * @param currentUser Current user of the application.
     */
    public void setCurrentUser(User currentUser) {
        UserDAO.currentUser = currentUser;
    }

    /**
     * @return Current User object.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /** TODO Contains 1 method reference (lambda expression).
     * @return Array of user names.
     */
    public String[] getUserNames() {
        return users.values().stream().map(User::getName).toArray(String[]::new);
    }

    /** TODO Contains 1 lambda expression.
     * @param id User ID.
     * @return User name.
     */
    public String getUserNameById(Integer id) {
        User[] userMatch =
                users.values().stream().filter(uObj -> uObj.getId() == (id)).toArray(User[]::new);
        String name = userMatch[0].getName(); //TODO rewrite.
        return name;
    }
}
