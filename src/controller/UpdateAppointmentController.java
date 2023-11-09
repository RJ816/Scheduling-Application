package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import helper.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Appointment;

import java.time.LocalDate;

/**
 * Update appointment scene controller.
 */
public class UpdateAppointmentController {
    private SceneController sc = new SceneController();
    private AppointmentDAO aDao = new AppointmentDAO();
    private CustomerDAO cDao = new CustomerDAO();
    private UserDAO uDao = new UserDAO();
    private ObservableList<String> contacts = FXCollections.observableArrayList();
    private ObservableList<String> customers = FXCollections.observableArrayList();
    private ObservableList<String> users = FXCollections.observableArrayList();
    private ObservableList<String> times = FXCollections.observableArrayList();
    private Alert eAlert = new Alert(Alert.AlertType.ERROR);
    private Alert iAlert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private TextField appointmentId;
    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField loc;
    @FXML
    private ComboBox contact;
    @FXML
    private TextField type;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox start;
    @FXML
    private ComboBox end;
    @FXML
    private ComboBox customer;
    @FXML
    private ComboBox user;

    /**
     * Generate a new unique appointment id.
     * Fill combo boxes with contact and user names, appointment times in 15 minute intervals.
     */
    public void initialize() {
        setForm(aDao.getAppointment());
        aDao.deleteUpdateAppointment(Integer.parseInt(appointmentId.getText()));
        contacts = FXCollections.observableArrayList(aDao.getContactNames());
        contact.setItems(contacts);
        customers = FXCollections.observableArrayList(cDao.getCustomerNames());
        customer.setItems(customers);
        users = FXCollections.observableArrayList(uDao.getUserNames());
        user.setItems(users);
        times = FXCollections.observableArrayList(Time.getTimeList());
        start.setItems(times);
        end.setItems(times);
    }

    /**
     * @param actionEvent Create appointment record and send to database.
     */
    public void saveClick(ActionEvent actionEvent) {
        try {
            Integer appointmentId = Integer.valueOf(this.appointmentId.getText());
            String title = this.title.getText();
            String description = this.description.getText();
            String loc = this.loc.getText();
            String contact = this.contact.getValue().toString();
            Integer contactId = aDao.getContactIdByContactName(contact);
            String type = this.type.getText();
            LocalDate date = this.date.getValue();
            String start = this.start.getValue().toString();
            String end = this.end.getValue().toString();
            Time.isStartTimeAfterEndTime(start, end);
            String startCombine = Time.combineDateTime(date, start);
            String endCombine = Time.combineDateTime(date, end);
            Time.checkET(startCombine, endCombine);
            String datetimeStart = Time.convertLocalToUTC(startCombine);
            String datetimeEnd = Time.convertLocalToUTC(endCombine);
            String create = Time.getTimestampUTC();
            String createBy = uDao.getCurrentUser().getName();
            String update = Time.getTimestampUTC();
            String updateBy = uDao.getCurrentUser().getName();
            String customer = this.customer.getSelectionModel().getSelectedItem().toString();
            Integer customerId = cDao.getCustomerIdByCustomerName(customer);
            Time.checkAppointmentConflict(datetimeStart, datetimeEnd, customerId);
            String user = this.user.getValue().toString();
            Integer userId = uDao.getUserId(user);
            if (title.isEmpty() || description.isEmpty() || loc.isEmpty() || contact.isEmpty() || type.isEmpty() ||
                    date == null || start.isEmpty() || end.isEmpty() || customer.isEmpty() || user.isEmpty()) {
                throw new NullPointerException();
            }
            Appointment appointment = new Appointment(appointmentId, title, description, loc, type, datetimeStart,
                    datetimeEnd, create, createBy, update, updateBy, customerId, userId, contactId);
            if (appointment.equalsSelective(aDao.getAppointment())) {
                iAlert.setContentText("No updates made to customer.");
                iAlert.showAndWait();
            } else {
                aDao.updateAppointmentRecord(appointment);
            }
            sc.enterAppointmentDirectory();
        } catch (NullPointerException e) {
            eAlert.setContentText("Fill all fields to save appointment.");
            eAlert.showAndWait();
        } catch (Exception e) {
            System.out.println("AddAppointmentController saveClick exception.");
        }
    }

    /**
     * @param actionEvent Enter appointment directory window.
     */
    public void cancelClick(ActionEvent actionEvent) {
        sc.enterAppointmentDirectory();
    }

    /**
     * @param appointment Appointment object data to fill form.
     */
    private void setForm(Appointment appointment) {
        this.appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        this.title.setText(appointment.getTitle());
        this.description.setText(appointment.getDescription());
        this.loc.setText(appointment.getLocation());
        this.contact.setValue(appointment.getContactName());
        this.type.setText(appointment.getType());
        this.date.setValue(Time.getDate(appointment.getStartLocal()));
        this.start.setValue(Time.getTime(appointment.getStartLocal()));
        this.end.setValue(Time.getTime(appointment.getEndLocal()));
        this.customer.setValue(cDao.getCustomerNameById(appointment.getCustomerId()));
        this.user.setValue(uDao.getUserNameById(appointment.getUserId()));
    }
}
