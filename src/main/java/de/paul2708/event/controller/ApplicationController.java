package de.paul2708.event.controller;

import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

import java.io.File;
import java.util.List;

/**
 * This class represents the controller in MVC and handles input events (like drag-and-drop) from the user.
 * It refers to {@link de.paul2708.event.view.ApplicationFX}.
 *
 * @author Paul2708
 */
public final class ApplicationController {

    private static final String MP3_EXTENSION = "mp3";

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

        // TODO: Open operation dialog
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