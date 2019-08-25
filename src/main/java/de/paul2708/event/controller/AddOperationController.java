package de.paul2708.event.controller;

import de.paul2708.event.model.ApplicationModel;
import de.paul2708.event.model.Operation;
import de.paul2708.event.model.observer.Update;
import de.paul2708.event.model.observer.UpdateReason;
import de.paul2708.event.model.repository.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.Date;

/**
 * This class represents the controller for {@link de.paul2708.event.view.AddOperationView} and adds the operation
 * to the list.
 *
 * @author Paul2708
 */
public final class AddOperationController {

    @FXML
    private TextField pathField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField timeField;

    @FXML
    private Button button;

    /**
     * Set the current date as sample date.
     */
    @FXML
    private void initialize() {
        timeField.setText(Operation.DATE_FORMAT.format(new Date()));
    }

    /**
     * Check the user input like name and time and add a new operation.
     */
    @FXML
    private void add() {
        try {
            requireValue(nameField.getText());
            requireValue(timeField.getText());
        } catch (IllegalArgumentException e) {
            // TODO: Better error handling
            new Alert(Alert.AlertType.ERROR, "Bitte alle Felder ausfüllen.", ButtonType.OK)
                    .showAndWait();
            return;
        }

        // TODO: Add additional checks

        long timestamp = 0;
        try {
            timestamp = Operation.DATE_FORMAT.parse(timeField.getText()).getTime();
        } catch (ParseException e) {
            new Alert(Alert.AlertType.ERROR, "Das Format stimmt nicht.", ButtonType.OK)
                    .showAndWait();
            return;
        }

        // Insert operation
        Operation operation = new Operation(pathField.getText(), nameField.getText(), timestamp);
        Repository repository = ApplicationModel.by().getRepository();
        repository.insert(operation);

        ApplicationModel.by().notifyObservers(new Update(UpdateReason.OPERATION_ADDED, operation));

        // Close window
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    /**
     * Set the path.
     *
     * @param path path
     */
    public void setPath(String path) {
        pathField.setText(path);
    }

    /**
     * Check if the value is present.
     *
     * @param value value to check
     * @throws IllegalArgumentException if the value is not present
     */
    private static void requireValue(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
    }
}