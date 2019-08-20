package de.paul2708.event.view;

import de.paul2708.event.view.impl.ApplicationFX;

/**
 * This interface represents the application view in MVC.
 *
 * @author Paul2708
 */
public interface ApplicationView {

    /**
     * Launch the application and show it to the user.
     */
    void launch();

    /**
     * Get the view implementation.
     *
     * @return view implementation
     */
    static ApplicationView by() {
        return new ApplicationFX();
    }
}
