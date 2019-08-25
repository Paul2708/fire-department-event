package de.paul2708.event.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * This class is a JavaFX-{@link Application} and the main application.
 *
 * @author Paul2708
 */
public final class ApplicationFX extends Application {

    private static final String MAIN_FXML_PATH = "application.fxml";

    /**
     * Launch the application.
     */
    public void launch() {
        Application.launch();
    }

    /**
     * Start the application.
     *
     * @param primaryStage primary stage
     * @throws IOException if loading the layout file throws any errors
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL mainURL = getClass().getClassLoader().getResource(ApplicationFX.MAIN_FXML_PATH);
        if (mainURL == null) {
            throw new RuntimeException("Failed to load " + ApplicationFX.MAIN_FXML_PATH);
        }

        Parent root = FXMLLoader.load(mainURL);
        primaryStage.setTitle("Einsatz-Verwaltung");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}