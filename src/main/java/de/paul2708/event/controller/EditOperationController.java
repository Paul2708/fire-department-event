package de.paul2708.event.controller;

import de.paul2708.event.model.ApplicationModel;
import de.paul2708.event.model.Operation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.Date;

/**
 * This class controls {@link EditOperationController} and edits an existing operation.
 *
 * @author Paul2708
 */
public final class EditOperationController {

    private Operation operation;

    @FXML
    private TextField nameField;

    @FXML
    private TextField oldTimeField;

    @FXML
    private TextField newTimeField;

    @FXML
    private Button button;

    /**
     * Set the current date as sample date.
     */
    @FXML
    private void initialize() {
        newTimeField.setText(Operation.DATE_FORMAT.format(new Date()));
    }

    /**
     * Edit the current operation and delay all other operations.
     */
    @FXML
    public void edit() {
        try {
            requireValue(newTimeField.getText());
        } catch (IllegalArgumentException e) {
            // TODO: Better error handling
            new Alert(Alert.AlertType.ERROR, "Bitte alle Felder ausfüllen.", ButtonType.OK)
                    .showAndWait();
            return;
        }

        // TODO: Add additional checks

        long timestamp;
        try {
            timestamp = Operation.DATE_FORMAT.parse(newTimeField.getText()).getTime();
        } catch (ParseException e) {
            new Alert(Alert.AlertType.ERROR, "Das Format stimmt nicht.", ButtonType.OK)
                    .showAndWait();
            return;
        }

        // Apply diff
        ApplicationModel.by().update(operation, timestamp - operation.getExecutionTime());

        // Close window
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    /**
     * Set the current operation.
     *
     * @param operation operation to edit
     */
    public void setOperation(Operation operation) {
        this.operation = operation;

        nameField.setText(operation.getName());
        oldTimeField.setText(operation.getFormattedTime());
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