package controller;

import dao.AppointmentDAO;
import dao.LocationDAO;
import dao.LoginDAO;
import dao.UserDAO;
import helper.Time;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for main/login window.
 */
public class MainController {
    private LoginDAO loginDao = new LoginDAO();
    private LocationDAO locationDao = new LocationDAO();
    private AppointmentDAO aDao = new AppointmentDAO();
    private UserDAO userDAO = new UserDAO();
    private String rbPath = "localization.Translation";
    private ResourceBundle rb = ResourceBundle.getBundle(rbPath, Locale.getDefault());
    private SceneController sc = new SceneController();

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label locationLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label locationTagLabel;

    /**
     * Initialize JavaFX nodes for time zone and locale.
     * Populate divisions/countries hashmaps.
     */
    public void initialize() {
        locationLabel.setText(Time.getLocalZoneID());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            sc.getStage().setTitle(rb.getString("Scheduling_Application"));
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("Login"));
            locationTagLabel.setText(rb.getString("Location_Tag"));
            loginDao.setAlertLanguage("fr").setContentText(rb.getString("Username_Password_invalid"));
            locationLabel.setText(rb.getString("French_Location"));
        }
        locationDao.setDivisionsHashMap();
        locationDao.setCountriesHashMap();
        aDao.setContactsHashMap();
        aDao.setAppointmentsHashMap();
    }

    /**
     * @param actionEvent Check login credentials and enter customer directory scene.
     */
    public void loginButtonClick(ActionEvent actionEvent) {
        String usernameInput = usernameTextField.getText();
        String passwordInput = passwordTextField.getText();
        boolean credentialFlag = false;
        credentialFlag = loginDao.checkCredentials(usernameInput, passwordInput);
        if (credentialFlag) {
            userDAO.setUsersHashMap();
            User user = new User(userDAO.getUserId(usernameInput),usernameInput);
            userDAO.setCurrentUser(user);
            trackLogin(user, credentialFlag);
            Time.checkUpcomingAppointments(aDao.getAppointments());
            SceneController sc = new SceneController();
            sc.enterCustomerDirectory();
        } else {
            userDAO.setUsersHashMap();
            User user = new User(userDAO.getUserId(usernameInput),usernameInput);
            trackLogin(user, credentialFlag);
        }
    }

    /**
     * @param user User object representing the logged-in user.
     * @param loginStatus A boolean indicating whether the login attempt was successful.
     */
    private void trackLogin(User user, boolean loginStatus) {
        String fileName = "login_activity.txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String timestampUTC = Time.getTimestampUTC();
            String logEntry = "[" + timestampUTC + "] User: " + user.getName() + ", Login: ";
            logEntry += loginStatus ? "Successful" : "Failed";
            bufferedWriter.write(logEntry);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("MainController IOException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
