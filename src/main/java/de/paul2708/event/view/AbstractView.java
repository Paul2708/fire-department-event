package de.paul2708.event.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * This abstract class represents a view.
 * It can be initialized and shown to the user.
 *
 * @param <T> controller type
 * @author Paull2708
 */
public abstract class AbstractView<T> {

    private final String path;
    private final String title;

    private T controller;

    private Stage stage;

    /**
     * Create a new abstract view with fxml path and title.
     *
     * @param path  fxml path to layout resource
     * @param title scene title
     */
    AbstractView(String path, String title) {
        this.path = path;
        this.title = title;
    }

    /**
     * Initialize layout and stage.
     *
     * @param ownerWindow owner window
     */
    public void initialize(Window ownerWindow) {
        // Parse layout
        URL url = getClass().getClassLoader().getResource(path);
        if (url == null) {
            throw new RuntimeException("Failed to load " + path);
        }

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(url);
            root = loader.load();

            this.controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + path);
        }

        // Apply stage
        this.stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(ownerWindow);

        stage.setTitle(title);
        stage.setResizable(false);

        stage.setScene(new Scene(root));
    }

    /**
     * Show the stage to the user.
     *
     * @param consumer consumer that consumes the controller before the stage is shown to the user
     */
    public void show(Consumer<T> consumer) {
        consumer.accept(controller);

        stage.show();
    }
}