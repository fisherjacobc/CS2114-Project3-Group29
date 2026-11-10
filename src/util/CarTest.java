package util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Car class. Tests the constructor, loading and
 * dropping off passengers, capacity checks, and the passenger list.
 *
 * @author fisherjc
 * @version 11 Nov 2026
 */
public class CarTest {
    private Car car;
    private Passenger jacob;
    private Passenger aishwarya;
    private Passenger john;

    /**
     * Sets up a fresh Car instance and passengers before each test executes
     */
    @BeforeEach
    public void setUp() {
        car = new Car(1, 2);
        jacob = new Passenger("Jacob", "Gym");
        aishwarya = new Passenger("Aishwarya", "Library");
        john = new Passenger("John", "Dorm");
    }

    /**
     * Tests that a newly constructed car is empty with the given values
     */
    @Test
    public void testConstructor() {
        assertEquals(1, car.getCarId());
        assertEquals(2, car.getCapacity());
        assertTrue(car.isEmpty());
        assertFalse(car.isFull());
        assertNotNull(car.getPassengerQueue());
        assertTrue(car.getPassengerQueue().isEmpty());
    }

    /**
     * Tests that an ID or capacity below 1 throws an exception
     */
    @Test
    public void testConstructorInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Car(0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Car(1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Car(-1, -1));
    }

    /**
     * Tests loading a single passenger into an empty car
     */
    @Test
    public void testLoadPassenger() {
        assertTrue(car.loadPassenger(jacob));

        assertFalse(car.isEmpty());
        assertFalse(car.isFull());
        assertEquals(1, car.getPassengerQueue().size());
    }

    /**
     * Tests that loading a null passenger is rejected
     */
    @Test
    public void testLoadPassengerNull() {
        assertFalse(car.loadPassenger(null));
        assertTrue(car.isEmpty());
    }

    /**
     * Tests that loading stops once the car reaches capacity
     */
    @Test
    public void testLoadPassengerWhenFull() {
        assertTrue(car.loadPassenger(jacob));
        assertTrue(car.loadPassenger(aishwarya));
        assertTrue(car.isFull());

        assertFalse(car.loadPassenger(john));
        assertEquals(List.of(jacob, aishwarya), car.getPassengers());
    }

    /**
     * Tests that dropOffPassenger returns null on an empty car
     */
    @Test
    public void testDropOffWhenEmpty() {
        assertNull(car.dropOffPassenger());
        assertTrue(car.isEmpty());
    }

    /**
     * Tests that passengers are dropped off in first-in, first-out order
     */
    @Test
    public void testDropOffFIFO() {
        car.loadPassenger(jacob);
        car.loadPassenger(aishwarya);

        assertSame(jacob, car.dropOffPassenger());
        assertFalse(car.isFull());
        assertSame(aishwarya, car.dropOffPassenger());
        assertTrue(car.isEmpty());
        assertNull(car.dropOffPassenger());
    }

    /**
     * Tests that a seat opens up after a drop off
     */
    @Test
    public void testDropOffThenLoad() {
        car.loadPassenger(jacob);
        car.loadPassenger(aishwarya);
        car.dropOffPassenger();

        assertTrue(car.loadPassenger(john));
        assertTrue(car.isFull());
        assertEquals(List.of(aishwarya, john), car.getPassengers());
    }

    /**
     * Tests that getDestination returns null on an empty car
     */
    @Test
    public void testGetDestinationWhenEmpty() {
        assertNull(car.getDestination());
    }

    /**
     * Tests that getDestination returns the front passenger's destination
     */
    @Test
    public void testGetDestination() {
        car.loadPassenger(jacob);
        car.loadPassenger(aishwarya);
        assertEquals("Gym", car.getDestination());

        car.dropOffPassenger();
        assertEquals("Library", car.getDestination());
    }

    /**
     * Tests that getPassengers returns an empty list for an empty car
     */
    @Test
    public void testGetPassengersEmpty() {
        assertNotNull(car.getPassengers());
        assertTrue(car.getPassengers().isEmpty());
    }

    /**
     * Tests that getPassengers returns a separate copy in drop-off order
     */
    @Test
    public void testGetPassengersIsCopy() {
        car.loadPassenger(jacob);
        car.loadPassenger(aishwarya);

        List<Passenger> passengers = car.getPassengers();
        assertEquals(List.of(jacob, aishwarya), passengers);

        passengers.clear();
        assertEquals(List.of(jacob, aishwarya), car.getPassengers());
        assertEquals(2, car.getPassengerQueue().size());
    }
}
