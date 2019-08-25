package de.paul2708.event.model.observer;

/**
 * This data class represents an update that holds arguments that the observers receive.
 *
 * @author Paul2708
 */
public final class Update {

    private final UpdateReason reason;
    private final Object[] arguments;

    /**
     * Create a new update.
     *
     * @param reason    update reason
     * @param arguments passed arguments
     */
    public Update(UpdateReason reason, Object... arguments) {
        this.reason = reason;
        this.arguments = arguments;
    }

    /**
     * Get the update reason.
     *
     * @return reason
     */
    public UpdateReason getReason() {
        return reason;
    }

    /**
     * Get the arguments.
     *
     * @return arguments
     */
    public Object[] getArguments() {
        return arguments;
    }
}