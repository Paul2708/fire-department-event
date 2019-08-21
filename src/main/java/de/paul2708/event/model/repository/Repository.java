package de.paul2708.event.model.repository;

import de.paul2708.event.model.Operation;

import java.util.List;

/**
 * This interface represents a repository that takes care of operation queries.
 * It features basic operations to add, remove and update operations.
 *
 * @author Paul2708
 */
public interface Repository {

    /**
     * Insert a new operation.
     *
     * @param operation operation to insert
     */
    void insert(Operation operation);

    /**
     * Update an operation.
     * The changed property must be the execution time since name and path are unique.
     *
     * @param operation operation to update
     */
    void update(Operation operation);

    /**
     * Delete an operation.
     *
     * @param operation operation to delete
     */
    void delete(Operation operation);

    /**
     * Select all operations.
     *
     * @return unmodifiable list of all operations
     */
    List<Operation> selectAll();
}