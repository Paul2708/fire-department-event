package de.paul2708.event.view;

import de.paul2708.event.controller.AddOperationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

/**
 * This class contains methods to init and show the operation view.
 *
 * @author Paul2708
 */
public final class AddOperationView {

    private static final String OPERATION_FXML_PATH = "add_operation.fxml";

    private AddOperationController controller;

    private Stage stage;

    /**
     * Initialize layout and stage.
     *
     * @param ownerWindow owner window
     */
    public void initialize(Window ownerWindow) {
        // Parse layout
        URL url = getClass().getClassLoader().getResource(AddOperationView.OPERATION_FXML_PATH);
        if (url == null) {
            throw new RuntimeException("Failed to load " + AddOperationView.OPERATION_FXML_PATH);
        }

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            this.controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + AddOperationView.OPERATION_FXML_PATH);
        }

        // Apply stage
        this.stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(ownerWindow);

        stage.setTitle("Einsatz hinzufügen");
        stage.setResizable(false);

        stage.setScene(new Scene(root));
    }

    /**
     * Show the stage to the user.
     *
     * @param path dropped mp3 file path
     */
    public void show(String path) {
        controller.setPath(path);

        stage.show();
    }
}