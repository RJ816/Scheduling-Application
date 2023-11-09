package dao;

import helper.JDBC;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object class for login/main scene.
 */
public class LoginDAO {
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private String lgCheck = "en";

    /**
     * @param username Username typed and entered by user.
     * @param password Password typed and entered by user.
     * @return True if credentials valid, false otherwise.
     */
    public boolean checkCredentials (String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE User_Name = ? AND Password = ?;";
        try {
            //SQL injection risk.
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            int rowCount = 0;
            if (rs.next()) {
                rowCount = rs.getInt(1);
                if (rowCount == 0) {
                    if (lgCheck.equals("en")) {
                        alert.setContentText("Username/Password invalid.");
                    }
                    alert.showAndWait();
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("LoginDAO SQLException:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param language Locale language used.
     * @return Object alert attribute.
     */
    public Alert setAlertLanguage (String language) {
        lgCheck = language;
        return alert;
    }
}
