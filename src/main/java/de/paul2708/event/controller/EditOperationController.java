package de.paul2708.event.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * This class controls {@link EditOperationController} and edits an existing operation.
 *
 * @author Paul2708
 */
public final class EditOperationController {

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
        // TODO: Implement me
    }
}