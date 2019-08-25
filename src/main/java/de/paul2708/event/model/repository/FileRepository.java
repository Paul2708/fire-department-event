package de.paul2708.event.model.repository;

import de.paul2708.event.model.Operation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This {@link Repository} is based on local file.
 * The operations will be written to file and read by {@link Serializable}.
 * <p>
 * Note: This isn't the best solution at all, but works fine for this use-case.
 *
 * @author Paul2708
 */
public class FileRepository implements Repository {

    private static final String PATH = ".event_data";

    /**
     * Insert a new operation.
     *
     * @param operation operation to insert
     */
    @Override
    public void insert(Operation operation) {
        List<Operation> list = readFromFile();

        list.add(operation);

        writeToFile(list);
    }

    /**
     * Update an operation.
     * The changed property must be the execution time since name and path are unique.
     *
     * @param operation operation to update
     */
    @Override
    public void update(Operation operation) {
        List<Operation> list = readFromFile();

        list.remove(operation);
        list.add(operation);

        writeToFile(list);
    }

    /**
     * Delete an operation.
     *
     * @param operation operation to delete
     */
    @Override
    public void delete(Operation operation) {
        List<Operation> list = readFromFile();

        list.remove(operation);

        writeToFile(list);
    }

    /**
     * Select all operations.
     *
     * @return unmodifiable list of all operations
     */
    @Override
    public List<Operation> selectAll() {
        return Collections.unmodifiableList(readFromFile());
    }

    /**
     * Write a list of operations to the file.
     *
     * @param operations list of operations
     */
    private static void writeToFile(List<Operation> operations) {
        try (OutputStream outputStream = new FileOutputStream(FileRepository.PATH);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(operations);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a list of operations from the file.
     * If the file doesn't exist, an empty list will be returned.
     *
     * @return list of operations
     */
    private List<Operation> readFromFile() {
        if (!Files.exists(Paths.get(FileRepository.PATH))) {
            return new ArrayList<>();
        }

        try (InputStream inputStream = new FileInputStream(FileRepository.PATH);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (List<Operation>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

            throw new RuntimeException("Failed to read from file");
        }
    }
}