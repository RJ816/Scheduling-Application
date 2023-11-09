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

import java.util.ArrayList;

public class UpdateCustomerController {
    private SceneController sc = new SceneController();
    private ArrayList<Integer> idList = new ArrayList<>();
    private CustomerDAO cDao = new CustomerDAO();
    private LocationDAO lDao = new LocationDAO();
    private UserDAO uDao = new UserDAO();
    private ObservableList<String> countries = FXCollections.observableArrayList();
    private ObservableList<String> divisions = FXCollections.observableArrayList();
    private Alert eAlert = new Alert(Alert.AlertType.ERROR);
    private Alert iAlert = new Alert(Alert.AlertType.INFORMATION);

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
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> division;

    /** TODO 1 lambda expression used.
     * Fill customer form and lock division combo box.
     */
    public void initialize() {
        countries = FXCollections.observableArrayList(lDao.getCountryNames());
        country.setItems(countries);
        setForm(cDao.getCustomer());
        String selectedCountry = country.getValue();
        Integer countryId = lDao.getCountryIdByCountryName(selectedCountry);
        divisions = FXCollections.observableArrayList(lDao.getFilteredDivisions(countryId));
        division.setItems(divisions);
        country.setOnAction(selectionChange -> {activateDivision();});
    }

    /**
     * Filter divisions based on country selection.
     */
    public void activateDivision() {
        String selectedCountry = country.getValue();
        Integer countryId = lDao.getCountryIdByCountryName(selectedCountry);
        divisions = FXCollections.observableArrayList(lDao.getFilteredDivisions(countryId));
        division.setItems(divisions);
    }

    /**
     * @param customer Customer object data to fill form.
     */
    private void setForm(Customer customer) {
        this.id.setText(String.valueOf(customer.getId()));
        this.name.setText(customer.getName());
        this.address.setText(customer.getAddress());
        this.postalCode.setText(customer.getPostalCode());
        this.phone.setText(customer.getPhone());
        this.country.setValue(customer.getCountry());
        this.division.setValue(customer.getDivision());
    }

    /**
     * @param actionEvent Create customer record and send to database to update if valid.
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
            String create = cDao.getCustomer().getCreate();
            String createBy = cDao.getCustomer().getCreateBy();
            String update = Time.getTimestampUTC();
            String updateBy = uDao.getCurrentUser().getName();;
            String division = this.division.getValue();
            Integer divisionId = lDao.getDivisionId(division);
            if (id == null || name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || division.isEmpty()) {
                throw new NullPointerException();
            }
            Customer customer = new Customer (id, name, address, postalCode, phone, create, createBy, update, updateBy, divisionId);
            if (customer.equalsSelective(cDao.getCustomer())) {
                iAlert.setContentText("No updates made to customer.");
                iAlert.showAndWait();
            } else {
                cDao.updateCustomerRecord(customer);
            }
            sc.enterCustomerDirectory();
        } catch (NullPointerException e) {
            eAlert.setContentText("Fill all fields to save customer.");
            eAlert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent Go back to customer directory scene.
     */
    public void cancelClick(ActionEvent actionEvent) {
        sc.enterCustomerDirectory();
    }
}
