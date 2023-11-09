package model;

import controller.MainController;
import controller.SceneController;
import helper.JDBC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point for Scheduling JavaFX application.
 */
public class Main extends Application {
    private SceneController sc = new SceneController();
    private String mvPath = "../view/MainView.fxml";
    private String mvTitle = "Scheduling Application";
    /**
     * @param stage Initialize main window.
     */
    @Override
    public void start(Stage stage) {
        sc.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(mvPath));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle(mvTitle);
            stage.setScene(scene);
            sc.setScene(scene);
            MainController controller = loader.getController(); //Get the controller instance.
            controller.initialize();
            stage.show();
        } catch (IOException e) {
            System.out.println("Main IOException:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Open 1 JDBC, run application, then close JDBC on exit.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}

