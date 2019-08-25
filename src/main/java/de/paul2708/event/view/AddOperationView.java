package de.paul2708.event.view;

import de.paul2708.event.controller.AddOperationController;

/**
 * This class represents the add operation layout.
 * It contains path, name, date and time.
 *
 * @author Paul2708
 */
public final class AddOperationView extends AbstractView<AddOperationController> {

    /**
     * Create a new add operation view.
     */
    public AddOperationView() {
        super("add_operation.fxml", "Einsatz hinzufügen");
    }
}