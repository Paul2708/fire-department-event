package de.paul2708.event.model;

import de.paul2708.event.model.observer.Update;
import de.paul2708.event.model.observer.UpdateReason;
import de.paul2708.event.model.repository.FileRepository;
import de.paul2708.event.model.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * Start the model and notify the observers.
     */
    @Override
    public void start() {
        List<Operation> operations = new ArrayList<>(repository.selectAll());
        Collections.sort(operations);

        notifyObservers(new Update(UpdateReason.STARTUP, operations));
    }

    /**
     * Update all unfinished operations by a diff of time and notify the observers.
     *
     * @param operation changed operation (every operation that follows this will be updated)
     * @param diff      difference in milliseconds
     */
    @Override
    public void update(Operation operation, long diff) {
        for (Operation singleOperation : repository.selectAll()) {
            if (singleOperation.getExecutionTime() >= operation.getExecutionTime()) {
                singleOperation.setExecutionTime(singleOperation.getExecutionTime() + diff);

                repository.update(singleOperation);
            }
        }

        List<Operation> operations = new ArrayList<>(repository.selectAll());
        Collections.sort(operations);

        notifyObservers(new Update(UpdateReason.OPERATION_UPDATE, operations));
    }
}