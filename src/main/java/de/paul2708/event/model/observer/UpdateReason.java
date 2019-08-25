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
    STARTUP,

    /**
     * Called every second.
     * Update the countdown.
     * Arguments: -1 if none next operation is known otherwise seconds till next operation
     */
    UPDATE_COUNTDOWN,

    /**
     * Next operation started.
     * Arguments: next operation
     */
    OPERATION_SWITCH
}
