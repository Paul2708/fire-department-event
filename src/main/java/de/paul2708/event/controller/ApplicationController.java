package de.paul2708.event.controller;

import de.paul2708.event.model.ApplicationModel;
import de.paul2708.event.model.Operation;
import de.paul2708.event.model.observer.Update;
import de.paul2708.event.model.observer.UpdateReason;
import de.paul2708.event.view.AbstractView;
import de.paul2708.event.view.AddOperationView;
import de.paul2708.event.view.EditOperationView;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class represents the controller in MVC and handles input events (like drag-and-drop) from the user.
 * It refers to {@link de.paul2708.event.view.ApplicationFX}.
 *
 * @author Paul2708
 */
public final class ApplicationController implements Observer {

    // TODO: Fix drag and drop detection

    private static final String MP3_EXTENSION = "mp3";

    private AbstractView<AddOperationController> addOperationView;
    private AbstractView<EditOperationController> editOperationView;

    private ApplicationModel applicationModel;

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<Operation> operationListView;

    @FXML
    private TextField countdownField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField durationField;

    @FXML
    private void initialize() {
        // Initialize view and observer
        this.addOperationView = new AddOperationView();
        this.editOperationView = new EditOperationView();

        this.applicationModel = ApplicationModel.by();
        applicationModel.addObserver(this);

        applicationModel.start();

        // Create list context
        // TODO: Out source it?
        MenuItem editItem = new MenuItem("Bearbeiten...");
        editItem.setOnAction(event -> {
            editOperationView.initialize(root.getScene().getWindow());
            editOperationView.show(controller -> getSelectedOperation().ifPresent(controller::setOperation));
        });
        MenuItem deleteItem = new MenuItem("Entfernen");
        deleteItem.setOnAction(event ->  {
            Optional<Operation> optional = getSelectedOperation();

            if (optional.isPresent()) {
                applicationModel.getRepository().delete(optional.get());
                applicationModel.notifyObservers(new Update(UpdateReason.OPERATION_REMOVED, optional.get()));
            }
        });

        ContextMenu contextMenu = new ContextMenu(editItem, deleteItem);

        operationListView.setOnContextMenuRequested(event -> {
            contextMenu.show(operationListView, event.getScreenX(), event.getScreenY());
        });
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

        addOperationView.initialize(root.getScene().getWindow());
        addOperationView.show(addOperationController -> addOperationController.setPath(files.get(0).getPath()));
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
        Update update = (Update) arg;

        switch (update.getReason()) {
            case OPERATION_ADDED:
                Operation added = (Operation) update.getArguments()[0];
                operationListView.getItems().add(added);

                Collections.sort(operationListView.getItems());

                applicationModel.onOperationUpdate();
                break;
            case OPERATION_REMOVED:
                Operation removed = (Operation) update.getArguments()[0];
                operationListView.getItems().remove(removed);

                Collections.sort(operationListView.getItems());

                applicationModel.onOperationUpdate();
                break;
            case OPERATION_UPDATE:
                operationListView.getItems().clear();
                operationListView.getItems().addAll((List<Operation>) update.getArguments()[0]);
                break;
            case STARTUP:
                operationListView.getItems().clear();
                operationListView.getItems().addAll((List<Operation>) update.getArguments()[0]);
                break;
            case UPDATE_COUNTDOWN:
                long countdown = (long) update.getArguments()[0];

                if (countdown == -1) {
                    countdownField.setText("keine nächster Einsatz bekannt");
                } else {
                    long[] timeUnits = getHourMinuteSecond(countdown);
                    String formattedCountdown = String.format("%02d Stunden %02d Minuten %02d Sekunden",
                            timeUnits[0], timeUnits[1], timeUnits[2]);

                    countdownField.setText(formattedCountdown);
                }

                break;
            case OPERATION_SWITCH:
                Operation operation = (Operation) update.getArguments()[0];

                playSound(operation.getPath());
                System.out.println("Play sound: " + operation.getPath());
                break;
            case CURRENT_OPERATION_UPDATE:
                Operation current = (Operation) update.getArguments()[0];
                Operation next = (Operation) update.getArguments()[1];

                if (current == null) {
                    nameField.setText("aktuell läuft kein Einsatz");
                    durationField.setText("-/-");
                } else {
                    nameField.setText(current.getName());

                    long duration = next != null ? next.getExecutionTime() - current.getExecutionTime() : -1;

                    durationField.setText(duration == -1 ? "open end"
                            : formatDuration(getHourMinuteSecond(duration)));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Get the selected operation from list view.
     *
     * @return optional operation
     */
    private Optional<Operation> getSelectedOperation() {
        return Optional.ofNullable(operationListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Play a sound by path.
     *
     * @param path absolute file path to mp3
     */
    private static void playSound(String path) {
        Media pick = new Media(new File(path).toPath().toUri().toString());
        MediaPlayer player = new MediaPlayer(pick);

        player.play();
    }

    /**
     * Get the time units in a formatted string.
     *
     * @param timeUnits time units by {@link #getHourMinuteSecond(long)}
     * @return formatted string
     */
    private static String formatDuration(long[] timeUnits) {
        String result = "";
        if (timeUnits[0] != 0) {
            result += timeUnits[0] + " Stunden";
        }
        if (timeUnits[1] != 0) {
            result += " " + timeUnits[1] + " Minuten";
        }
        if (timeUnits[2] != 0) {
            result += " " + timeUnits[2] + " Sekunden";
        }

        return result;
    }

    /**
     * Get the hours, minutes and seconds by duration in milliseconds.
     *
     * @param duration duration in milliseconds
     * @return long array with hours, minutes and seconds
     */
    private static long[] getHourMinuteSecond(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));

        return new long[] { hours, minutes, seconds };
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