package controller;

import javafx.event.ActionEvent;

/**
 * Controller for reports selection scene.
 */
public class ReportsController {
    private SceneController sc = new SceneController();

    /**
     * @param actionEvent Enter report.
     */
    public void sumClick(ActionEvent actionEvent) {
        sc.enterAppointmentSumReport();
    }

    /**
     * @param actionEvent Enter contact schedule report.
     */
    public void scheduleClick(ActionEvent actionEvent) {
        sc.enterContactScheduleReport();
    }

    /**
     * @param actionEvent Enter appointment location sum report.
     */
    public void locationClick(ActionEvent actionEvent) {
        sc.enterLocationSumReport();
    }

    /**
     * @param actionEvent Return to previous screen.
     */
    public void cancelClick(ActionEvent actionEvent) {
        sc.enterCustomerDirectory();
    }
}
