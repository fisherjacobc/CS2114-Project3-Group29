package rideshare;

import util.Passenger;

/**
 * <h3>RideshareController</h3>
 *
 * Controller Portion of the MVC for the Rideshare Program
 *
 * @author fisherjc
 * @version 10 Nov 2026
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
        if (model == null || view == null || roadmapView == null) {
            throw new IllegalArgumentException("Model, view, and roadmap view must not be null. Please restart the program and try again.");
        }

        this.model = model;
        this.view = view;
        this.roadmapView = roadmapView;
    }


    /**
     * Starts the program and runs the menu until the user quits.
     */
    public void start() {
        while (true) {
            view.displayMenu();
            int choice = view.readIntRaw("Input Choice");

            if (choice == 0) {
                view.displayMessage("Program ended.");
                return;
            }

            handleMenuChoice(choice);
        }
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
        if (model.initializeCars(numberOfCars, capacity)) {
            view.displayMessage("Created " + numberOfCars
                + " car(s) with capacity " + capacity + ".");
        }
        else {
            view.displayError("Cars must have a count and capacity of at "
                + "least 1, and existing cars must be empty.");
        }
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
        try {
            model.addPassenger(name, destination);
            view.displayMessage("Added passenger " + name.trim() + ".");
        }
        catch (IllegalArgumentException e) {
            view.displayError(e.getMessage());
        }
    }


    /**
     * Assigns the next waiting passenger to an available car.
     */
    public void handlePassengerAssignment() {
        if (!checkCarsExist()) {
            return;
        }

        if (model.loadPassengerInCar()) {
            view.displayMessage("Passenger assigned to a car.");
        }
        else {
            view.displayError("No passengers waiting or all cars are full.");
        }
    }


    /**
     * Drops off the next passenger in the given car.
     *
     * @param carId
     *            the id of the car
     */
    public void handleDropOffPassenger(int carId) {
        if (!checkCarsExist()) {
            return;
        }

        Passenger droppedOff = model.dropOffPassenger(carId);

        if (droppedOff != null) {
            view.displayMessage("Dropped off " + droppedOff.getName()
                + " at " + droppedOff.getDestination() + ".");
        }
        else {
            view.displayError("Car " + carId + " does not exist or is empty.");
        }
    }


    /**
     * Displays the waiting passengers and the passengers in each car.
     */
    public void handleViewPassengers() {
        view.displayPassengers(model.getWaitingPassengers(), model.getCars());
    }


    /**
     * Displays the cars and their capacities.
     */
    public void handleViewCars() {
        view.displayCars(model.getCars());
    }


    /**
     * Displays the roadmap.
     */
    public void handleRoadmapDisplay() {
        // TODO: (roadmap view): display model.getCars() on the roadmap for Deliverable 5
        view.displayMessage("Roadmap not implemented yet.");
    }


    /**
     * Simulates the passage of time.
     *
     * @param minutesElapsed
     *            the number of minutes that have passed
     */
    public void handleTimeSimulation(int minutesElapsed) {
        // TODO: Finish time simulation for Deliverable 5
        view.displayMessage("Time Simulation not completed yet.");
        // try {
        //     model.updateTravelTime(minutesElapsed);
        //     view.displayMessage("Total elapsed time: "
        //         + model.getElapsedMinutes() + " minute(s).");
        // }
        // catch (IllegalArgumentException e) {
        //     view.displayError(e.getMessage());
        // }
    }


    /**
     * Handles a menu choice from the user.
     *
     * @param choice
     *            the menu option selected
     */
    public void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                handleInitializeCars(
                    view.readInt("Input Number of cars"),
                    view.readInt("Input Capacity for every car"));
                break;
            case 2:
                handleAddPassenger(
                    view.readString("Input Passenger Name"),
                    view.readString("Input Destination"));
                break;
            case 3:
                handlePassengerAssignment();
                break;
            case 4:
                if (checkCarsExist()) {
                    handleDropOffPassenger(view.readInt("Input Car ID"));
                }
                break;
            case 5:
                handleViewPassengers();
                break;
            case 6:
                handleViewCars();
                break;
            case 7:
                handleRoadmapDisplay();
                break;
            case 8:
                handleTimeSimulation(/*view.readInt("Input Minutes elapsed")*/ 0);
                break;
            default:
                view.displayError("Invalid choice.");
                break;
        }
    }


    /**
     * Checks that cars have been set up, and shows an error if not.
     *
     * @return true if at least one car exists
     */
    private boolean checkCarsExist() {
        if (model.getCars().isEmpty()) {
            view.displayError("No cars have been set up. Use option 1 to "
                + "initialize cars first.");
            return false;
        }

        return true;
    }
}
