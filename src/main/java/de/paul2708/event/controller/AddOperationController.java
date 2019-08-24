package de.paul2708.event.controller;

import de.paul2708.event.model.ApplicationModel;
import de.paul2708.event.model.Operation;
import de.paul2708.event.model.observer.Update;
import de.paul2708.event.model.observer.UpdateReason;
import de.paul2708.event.model.repository.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

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
     * Check the user input like name and time and add a new operation.
     */
    @FXML
    private void add() {
        try {
            requireValue(nameField.getText());
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