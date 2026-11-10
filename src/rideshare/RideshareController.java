package rideshare;

/**
 * <h3>RideshareController</h3>
 *
 * Controller Portion of the MVC for the Rideshare Program
 *
 * @author fisherjc
 * @version 11 Nov 2026
 */
public class RideshareController {
    private RideshareModel model;
    private RideshareView view;
    private RoadmapView roadmapView;

    /**
     * Creates a new RideshareController.
     *
     * @param model
     *            the rideshare model
     * @param view
     *            the rideshare view
     * @param roadmapView
     *            the roadmap view
     */
    public RideshareController(
        RideshareModel model,
        RideshareView view,
        RoadmapView roadmapView) {
        this.model = model;
        this.view = view;
        this.roadmapView = roadmapView;
    }


    /**
     * Starts the program.
     */
    public void start() {
        // TODO
    }


    /**
     * Initializes the cars.
     *
     * @param numberOfCars
     *            the number of cars to create
     * @param capacity
     *            the passenger capacity of each car
     */
    public void handleInitializeCars(int numberOfCars, int capacity) {
        // TODO
    }


    /**
     * Adds a passenger.
     *
     * @param name
     *            the passenger's name
     * @param destination
     *            the passenger's destination
     */
    public void handleAddPassenger(String name, String destination) {
        // TODO
    }


    /**
     * Assigns waiting passengers to cars.
     */
    public void handlePassengerAssignment() {
        // TODO
    }


    /**
     * Drops off the passenger(s) in the given car.
     *
     * @param carId
     *            the id of the car
     */
    public void handleDropOffPassenger(int carId) {
        // TODO
    }


    /**
     * Displays the passengers.
     */
    public void handleViewPassengers() {
        // TODO
    }


    /**
     * Displays the cars.
     */
    public void handleViewCars() {
        // TODO
    }


    /**
     * Displays the roadmap.
     */
    public void handleRoadmapDisplay() {
        // TODO
    }


    /**
     * Simulates the passage of time.
     *
     * @param minutesElapsed
     *            the number of minutes that have passed
     */
    public void handleTimeSimulation(int minutesElapsed) {
        // TODO
    }


    /**
     * Handles a menu choice from the user.
     *
     * @param choice
     *            the menu option selected
     */
    public void handleMenuChoice(int choice) {
        // TODO
    }
}
