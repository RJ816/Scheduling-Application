package controller;

import dao.AppointmentDAO;
import helper.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

/**
 * Controller for appointment directory scene.
 */
public class AppointmentDirectoryController {
    private SceneController sc = new SceneController();
    private AppointmentDAO dao = new AppointmentDAO();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
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
    private TableColumn<Appointment, String> loc; //location is FXML Loader KEYWORD!!!
    @FXML
    private TableColumn<Appointment, String> contact;
    @FXML
    private TableColumn<Appointment, String> type;
    @FXML
    private TableColumn<Appointment, String> start;
    @FXML
    private TableColumn<Appointment, String> end;
    @FXML
    private TableColumn<Appointment, Integer> customerId;
    @FXML
    private TableColumn<Appointment, Integer> userId;

    /**
     * Initialize JavaFX nodes to display appointment data.
     */
    public void initialize() {
        dao.setContactsHashMap();
        appointments = dao.populateTable();
        appointments = Time.nextWeeksAppointments(dao.getAppointments());
        table.setItems(appointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        end.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * @param actionEvent Filter tableview with appointments in next 7 days.
     */
    public void filterWeekly (ActionEvent actionEvent) {
        appointments = Time.nextWeeksAppointments(dao.getAppointments());
        table.setItems(appointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        end.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * @param actionEvent Filter tableview with appointments in next 31 days.
     */
    public void filterMonthly (ActionEvent actionEvent) {
        appointments = Time.nextMonthsAppointments(dao.getAppointments());
        table.setItems(appointments);
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        end.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * @param actionEvent Enter add appointment scene.
     */
    public void addClick(ActionEvent actionEvent){
        sc.enterAddAppointment();
    }

    /**
     * @param actionEvent Pass selected appointment data and enter update appointment scene.
     */
    public void updateClick(ActionEvent actionEvent) {
        boolean selectedFlag = table.getSelectionModel().isEmpty();
        if (selectedFlag) {
            eAlert.setContentText("No customer selected in table.");
            eAlert.showAndWait();
        } else {
            Appointment appointment = table.getSelectionModel().getSelectedItem();
            dao.setAppointment(appointment);
            sc.enterUpdateAppointment();
        }
    }

    /**
     * @param actionEvent Cancel selected appointment.
     */
    public void deleteClick(ActionEvent actionEvent) {
        boolean selectedFlag = table.getSelectionModel().isEmpty();
        if (selectedFlag) {
            eAlert.setContentText("No appointment selected in table.");
            eAlert.showAndWait();
        } else {
            Appointment appointment = table.getSelectionModel().getSelectedItem();
            int id = appointment.getAppointmentId();
            String type = appointment.getType();
            dao.deleteAppointmentRecord(id);
            cAlert.setContentText("Appointment ID " + id + " and type " + type + " canceled.");
            cAlert.showAndWait();
            appointments = dao.populateTable();
            appointments = Time.nextWeeksAppointments(dao.getAppointments());
            table.setItems(appointments);
            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            loc.setCellValueFactory(new PropertyValueFactory<>("location"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            this.type.setCellValueFactory(new PropertyValueFactory<>("type"));
            start.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
            end.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
    }

    /**
     * @param actionEvent Enter Customer Directory window.
     */
    public void cancelClick(ActionEvent actionEvent) {
        sc.enterCustomerDirectory();
    }
}
