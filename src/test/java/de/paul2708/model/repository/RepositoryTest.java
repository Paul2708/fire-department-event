package de.paul2708.model.repository;

import de.paul2708.event.model.Operation;
import de.paul2708.event.model.repository.FileRepository;
import de.paul2708.event.model.repository.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test class tests the {@link Repository}.
 *
 * @author Paul2708
 */
public final class RepositoryTest {

    private Repository repository;

    private Operation operation;

    /**
     * Create a new repository and operation.
     */
    @Before
    public void setUp() {
        this.repository = new FileRepository();

        assertTrue(repository.selectAll().isEmpty());

        this.operation = new Operation("/sample/path/value.mp3", "First operation", System.currentTimeMillis());
    }

    /**
     * Test if insertion works fine.
     */
    @Test
    public void testInsert() {
        // Insert
        repository.insert(operation);

        // Verify
        List<Operation> operations = repository.selectAll();

        assertEquals(1, operations.size());

        assertEquals(operation.getPath(), operations.get(0).getPath());
        assertEquals(operation.getName(), operations.get(0).getName());
        assertEquals(operation.getExecutionTime(), operations.get(0).getExecutionTime());
    }

    /**
     * Test if an operation got deleted correctly.
     */
    @Test
    public void testDelete() {
        // Insert
        repository.insert(operation);
        repository.delete(operation);

        // Verify
        List<Operation> operations = repository.selectAll();

        assertEquals(0, operations.size());
    }

    /**
     * Test if the update value got fetched correctly.
     */
    @Test
    public void testUpdate() {
        // Insert
        repository.insert(operation);

        assertEquals(operation.getExecutionTime(), repository.selectAll().get(0).getExecutionTime());

        // Update
        operation.setExecutionTime(42);

        repository.update(operation);

        assertEquals(42, repository.selectAll().get(0).getExecutionTime());
    }

    /**
     * Check if the collection is unmodifiable.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableList() {
        repository.selectAll().add(operation);
    }

    /**
     * Clear the repository after each test case.
     */
    @After
    public void tearDown() {
        for (Operation operation : repository.selectAll()) {
            repository.delete(operation);
        }
    }
}