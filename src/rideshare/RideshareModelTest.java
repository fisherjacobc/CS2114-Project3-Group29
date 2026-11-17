package rideshare;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.*;


/**
 * Tests the RideshareModel class without using controller or view classes.
 * 
 * @author Aishwarya Shah
 * @version 17 Nov 2026
 */

public class RideshareModelTest {
    private RideshareModel model;

    /**
     * Creates a new model before each test
     */
    @BeforeEach
    public void setUp() {
        model = new RideshareModel();
    }

    /**
     * Checks the starting state of each new model
     */
    @Test
    public void testConstructor() {
        assertTrue(model.getCars().isEmpty());
        assertTrue(model.getWaitingPassengers().isEmpty());
        assertEquals(0, model.getWaitingCount());
        assertEquals(0L, model.getElapsedMinutes());
        assertNull(model.getCarById(1));
    }

    /**
     * Checks that separate models are different state
     */
    @Test
    public void testSeparateModels() {
        RideshareModel other = new RideshareModel();

        model.initializeCars(1, 2);
        model.addPassenger("Maya", "Library");
        model.updateTravelTime(5);

        assertTrue(other.getCars().isEmpty());
        assertEquals(0, other.getWaitingCount());
        assertEquals(0L, other.getElapsedMinutes());
    }

    /**
     * Checks that cars receive the correct IDs and capacities
     */
    @Test
    public void testInitializeCars() {
        assertTrue(model.initializeCars(2, 3));

        assertEquals(2, model.getCars().size());

        Car first = model.getCarById(1);
        Car second = model.getCarById(2);

        assertNotNull(first);
        assertNotNull(second);

        assertEquals(1, first.getCarId());
        assertEquals(2, second.getCarId());
        assertEquals(3, first.getCapacity());
        assertEquals(3, second.getCapacity());

        assertTrue(first.isEmpty());
        assertTrue(second.isEmpty());
    }

    /**
     * Checks invalid car counts and capacities
     */
    @Test
    public void testInitializeCarsInvalid() {
        assertFalse(model.initializeCars(0, 2));
        assertFalse(model.initializeCars(-1, 2));
        assertFalse(model.initializeCars(2, 0));
        assertFalse(model.initializeCars(2, -1));

        assertTrue(model.getCars().isEmpty());

        model.initializeCars(1, 2);
        Car original = model.getCarById(1);

        assertFalse(model.initializeCars(2, 0));
        assertEquals(1, model.getCars().size());
        assertSame(original, model.getCarById(1));
    }

    /**
     * Checks that empty cars can be replaced
     */
    @Test
    public void testReplaceEmptyCars() {
        model.initializeCars(2, 3);
        model.addPassenger("Maya", "Library");

        assertTrue(model.initializeCars(1, 4));

        assertEquals(1, model.getCars().size());
        assertEquals(4, model.getCarById(1).getCapacity());
        assertNull(model.getCarById(2));

        // Replacing empty cars should preserve waiting passengers.
        assertEquals(1, model.getWaitingCount());
        assertEquals(
            "Maya", model.getWaitingPassengers().get(0).getName());
    }

    /**
     * Checks that cars cannot be replaced while one is occupied
     */
    @Test
    public void testReplaceOccupiedCars() {
        model.initializeCars(2, 1);
        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");

        assertTrue(model.loadPassengerInCar());
        assertTrue(model.loadPassengerInCar());

        // Leave the first car empty and the second car occupied
        model.dropOffPassenger(1);
        Car second = model.getCarById(2);

        assertFalse(model.initializeCars(3, 4));

        assertEquals(2, model.getCars().size());
        assertSame(second, model.getCarById(2));
        assertEquals("Sam", model.dropOffPassenger(2).getName());
    }

    /**
     * Checks that valid passengers join the waiting queue in order
     */
    @Test
    public void testAddPassenger() {
        assertTrue(model.addPassenger(" Maya ", " Library "));
        assertTrue(model.addPassenger("Sam", "Gym"));

        List<Passenger> waiting = model.getWaitingPassengers();

        assertEquals(2, model.getWaitingCount());
        assertEquals("Maya", waiting.get(0).getName());
        assertEquals("Library", waiting.get(0).getDestination());
        assertEquals("Sam", waiting.get(1).getName());
        assertEquals("Gym", waiting.get(1).getDestination());
    }

