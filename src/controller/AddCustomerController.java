package controller;

import dao.CustomerDAO;
import dao.LocationDAO;
import dao.UserDAO;
import helper.Format;
import helper.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Customer;

/**
 * Controller for add customer scene.
 */
public class AddCustomerController {
    private SceneController sc = new SceneController();
    private CustomerDAO cDao = new CustomerDAO();
    private LocationDAO lDao = new LocationDAO();
    private UserDAO uDao = new UserDAO();
    private ObservableList<String> countries = FXCollections.observableArrayList();
    private ObservableList<String> divisions = FXCollections.observableArrayList();
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField phone;
    @FXML
    private ComboBox country;
    @FXML
    private ComboBox division;

    /**
     * Generate a new unique customer id.
     * Fill combo boxes with divisions/countries.
     */
    public void initialize() {
        id.setText(cDao.getId().toString());
        countries = FXCollections.observableArrayList(lDao.getCountryNames());
        country.setItems(countries);
    }

    /**
     * @param actionEvent Create customer record and send to database.
     */
    public void saveClick(ActionEvent actionEvent) {
        try {
            Integer id = Integer.valueOf(this.id.getText());
            String name = this.name.getText();
            String address = this.address.getText();
            Format format = new Format();
            format.checkAddress(address);
            String postalCode = this.postalCode.getText();
            String phone = this.phone.getText();
            String create = Time.getTimestampUTC();
            String createBy = uDao.getCurrentUser().getName();
            String update = Time.getTimestampUTC();
            String updateBy = uDao.getCurrentUser().getName();
            String division = this.division.getValue().toString();
            Integer divisionId = lDao.getDivisionId(division);
            if (name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || division.isEmpty()) {
                throw new NullPointerException();
            }
            Customer customer = new Customer (id, name, address, postalCode, phone, create, createBy, update, updateBy, divisionId);
            cDao.addCustomerRecord(customer);
            sc.enterCustomerDirectory();
        } catch (NullPointerException e) {
            alert.setContentText("Fill all fields to save customer.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent Filter divisions based on country selection.
     */
    public void activateDivision(ActionEvent actionEvent) {
        String selectedCountry = country.getValue().toString();
        Integer countryId = lDao.getCountryIdByCountryName(selectedCountry);
        divisions = FXCollections.observableArrayList(lDao.getFilteredDivisions(countryId));
        division.setItems(divisions);
    }

    /**
     * @param actionEvent Go back to customer directory scene.
     */
    public void cancelClick(ActionEvent actionEvent) {
        SceneController sc = new SceneController();
        sc.enterCustomerDirectory();
    }
}
