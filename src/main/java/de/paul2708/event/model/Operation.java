package de.paul2708.event.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This data class holds information about an operation like name, time and path to the mp3 file.
 * It implements {@link Serializable} to write it to a file.
 *
 * @author Paul2708
 */
public final class Operation implements Serializable {

    private final String path;
    private final String name;
    private long executeTime;

    /**
     * Create a new operation.
     *
     * @param path unique path to mp3 file that will play at execution time
     * @param name unique operation name to identify
     * @param executeTime execution timestamp
     */
    public Operation(String path, String name, long executeTime) {
        this.path = path;
        this.name = name;
        this.executeTime = executeTime;
    }

    /**
     * Set the execution time.
     *
     * @param executeTime new timestamp
     */
    public void setExecutionTime(long executeTime) {
        this.executeTime = executeTime;
    }

    /**
     * Get the path to the mp3 file.
     *
     * @return absolute file path
     */
    public String getPath() {
        return path;
    }

    /**
     * Get the unique operation name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the execution timestamp.
     *
     * @return timestamp
     */
    public long getExecutionTime() {
        return executeTime;
    }

    /**
     * Return the operation name to display it in a readable message to the user.
     * Since name is unique, the user can identify the correct operation.
     *
     * @return operation name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Check if two operations are equal.
     * They are equal if the path and the name are the same.
     *
     * @param o object to check
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Operation operation = (Operation) o;

        if (!Objects.equals(path, operation.path)) {
            return false;
        }

        return Objects.equals(name, operation.name);
    }

    /**
     * Get the hash code based on path and name.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}