    /**
     * Checks that invalid passenger information is rejected
     */
    @Test
    public void testAddPassengerInvalid() {
        model.addPassenger("Maya", "Library");

        String longName =
            "12345678901234567890123456789012345678901234567890";

        String longDestination = longName + longName;

        String[][] invalidInputs = {
            { null, "Library" },
            { "Sam", null },
            { "", "Library" },
            { "Sam", "" },
            { "   ", "Library" },
            { "Sam", "   " },
            { longName + "X", "Library" },
            { "Sam", longDestination + "X" }
        };

        for (String[] input : invalidInputs) {
            try {
                model.addPassenger(input[0], input[1]);
                fail("Invalid passenger information should be rejected.");
            }
            catch (IllegalArgumentException exception) {
                // This is the expected exception.
            }

            assertEquals(1, model.getWaitingCount());
            assertEquals(
                "Maya", model.getWaitingPassengers().get(0).getName());
        }
    }

    /**
     * Checks that values exactly at the character limits are accepted
     */
    @Test
    public void testAddPassengerAtLimits() {
        String name =
            "12345678901234567890123456789012345678901234567890";
        String destination = name + name;

        assertTrue(model.addPassenger(name, destination));

        Passenger passenger = model.getWaitingPassengers().get(0);

        assertEquals(name, passenger.getName());
        assertEquals(destination, passenger.getDestination());
        assertEquals(1, model.getWaitingCount());
    }

    /**
     * Checks assignment when nobody is waiting
     */
    @Test
    public void testLoadWithNoWaitingPassengers() {
        model.initializeCars(1, 2);

        assertFalse(model.loadPassengerInCar());
        assertTrue(model.getCarById(1).isEmpty());
        assertEquals(0, model.getWaitingCount());
    }

    /**
     * Checks that passengers stay waiting when no cars exist
     */
    @Test
    public void testLoadWithNoCars() {
        model.addPassenger("Maya", "Library");

        assertFalse(model.loadPassengerInCar());
        assertEquals(1, model.getWaitingCount());
        assertEquals(
            "Maya", model.getWaitingPassengers().get(0).getName());
    }

    /**
     * Checks that one passenger moves into the car per assignment.
     */
    @Test
    public void testLoadPassengerInCar() {
        model.initializeCars(1, 2);
        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");

        assertTrue(model.loadPassengerInCar());

        assertEquals(1, model.getWaitingCount());
        assertEquals(
            "Sam", model.getWaitingPassengers().get(0).getName());

        List<Passenger> passengers =
            model.getCarById(1).getPassengers();

        assertEquals(1, passengers.size());
        assertEquals("Maya", passengers.get(0).getName());
    }

    /**
     * Checks that assignment skips full cars.
     */
    @Test
    public void testLoadIntoNextAvailableCar() {
        model.initializeCars(2, 1);
        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");

        assertTrue(model.loadPassengerInCar());
        assertTrue(model.loadPassengerInCar());

        assertEquals(
            "Maya",
            model.getCarById(1).getPassengers().get(0).getName());
        assertEquals(
            "Sam",
            model.getCarById(2).getPassengers().get(0).getName());

        assertEquals(0, model.getWaitingCount());
    }

    /**
     * Checks that a full car leaves the waiting queue unchanged
     */
    @Test
    public void testLoadWhenFull() {
        model.initializeCars(1, 1);
        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");

        assertTrue(model.loadPassengerInCar());
        assertFalse(model.loadPassengerInCar());

        assertEquals(1, model.getWaitingCount());
        assertEquals(
            "Sam", model.getWaitingPassengers().get(0).getName());
        assertEquals(
            "Maya",
            model.getCarById(1).getPassengers().get(0).getName());

        // Once a seat opens, the waiting passenger can enter.
        assertEquals("Maya", model.dropOffPassenger(1).getName());
        assertTrue(model.loadPassengerInCar());

        assertEquals(0, model.getWaitingCount());
        assertEquals("Sam", model.dropOffPassenger(1).getName());
    }

