package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;

/**
 * Class to manage scene switches in application.
 */
public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private String cdvPath = "../view/CustomerDirectoryView.fxml";
    private String cdTitle = "Customer Directory";
    private String acvPath = "../view/AddCustomerView.fxml";
    private String acTitle = "Add Customer";
    private String ucvPath = "../view/UpdateCustomerView.fxml";
    private String ucTitle = "Update Customer";
    private String advPath = "../view/AppointmentDirectoryView.fxml";
    private String adTitle = "Appointment Directory";
    private String aavPath = "../view/AddAppointmentView.fxml";
    private String aaTitle = "Add Appointment";
    private String uavPath = "../view/UpdateAppointmentView.fxml";
    private String uaTitle = "Update Appointment";
    private String rvPath = "../view/ReportsView.fxml";
    private String rTitle = "Reports";
    private String srvPath = "../view/SumReportView.fxml";
    private String srTitle = "Appointment Sum Report";
    private String csrvPath = "../view/ContactScheduleReportView.fxml";
    private String csrTitle = "Contact Schedule Report";
    private String lsrvPath = "../view/LocationSumReportView.fxml";
    private String lsrTitle = "Location Sum Report";

    /**
     * @param stage Primary window.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return Primary window.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param scene Primary scene.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Switch to customer directory window.
     */
    public void enterCustomerDirectory() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(cdvPath));
            scene = new Scene(root);
            stage.setTitle(cdTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to add customer window.
     */
    public void enterAddCustomer() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(acvPath));
            scene = new Scene(root);
            stage.setTitle(acTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to update customer window.
     */
    public void enterUpdateCustomer(Customer customer) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ucvPath));
            scene = new Scene(root);
            stage.setTitle(ucTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to appointment directory window.
     */
    public void enterAppointmentDirectory() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(advPath));
            scene = new Scene(root);
            stage.setTitle(adTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to add appointment directory window.
     */
    public void enterAddAppointment() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(aavPath));
            scene = new Scene(root);
            stage.setTitle(aaTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to update appointment window.
     */
    public void enterUpdateAppointment() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(uavPath));
            scene = new Scene(root);
            stage.setTitle(uaTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to reports window.
     */
    public void enterReports() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rvPath));
            scene = new Scene(root);
            stage.setTitle(rTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to appointment sum report.
     */
    public void enterAppointmentSumReport() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(srvPath));
            scene = new Scene(root);
            stage.setTitle(srTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to contact report.
     */
    public void enterContactScheduleReport() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(csrvPath));
            scene = new Scene(root);
            stage.setTitle(csrTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Switch to location sum report scene.
     */
    public void enterLocationSumReport() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(lsrvPath));
            scene = new Scene(root);
            stage.setTitle(lsrTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Scene Controller IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

