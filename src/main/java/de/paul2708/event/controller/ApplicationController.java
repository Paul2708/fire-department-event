package de.paul2708.event.controller;

import de.paul2708.event.model.ApplicationModel;
import de.paul2708.event.model.Operation;
import de.paul2708.event.model.observer.UpdateReason;
import de.paul2708.event.view.AddOperationView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.*;

/**
 * This class represents the controller in MVC and handles input events (like drag-and-drop) from the user.
 * It refers to {@link de.paul2708.event.view.ApplicationFX}.
 *
 * @author Paul2708
 */
public final class ApplicationController implements Observer {

    // TODO: Fix drag and drop detection

    private static final String MP3_EXTENSION = "mp3";

    private AddOperationView operationView;

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<Operation> operationListView;

    @FXML
    private void initialize() {
        this.operationView = new AddOperationView();

        ApplicationModel.by().addObserver(this);

        // Load operations
        ApplicationModel.by().notifyObservers(UpdateReason.OPERATION_UPDATE);
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
     * This method is called whenever the observed object is changed.
     * An application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param observable the observable object.
     * @param arg        an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable observable, Object arg) {
        UpdateReason reason = (UpdateReason) arg;

        switch (reason) {
            case OPERATION_UPDATE:
                List<Operation> operationList = ApplicationModel.by().getRepository().selectAll();
                List<Operation> sortedList = new ArrayList<>(operationList);
                Collections.sort(sortedList);

                this.operationListView.getItems().setAll(sortedList);
                break;
            default:
                break;
        }
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