    /**
     * Checks that passengers are dropped off in FIFO order
     */
    @Test
    public void testDropOffPassenger() {
        model.initializeCars(1, 2);
        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");

        model.loadPassengerInCar();
        model.loadPassengerInCar();

        Passenger first = model.dropOffPassenger(1);
        Passenger second = model.dropOffPassenger(1);

        assertNotNull(first);
        assertNotNull(second);
        assertEquals("Maya", first.getName());
        assertEquals("Sam", second.getName());

        assertTrue(model.getCarById(1).isEmpty());
        assertNull(model.dropOffPassenger(1));
    }

    /**
     * Checks drop-offs for empty and missing cars
     */
    @Test
    public void testDropOffEmptyOrMissingCar() {
        model.initializeCars(1, 2);

        assertNull(model.dropOffPassenger(1));
        assertNull(model.dropOffPassenger(99));

        model.addPassenger("Maya", "Library");
        model.loadPassengerInCar();

        assertNull(model.dropOffPassenger(99));

        // An invalid car ID must not remove someone from another car
        assertEquals("Maya", model.dropOffPassenger(1).getName());
    }

    /**
     * Checks that changing the returned car list does not affect
     * the model's list.
     */
    @Test
    public void testGetCarsCopy() {
        model.initializeCars(2, 3);

        List<Car> returnedCars = model.getCars();

        assertEquals(2, returnedCars.size());
        returnedCars.clear();

        assertEquals(2, model.getCars().size());
        assertNotNull(model.getCarById(1));
        assertNotNull(model.getCarById(2));
    }

    /**
     * Checks waiting order and the returned list's independence
     */
    @Test
    public void testGetWaitingPassengersCopy() {
        assertTrue(model.getWaitingPassengers().isEmpty());

        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");

        List<Passenger> returnedPassengers =
            model.getWaitingPassengers();

        assertEquals("Maya", returnedPassengers.get(0).getName());
        assertEquals("Sam", returnedPassengers.get(1).getName());

        returnedPassengers.clear();

        assertEquals(2, model.getWaitingCount());
        assertEquals(2, model.getWaitingPassengers().size());
        assertEquals(
            "Maya", model.getWaitingPassengers().get(0).getName());
    }

    /**
     * Checks valid and invalid car IDs
     */
    @Test
    public void testGetCarById() {
        assertNull(model.getCarById(1));

        model.initializeCars(2, 3);

        assertEquals(1, model.getCarById(1).getCarId());
        assertEquals(2, model.getCarById(2).getCarId());

        assertNull(model.getCarById(0));
        assertNull(model.getCarById(-1));
        assertNull(model.getCarById(99));
    }

    /**
     * Checks the waiting count as passengers enter cars
     */
    @Test
    public void testGetWaitingCount() {
        model.initializeCars(1, 2);

        assertEquals(0, model.getWaitingCount());

        model.addPassenger("Maya", "Library");
        model.addPassenger("Sam", "Gym");
        assertEquals(2, model.getWaitingCount());

        model.loadPassengerInCar();
        assertEquals(1, model.getWaitingCount());

        // Dropping off a car passenger does not remove a waiting person
        model.dropOffPassenger(1);
        assertEquals(1, model.getWaitingCount());

        model.loadPassengerInCar();
        assertEquals(0, model.getWaitingCount());
    }

    /**
     * Checks that positive travel times accumulate
     */
    @Test
    public void testUpdateTravelTime() {
        model.updateTravelTime(5);
        assertEquals(5L, model.getElapsedMinutes());

        model.updateTravelTime(10);
        assertEquals(15L, model.getElapsedMinutes());

        model.updateTravelTime(0);
        assertEquals(15L, model.getElapsedMinutes());
    }

    /**
     * Checks that negative time is rejected without changing the clock
     */
    @Test
    public void testUpdateTravelTimeNegative() {
        model.updateTravelTime(5);

        try {
            model.updateTravelTime(-1);
            fail("Negative travel time should be rejected.");
        }
        catch (IllegalArgumentException exception) {
            // This is the expected exception.
        }

        assertEquals(5L, model.getElapsedMinutes());
    }

    /**
     * Checks that elapsed time can exceed the maximum int value
     */
    @Test
    public void testElapsedMinutesLargeValue() {
        model.updateTravelTime(Integer.MAX_VALUE);
        model.updateTravelTime(1);

        assertEquals(2147483648L, model.getElapsedMinutes());
    }
}
