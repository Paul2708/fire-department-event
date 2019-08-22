package de.paul2708.event.model;

import de.paul2708.event.model.repository.Repository;

import java.util.Observable;

/**
 * This class describes the model and refers to the operation {@link Repository}.
 *
 * @author Paul2708
 */
public abstract class ApplicationModel extends Observable {

    private static ApplicationModel instance;

    /**
     * Get the repository instance.
     *
     * @return repository
     */
    public abstract Repository getRepository();

    /**
     * Set and clear the changed state before and after calling {@link super#notifyObservers(Object)}.
     * The observers will only be notified, if something changed.
     *
     * @param arg {@link de.paul2708.event.model.observer.UpdateReason} argument
     */
    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
        clearChanged();
    }

    /**
     * Get the application model implementation.
     *
     * @return implementation
     */
    public static ApplicationModel by() {
        if (ApplicationModel.instance == null) {
            ApplicationModel.instance = new DefaultApplicationModel();
        }

        return ApplicationModel.instance;
    }
}