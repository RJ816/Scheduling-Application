package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

/**
 * Controller for customer directory scene.
 */
public class CustomerDirectoryController {
    private SceneController sc = new SceneController();
    private CustomerDAO cDao = new CustomerDAO();
    private AppointmentDAO aDao = new AppointmentDAO();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private Alert eAlert = new Alert(Alert.AlertType.ERROR);
    private Alert cAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private TableView<Customer> table;
    @FXML
    private TableColumn<Customer, Integer> id;
    @FXML
    private TableColumn<Customer, String> name;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Customer, String> postalCode;
    @FXML
    private TableColumn<Customer, String> phone;
    @FXML
    private TableColumn<Customer, String> division;
    @FXML
    private TableColumn<Customer, String> country;

    /**
     * Initialize JavaFX nodes to display customer data.
     */
    public void initialize() {
        customers = cDao.populateTable();
        table.setItems(customers);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        division.setCellValueFactory(new PropertyValueFactory<>("division"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    /**
     * @param actionEvent Enter add customer scene.
     */
    public void addClick(ActionEvent actionEvent){
        sc.enterAddCustomer();
    }

    /**
     * @param actionEvent Pass selected customer data and enter update customer scene.
     */
    public void updateClick(ActionEvent actionEvent) {
        boolean selectedFlag = table.getSelectionModel().isEmpty();
        if (selectedFlag) {
            eAlert.setContentText("No customer selected in table.");
            eAlert.showAndWait();
        } else {
            Customer customer = table.getSelectionModel().getSelectedItem();
            cDao.setCustomer(customer);
            sc.enterUpdateCustomer(customer);
        }
    }

    /**
     * @param actionEvent Check if customer record has fk appointments, if not, delete.
     */
    public void deleteClick(ActionEvent actionEvent) {
        boolean selectedFlag = table.getSelectionModel().isEmpty();
        if (selectedFlag) {
            eAlert.setContentText("No customer selected in table.");
            eAlert.showAndWait();
        } else {
            Customer customer = table.getSelectionModel().getSelectedItem();
            int id = customer.getId();
            boolean flag = aDao.checkAppointments(id);
            if (flag) {
                cDao.deleteCustomerRecord(id);
                cAlert.setContentText("Customer record deleted.");
                cAlert.showAndWait();
                customers = cDao.populateTable();
                table.setItems(customers);
                this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                address.setCellValueFactory(new PropertyValueFactory<>("address"));
                postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
                division.setCellValueFactory(new PropertyValueFactory<>("division"));
                country.setCellValueFactory(new PropertyValueFactory<>("country"));
            }
        }
    }

    /**
     * @param actionEvent Enter appointment directory window.
     */
    public void appointmentClick(ActionEvent actionEvent) {
        sc.enterAppointmentDirectory();
    }

    /**
     * @param actionEvent Enter Reports window.
     */
    public void reportsClick(ActionEvent actionEvent) {
        sc.enterReports();
    }

    /**
     * @param actionEvent Close the application.
     */
    public void exitProgram(ActionEvent actionEvent) {
        Platform.exit();
    }
}

