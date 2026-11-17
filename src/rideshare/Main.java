package rideshare;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * <h3>Main</h3>
 *
 * Entry point for the Rideshare Program. Creates the model, views, and
 * controller, then starts the program.
 *
 * @author fisherjc
 * @version 17 Nov 2026
 */
public class Main {

    /**
     * Runs the Rideshare Program.
     *
     * @param args
     *            command line arguments (unused)
     */
    public static void main(String[] args) {
        RideshareModel model = new RideshareModel();
        RideshareView view = new RideshareView();
        RoadmapView roadmapView = new RoadmapView();

        RideshareController controller =
            new RideshareController(model, view, roadmapView);
        controller.start();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pauses until the user presses Enter.
     *
     * @param scanner the user input
     */
    public static void pause(Scanner scanner) {
        System.out.println();
        System.out.print("Press Enter to continue...");
        try {
            scanner.nextLine();
        } catch (NoSuchElementException e) {
            // No more input, so continue on
        }
    }
}
