package de.paul2708.event.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

/**
 * This class represents the controller for {@link de.paul2708.event.view.AddOperationView} and adds the operation
 * to the list.
 *
 * @author Paul2708
 */
public final class OperationController {

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
        // TODO: Implement me
    }

    /**
     * Set the path.
     *
     * @param path path
     */
    public void setPath(String path) {
        pathField.setText(path);
    }
}