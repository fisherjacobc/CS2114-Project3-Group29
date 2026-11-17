package rideshare;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Car;
import util.Passenger;

/**
 * Tests the controller actions using an automatic test view
 * 
 * @author Aishwarya Shah
 * @version 17 Nov 2026
 */

/**
 * Tests controller actions using an automatic test view.
 *
 * @author Aishwarya Shah
 * @version 17 Nov 2026
 */
public class RideshareControllerTest {

    private RideshareModel model;
    private TestView view;
    private RoadmapView roadmapView;
    private RideshareController controller;

    /**
     * Creates fresh objects before each test.
     */
    @BeforeEach
    public void setUp() {
        model = new RideshareModel();
        view = new TestView();
        roadmapView = new RoadmapView();

        controller = new RideshareController(
            model, view, roadmapView);
    }

    /**
     * Checks that the constructor rejects each null argument.
     */
    @Test
    public void testConstructorRejectsNull() {
        try {
            new RideshareController(null, view, roadmapView);
            fail("A null model should be rejected.");
        }
        catch (IllegalArgumentException exception) {
            // Expected exception.
        }

        try {
            new RideshareController(model, null, roadmapView);
            fail("A null view should be rejected.");
        }
        catch (IllegalArgumentException exception) {
            // Expected exception.
        }

        try {
            new RideshareController(model, view, null);
            fail("A null roadmap view should be rejected.");
        }
        catch (IllegalArgumentException exception) {
            // Expected exception.
        }
    }

    /**
     * Checks successful car initialization.
     */
    @Test
    public void testInitializeCars() {
        controller.handleInitializeCars(2, 3);

        assertEquals(2, model.getCars().size());
        assertEquals(3, model.getCarById(1).getCapacity());
        assertEquals(3, model.getCarById(2).getCapacity());

        assertEquals(
            "Created 2 car(s) with capacity 3.",
            view.messages.get(0));
        assertTrue(view.errors.isEmpty());
    }

    /**
     * Checks invalid counts, capacities, and occupied cars.
     */
    @Test
    public void testInitializeCarsRejected() {
        controller.handleInitializeCars(0, 2);
        controller.handleInitializeCars(1, 0);

        assertEquals(2, view.errors.size());
        assertTrue(model.getCars().isEmpty());

        model.initializeCars(1, 1);
        model.addPassenger("Maya", "Library");
        model.loadPassengerInCar();

        controller.handleInitializeCars(2, 3);

        assertEquals(3, view.errors.size());
        assertEquals(
            "Cars must have a count and capacity of at "
                + "least 1, and existing cars must be empty.",
            view.errors.get(2));
        assertEquals(1, model.getCars().size());
        assertEquals(
            "Maya",
            model.getCarById(1).getPassengers().get(0).getName());
    }

    /**
     * Checks adding a valid passenger and the success message.
     */
    @Test
    public void testAddPassenger() {
        controller.handleAddPassenger(" Maya ", " Library ");

        assertEquals(1, model.getWaitingCount());
        Passenger passenger = model.getWaitingPassengers().get(0);

        assertEquals("Maya", passenger.getName());
        assertEquals("Library", passenger.getDestination());
        assertEquals("Added passenger Maya.", view.messages.get(0));
        assertTrue(view.errors.isEmpty());
    }

    /**
     * Checks that invalid passenger information displays errors.
     */
    @Test
    public void testAddPassengerInvalid() {
        controller.handleAddPassenger(null, "Library");
        controller.handleAddPassenger("Sam", "   ");

        assertEquals(0, model.getWaitingCount());
        assertEquals(2, view.errors.size());
        assertFalse(view.errors.get(0).isEmpty());
        assertFalse(view.errors.get(1).isEmpty());
        assertTrue(view.messages.isEmpty());
    }

    /**
     * Checks assignment before cars have been created.
     */
    @Test
    public void testAssignmentWithoutCars() {
        model.addPassenger("Maya", "Library");

        controller.handlePassengerAssignment();

        assertEquals(1, model.getWaitingCount());
        assertEquals(
            "No cars have been set up. Use option 1 to "
                + "initialize cars first.",
            view.errors.get(0));
        assertTrue(view.messages.isEmpty());
    }

    /**
     * Checks a successful passenger assignment.
     */
    @Test
    public void testPassengerAssignment() {
        model.initializeCars(1, 2);
        model.addPassenger("Maya", "Library");

        controller.handlePassengerAssignment();

        assertEquals(0, model.getWaitingCount());
        assertEquals(
            "Maya",
            model.getCarById(1).getPassengers().get(0).getName());
        assertEquals(
            "Passenger assigned to a car.",
            view.messages.get(0));
        assertTrue(view.errors.isEmpty());
    }

