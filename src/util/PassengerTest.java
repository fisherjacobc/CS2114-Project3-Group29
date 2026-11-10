package util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Passenger class. Tests the constructor, input
 * validation, and trimming of the name and destination.
 *
 * @author fisherjc
 * @version 11 Nov 2026
 */
public class PassengerTest {
    private Passenger passenger;

    /**
     * Sets up a fresh Passenger instance before each test executes
     */
    @BeforeEach
    public void setUp() {
        passenger = new Passenger("Alice", "Library");
    }

    /**
     * Tests that the constructor stores the name and destination
     */
    @Test
    public void testConstructor() {
        assertEquals("Alice", passenger.getName());
        assertEquals("Library", passenger.getDestination());
    }

    /**
     * Tests that the constructor trims surrounding whitespace
     */
    @Test
    public void testConstructorTrimsWhitespace() {
        Passenger trimmed = new Passenger("  Bob  ", "\tGym \n");

        assertEquals("Bob", trimmed.getName());
        assertEquals("Gym", trimmed.getDestination());
    }

    /**
     * Tests that a null name or destination throws an exception
     */
    @Test
    public void testConstructorNull() {
        assertThrows(IllegalArgumentException.class,
            () -> new Passenger(null, "Library"));
        assertThrows(IllegalArgumentException.class,
            () -> new Passenger("Alice", null));
        assertThrows(IllegalArgumentException.class,
            () -> new Passenger(null, null));
    }

    /**
     * Tests that an empty or blank name or destination throws an exception
     */
    @Test
    public void testConstructorBlank() {
        assertThrows(IllegalArgumentException.class,
            () -> new Passenger("", "Library"));
        assertThrows(IllegalArgumentException.class,
            () -> new Passenger("   ", "Library"));
        assertThrows(IllegalArgumentException.class,
            () -> new Passenger("Alice", ""));
        assertThrows(IllegalArgumentException.class,
            () -> new Passenger("Alice", "   "));
    }

    /**
     * Tests the 50 character limit on the name
     */
    @Test
    public void testNameLengthLimit() {
        String fifty = "a".repeat(50);
        assertEquals(fifty, new Passenger(fifty, "Library").getName());

        assertThrows(IllegalArgumentException.class,
            () -> new Passenger("a".repeat(51), "Library"));
    }

    /**
     * Tests the 100 character limit on the destination
     */
    @Test
    public void testDestinationLengthLimit() {
        String hundred = "b".repeat(100);
        assertEquals(hundred, new Passenger("Alice", hundred).getDestination());

        assertThrows(IllegalArgumentException.class,
            () -> new Passenger("Alice", "b".repeat(101)));
    }

    /**
     * Tests that length limits are checked after trimming
     */
    @Test
    public void testLengthLimitAfterTrim() {
        Passenger padded = new Passenger("  " + "a".repeat(50) + "  ",
            "  " + "b".repeat(100) + "  ");

        assertEquals(50, padded.getName().length());
        assertEquals(100, padded.getDestination().length());
    }
}
