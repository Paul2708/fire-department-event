package de.paul2708.event.model;

import de.paul2708.event.model.repository.Repository;

/**
 * This interface describes the model and refers to the operation {@link Repository}.
 *
 * @author Paul2708
 */
public interface ApplicationModel {

    /**
     * Get the repository instance.
     *
     * @return repository
     */
    Repository getRepository();

    /**
     * Get the application model implementation.
     *
     * @return implementation
     */
    static ApplicationModel by() {
        return DefaultApplicationModel.getInstance();
    }
}