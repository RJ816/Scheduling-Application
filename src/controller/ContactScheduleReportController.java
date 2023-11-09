package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

/**
 * Controller for contact schedule report.
 */
public class ContactScheduleReportController {
    private SceneController sc = new SceneController();
    private AppointmentDAO dao = new AppointmentDAO();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<String> contacts = FXCollections.observableArrayList();
    private Alert eAlert = new Alert(Alert.AlertType.ERROR);
    private Alert cAlert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private TableView<Appointment> table;
    @FXML
    private TableColumn<Appointment, Integer> appointmentId;
    @FXML
    private TableColumn<Appointment, String> title;
    @FXML
    private TableColumn<Appointment, String> description;
    @FXML
    private ComboBox contact;
    @FXML
    private TableColumn<Appointment, String> type;
    @FXML
    private TableColumn<Appointment, String> start;
    @FXML
    private TableColumn<Appointment, String> end;
    @FXML
    private TableColumn<Appointment, Integer> customerId;

    /**
     * Initialize JavaFX nodes to display appointment data.
     */
    public void initialize() {
        dao.setContactsHashMap();
        contacts = FXCollections.observableArrayList(dao.getContactNames());
        contact.setItems(contacts);
        appointments = dao.populateTable();
        table.setItems(appointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        end.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * @param actionEvent Filter tableview with appointments for selected contact.
     */
    public void filter (ActionEvent actionEvent) {
        appointments = dao.getContactAppointments(String.valueOf(contact.getValue()));
        table.setItems(appointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        end.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * @param actionEvent Return to previous screen.
     */
    public void cancelClick(ActionEvent actionEvent) {
        sc.enterReports();
    }
}

