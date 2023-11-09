package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Controller for location sum report view.
 */
public class LocationSumReportController {
    private SceneController sc = new SceneController();
    private AppointmentDAO dao = new AppointmentDAO();
    private ObservableList<String> locations = FXCollections.observableArrayList();

    @FXML
    private ComboBox loc;
    @FXML
    private Label sum;

    /**
     * Fill combo box with locations.
     */
    public void initialize() {
        dao.setAppointmentsHashMap();
        locations = FXCollections.observableArrayList(dao.getLocations());
        loc.setItems(locations);
    }

    /**
     * @param actionEvent Calculate sum of appointments filtered by locations.
     */
    public void calculateClick(ActionEvent actionEvent) {
        String location = String.valueOf(this.loc.getValue());
        int sum = dao.countLocations(location);
        this.sum.setText(String.valueOf(sum));
    }

    /**
     * @param actionEvent Enter previous screen.
     */
    public void cancelClick(ActionEvent actionEvent) {
        sc.enterReports();
    }
}
