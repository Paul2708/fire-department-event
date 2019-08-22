package de.paul2708.event.model.observer;

/**
 * This enum holds the reasons why the ui should be updated.
 * It prevents the ui to reload all ui elements.
 *
 * @author Paul2708
 */
public enum UpdateReason {

    /**
     * An operation was added or removed.
     */
    OPERATION_UPDATE
}
