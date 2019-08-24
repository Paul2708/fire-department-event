package de.paul2708.event.model.observer;

/**
 * This enum holds the reasons why the ui should be updated.
 * It prevents the ui to reload all ui elements.
 *
 * @author Paul2708
 */
public enum UpdateReason {

    /**
     * An operation was added.
     * Arguments: added operation
     */
    OPERATION_ADDED,

    /**
     * An operation was removed.
     * Arguments: removed operation
     */
    OPERATION_REMOVED,

    /**
     * All operations got updated.
     * Arguments: sorted list of operations
     */
    OPERATION_UPDATE,

    /**
     * The model was started.
     * Arguments: sorted list of operations
     */
    STARTUP
}
