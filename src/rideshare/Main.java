package rideshare;

/**
 * <h3>Main</h3>
 *
 * Entry point for the Rideshare Program. Creates the model, views, and
 * controller, then starts the program.
 *
 * @author fisherjc
 * @version 11 Nov 2026
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
}
