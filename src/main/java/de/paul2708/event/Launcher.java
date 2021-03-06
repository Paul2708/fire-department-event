package de.paul2708.event;

import de.paul2708.event.view.ApplicationFX;

/**
 * This class holds the main method and runs the application.
 *
 * @author Paul2708
 */
public final class Launcher {

    /**
     * Nothing to call here.
     */
    private Launcher() {
        throw new IllegalAccessError();
    }

    /**
     * Program entry. Launches the view.
     *
     * @param args ignored command line arguments
     */
    public static void main(String[] args) {
        ApplicationFX application = new ApplicationFX();
        application.launch();
    }
}