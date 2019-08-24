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

    private Operation currentOperation;
    private Operation nextOperation;

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

        updateLocalOperations(operations);

        notifyObservers(new Update(UpdateReason.STARTUP, operations));

        new Thread(() -> {
            while (true) {
                if (nextOperation == null) {
                    notifyObservers(new Update(UpdateReason.UPDATE_COUNTDOWN, -1L));
                } else {
                    long diff = nextOperation.getExecutionTime() - System.currentTimeMillis();
                    notifyObservers(new Update(UpdateReason.UPDATE_COUNTDOWN, diff));
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

        updateLocalOperations(operations);

        notifyObservers(new Update(UpdateReason.OPERATION_UPDATE, operations));
    }

    /**
     * Update local operations (current operation, next operation) by list of operations.
     *
     * @param operations sorted list of all operations
     */
    private void updateLocalOperations(List<Operation> operations) {
        for (int i = 0; i < operations.size(); i++) {
            if (operations.get(i).getExecutionTime() >= System.currentTimeMillis()) {
                this.nextOperation = operations.get(i);

                if (i > 0) {
                    this.currentOperation = operations.get(i - 1);
                }

                break;
            }
        }
    }
}