    /**
     * Checks assignment with no waiting passengers and a full car.
     */
    @Test
    public void testAssignmentUnavailable() {
        model.initializeCars(1, 1);

        controller.handlePassengerAssignment();

        assertEquals(
            "No passengers waiting or all cars are full.",
            view.errors.get(0));

        model.addPassenger("Maya", "Library");
        model.loadPassengerInCar();
        model.addPassenger("Sam", "Gym");

        controller.handlePassengerAssignment();

        assertEquals(2, view.errors.size());
        assertEquals(
            "No passengers waiting or all cars are full.",
            view.errors.get(1));
        assertEquals(1, model.getWaitingCount());
        assertEquals(
            "Sam", model.getWaitingPassengers().get(0).getName());
        assertEquals(
            "Maya",
            model.getCarById(1).getPassengers().get(0).getName());
    }

    /**
     * Checks drop-off before cars exist.
     */
    @Test
    public void testDropOffWithoutCars() {
        controller.handleDropOffPassenger(1);

        assertEquals(
            "No cars have been set up. Use option 1 to "
                + "initialize cars first.",
            view.errors.get(0));
        assertTrue(view.messages.isEmpty());
    }

    /**
     * Checks that the first passenger is dropped off.
     */
    @Test
    public void testDropOffPassenger() {
        model.initializeCars(1, 2);
        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");
        model.loadPassengerInCar();
        model.loadPassengerInCar();

        controller.handleDropOffPassenger(1);

        assertEquals(
            "Dropped off Maya at Library.",
            view.messages.get(0));
        assertEquals(1, model.getCarById(1).getPassengers().size());
        assertEquals(
            "Sam",
            model.getCarById(1).getPassengers().get(0).getName());
        assertTrue(view.errors.isEmpty());
    }

    /**
     * Checks errors for an empty car and a missing car.
     */
    @Test
    public void testDropOffEmptyOrMissing() {
        model.initializeCars(1, 1);

        controller.handleDropOffPassenger(1);

        assertEquals(
            "Car 1 does not exist or is empty.",
            view.errors.get(0));

        model.addPassenger("Maya", "Library");
        model.loadPassengerInCar();

        controller.handleDropOffPassenger(99);

        assertEquals(
            "Car 99 does not exist or is empty.",
            view.errors.get(1));
        assertEquals(
            "Maya",
            model.getCarById(1).getPassengers().get(0).getName());
        assertTrue(view.messages.isEmpty());
    }

    /**
     * Checks that passenger information reaches the view.
     */
    @Test
    public void testViewPassengers() {
        controller.handleViewPassengers();

        assertEquals(1, view.passengerDisplays);
        assertTrue(view.shownWaiting.isEmpty());
        assertTrue(view.shownCars.isEmpty());

        model.initializeCars(1, 2);
        model.addPassenger("Maya", "Library");
        model.loadPassengerInCar();
        model.addPassenger("Sam", "Gym");

        controller.handleViewPassengers();

        assertEquals(2, view.passengerDisplays);
        assertEquals(1, view.shownWaiting.size());
        assertEquals("Sam", view.shownWaiting.get(0).getName());
        assertEquals(1, view.shownCars.size());
        assertSame(model.getCarById(1), view.shownCars.get(0));
        assertEquals(
            "Maya",
            view.shownCars.get(0).getPassengers().get(0).getName());
    }

    /**
     * Checks that car information reaches the view.
     */
    @Test
    public void testViewCars() {
        controller.handleViewCars();

        assertEquals(1, view.carDisplays);
        assertTrue(view.shownCars.isEmpty());

        model.initializeCars(2, 4);
        controller.handleViewCars();

        assertEquals(2, view.carDisplays);
        assertEquals(2, view.shownCars.size());
        assertSame(model.getCarById(1), view.shownCars.get(0));
        assertSame(model.getCarById(2), view.shownCars.get(1));
    }

    /**
     * Checks the current roadmap placeholder message.
     */
    @Test
    public void testRoadmapDisplay() {
        controller.handleRoadmapDisplay();

        assertEquals(
            "Roadmap not implemented yet.",
            view.messages.get(0));
    }

    /**
     * Checks the current inactive time-simulation behavior.
     */
    @Test
    public void testTimeSimulationPlaceholder() {
        model.updateTravelTime(5);

        controller.handleTimeSimulation(10);

        assertEquals(
            "Time Simulation not completed yet.",
            view.messages.get(0));
        assertEquals(5L, model.getElapsedMinutes());
    }

    /**
     * Checks menu choices 1 through 4 using automatic input.
     */
    @Test
    public void testMenuRideActions() {
        view.addInput("1", "2");
        controller.handleMenuChoice(1);

        assertEquals(1, model.getCars().size());
        assertEquals(2, model.getCarById(1).getCapacity());

        view.addInput("Maya", "Library");
        controller.handleMenuChoice(2);

        assertEquals(1, model.getWaitingCount());
        assertEquals(
            "Maya", model.getWaitingPassengers().get(0).getName());

        controller.handleMenuChoice(3);

        assertEquals(0, model.getWaitingCount());
        assertEquals(1, model.getCarById(1).getPassengers().size());

        view.addInput("1");
        controller.handleMenuChoice(4);

        assertTrue(model.getCarById(1).isEmpty());
        assertEquals(
            "Dropped off Maya at Library.",
            view.messages.get(3));
        assertTrue(view.errors.isEmpty());
        assertTrue(view.input.isEmpty());
    }

