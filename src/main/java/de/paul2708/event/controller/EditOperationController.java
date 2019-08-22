package de.paul2708.event.controller;

import de.paul2708.event.model.ApplicationModel;
import de.paul2708.event.model.Operation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * This class controls {@link EditOperationController} and edits an existing operation.
 *
 * @author Paul2708
 */
public final class EditOperationController {

    private Operation operation;

    @FXML
    private TextField timeField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Integer> hoursBox;

    @FXML
    private ComboBox<Integer> minutesBox;

    @FXML
    private Button button;

    /**
     * Initialize the time boxes with hours and minutes.
     */
    @FXML
    private void initialize() {
        for (int i = 1; i <= 23; i++) {
            hoursBox.getItems().add(i);
        }

        for (int i = 0; i <= 59; i++) {
            minutesBox.getItems().add(i);
        }
    }

    /**
     * Edit the current operation and delay all other operations.
     */
    @FXML
    public void edit() {
        try {
            requireValue(datePicker.getValue());
            requireValue(hoursBox.getValue());
            requireValue(minutesBox.getValue());
        } catch (IllegalArgumentException e) {
            // TODO: Better error handling
            new Alert(Alert.AlertType.ERROR, "Bitte alle Felder ausfüllen.", ButtonType.OK)
                    .showAndWait();
            return;
        }

        // TODO: Add additional checks

        LocalDateTime localDateTime = LocalDateTime.of(
                datePicker.getValue(),
                LocalTime.of(hoursBox.getValue(), minutesBox.getValue())
        );
        long timestamp = localDateTime.toInstant(ZoneOffset.ofHours(2)).toEpochMilli();

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

        timeField.setText(operation.getFormattedTime());
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