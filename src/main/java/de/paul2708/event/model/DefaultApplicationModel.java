package de.paul2708.event.model;

import de.paul2708.event.model.repository.FileRepository;
import de.paul2708.event.model.repository.Repository;

/**
 * This class implements {@link ApplicationModel} and features a {@link FileRepository}.
 *
 * @author Paul2708
 */
public final class DefaultApplicationModel extends ApplicationModel {

    private final Repository repository;

    /**
     * Create a new default application model implementation.
     */
    DefaultApplicationModel() {
        this.repository = new FileRepository();
    }

    /**
     * Get the repository instance.
     *
     * @return repository
     */
    @Override
    public Repository getRepository() {
        return repository;
    }
}