    /**
     * Checks that option 4 does not ask for input when no cars exist.
     */
    @Test
    public void testMenuDropOffWithoutCars() {
        controller.handleMenuChoice(4);

        assertEquals(0, view.inputReads);
        assertEquals(1, view.errors.size());
        assertEquals(
            "No cars have been set up. Use option 1 to "
                + "initialize cars first.",
            view.errors.get(0));
    }

    /**
     * Checks the display and placeholder menu options.
     */
    @Test
    public void testMenuDisplayActions() {
        controller.handleMenuChoice(5);
        controller.handleMenuChoice(6);
        controller.handleMenuChoice(7);
        controller.handleMenuChoice(8);

        assertEquals(1, view.passengerDisplays);
        assertEquals(1, view.carDisplays);
        assertEquals(
            "Roadmap not implemented yet.",
            view.messages.get(0));
        assertEquals(
            "Time Simulation not completed yet.",
            view.messages.get(1));
        assertEquals(0, view.inputReads);
    }

    /**
     * Checks invalid menu choices.
     */
    @Test
    public void testInvalidMenuChoice() {
        controller.handleMenuChoice(-1);
        controller.handleMenuChoice(99);

        assertEquals(2, view.errors.size());
        assertEquals("Invalid choice.", view.errors.get(0));
        assertEquals("Invalid choice.", view.errors.get(1));
        assertTrue(view.messages.isEmpty());
    }

    /**
     * Checks quitting immediately.
     */
    @Test
    public void testStartQuit() {
        view.addInput("0");

        controller.start();

        assertEquals(1, view.menuDisplays);
        assertEquals("Program ended.", view.messages.get(0));
        assertTrue(view.errors.isEmpty());
    }

    /**
     * Checks that the menu processes actions until the user quits.
     */
    @Test
    public void testStartProcessesChoices() {
        // Choose initialize, enter 1 car and 2 seats,
        // choose an invalid option, and then quit.
        view.addInput("1", "1", "2", "99", "0");

        controller.start();

        assertEquals(3, view.menuDisplays);
        assertEquals(1, model.getCars().size());
        assertEquals(2, model.getCarById(1).getCapacity());
        assertEquals(
            "Created 1 car(s) with capacity 2.",
            view.messages.get(0));
        assertEquals("Invalid choice.", view.errors.get(0));
        assertEquals("Program ended.", view.messages.get(1));
        assertTrue(view.input.isEmpty());
    }

    /**
     * Checks that ending input exits the program.
     */
    @Test
    public void testStartEndOfInput() {
        controller.start();

        assertEquals(1, view.menuDisplays);
        assertEquals(
            "No more input. Program ended.",
            view.messages.get(0));
    }

    /**
     * Checks input ending partway through a menu action.
     */
    @Test
    public void testStartInputEndsDuringAction() {
        // Choose initialize and provide a car count,
        // but provide no capacity.
        view.addInput("1", "2");

        controller.start();

        assertTrue(model.getCars().isEmpty());
        assertEquals(
            "No more input. Program ended.",
            view.messages.get(0));
    }

    /**
     * A view used by tests to supply input and record output.
     */
    private static class TestView extends RideshareView {
        private final Deque<String> input = new ArrayDeque<>();
        private final List<String> messages = new ArrayList<>();
        private final List<String> errors = new ArrayList<>();

        private List<Passenger> shownWaiting;
        private List<Car> shownCars;

        private int menuDisplays;
        private int passengerDisplays;
        private int carDisplays;
        private int inputReads;

        /**
         * Supplies input lines for the controller to read.
         *
         * @param lines the simulated input
         */
        public void addInput(String... lines) {
            for (String line : lines) {
                input.addLast(line);
            }
        }

        @Override
        public void displayMenu() {
            menuDisplays++;
        }

        @Override
        public String readString(String prompt) {
            inputReads++;
            return input.removeFirst();
        }

        @Override
        public int readInt(String prompt) {
            return Integer.parseInt(readString(prompt));
        }

        @Override
        public int readIntRaw(String prompt) {
            return readInt(prompt);
        }

        @Override
        public void displayMessage(String message) {
            messages.add(message);
        }

        @Override
        public void displayError(String message) {
            errors.add(message);
        }

        @Override
        public void displayPassengers(
            List<Passenger> waiting,
            List<Car> cars) {
            passengerDisplays++;
            shownWaiting = new ArrayList<>(waiting);
            shownCars = new ArrayList<>(cars);
        }

        @Override
        public void displayCars(List<Car> cars) {
            carDisplays++;
            shownCars = new ArrayList<>(cars);
        }
    }
}