package de.paul2708.event.view;

import de.paul2708.event.controller.EditOperationController;

/**
 * This class contains methods to init and show the operation view.
 *
 * @author Paul2708
 */
public final class EditOperationView extends AbstractView<EditOperationController> {

    /**
     * Create a new edit operation view.
     */
    public EditOperationView() {
        super("edit_operation.fxml", "Einsatz bearbeiten");
    }
}