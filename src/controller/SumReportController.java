package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Appointment Sum Report Controller
 */
public class SumReportController {
    private SceneController sc = new SceneController();
    private AppointmentDAO dao = new AppointmentDAO();
    private ObservableList<String> types = FXCollections.observableArrayList();
    private ObservableList<String> months = FXCollections.observableArrayList();

    @FXML
    private ComboBox type;
    @FXML
    private ComboBox month;
    @FXML
    private Label sum;

    /**
     * Fill combo boxes with types and months.
     */
    public void initialize() {
        dao.setAppointmentsHashMap();
        types = FXCollections.observableArrayList(dao.getTypes());
        type.setItems(types);
        months = FXCollections.observableArrayList(dao.getMonths());
        month.setItems(months);
    }

    /**
     * @param actionEvent Calculate sum of appointments
     */
    public void calculateClick(ActionEvent actionEvent) {
        String type = String.valueOf(this.type.getValue());
        String month = String.valueOf(this.month.getValue());
        int sum = dao.countAppointments(type, month);
        this.sum.setText(String.valueOf(sum));
    }

    /**
     * @param actionEvent Enter previous screen.
     */
    public void cancelClick(ActionEvent actionEvent) {
        sc.enterReports();
    }
}
