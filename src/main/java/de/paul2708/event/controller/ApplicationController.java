package de.paul2708.event.controller;

import de.paul2708.event.view.AddOperationView;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.List;

/**
 * This class represents the controller in MVC and handles input events (like drag-and-drop) from the user.
 * It refers to {@link de.paul2708.event.view.ApplicationFX}.
 *
 * @author Paul2708
 */
public final class ApplicationController {

    // TODO: Fix drag and drop detection

    private static final String MP3_EXTENSION = "mp3";

    private AddOperationView operationView;

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        this.operationView = new AddOperationView();
    }

    /**
     * Check if the dropped file is a <code>mp3</code> file and add it to the list.
     *
     * @param event drag event
     */
    @FXML
    private void handleDroppedDrag(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        List<File> files = dragboard.getFiles();

        if (files == null || files.isEmpty() || !isMediaFile(files.get(0).getPath())) {
            return;
        }

        operationView.initialize(root.getScene().getWindow());
        operationView.show(files.get(0).getPath());
    }

    /**
     * Check if the file has the correct extension.
     *
     * @param file file to check
     * @return true if the file is a media file, otherwise false
     */
    private static boolean isMediaFile(String file) {
        return file.endsWith(ApplicationController.MP3_EXTENSION.toLowerCase())
                || file.endsWith(ApplicationController.MP3_EXTENSION.toUpperCase());
    }
}