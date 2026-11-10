package util;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Queue class. Tests enqueueing, dequeueing, peeking,
 * clearing, and converting the queue to a list.
 *
 * @author fisherjc
 * @version 11 Nov 2026
 */
public class QueueTest {
    private Queue<String> queue;

    /**
     * Sets up a fresh Queue instance before each test executes
     */
    @BeforeEach
    public void setUp() {
        queue = new Queue<>();
    }

    /**
     * Tests that a newly constructed queue is empty
     */
    @Test
    public void testConstructor() {
        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        assertNull(queue.toList());
    }

    /**
     * Tests enqueueing a single entry into an empty queue
     */
    @Test
    public void testSingleEnqueue() {
        queue.enqueue("A");

        assertFalse(queue.isEmpty());
        assertEquals("A", queue.peek());
    }

    /**
     * Tests that enqueueing multiple entries keeps the first at the front
     */
    @Test
    public void testMultipleEnqueue() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.peek());
        assertEquals(List.of("A", "B", "C"), queue.toList());
    }
    /**
     * Tests that peek returns null on an empty queue
     */
    @Test
    public void testPeekWhenEmpty() {
        assertNull(queue.peek());
    }

    /**
     * Tests that peek does not remove the front entry
     */
    @Test
    public void testPeekDoesNotRemove() {
        queue.enqueue("A");

        assertEquals("A", queue.peek());
        assertEquals("A", queue.peek());
        assertFalse(queue.isEmpty());
    }

    /**
     * Tests that dequeue returns null on an empty queue
     */
    @Test
    public void testDequeueWhenEmpty() {
        assertNull(queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    /**
     * Tests that dequeue removes entries in first-in, first-out order
     */
    @Test
    public void testDequeueFIFO() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.peek());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
        assertTrue(queue.isEmpty());
        assertNull(queue.dequeue());
    }

    /**
     * Tests that the queue works correctly after being emptied by dequeue
     */
    @Test
    public void testDequeueEnqueue() {
        queue.enqueue("A");
        queue.dequeue();
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("B", queue.peek());
        assertEquals(List.of("B", "C"), queue.toList());
    }

    /**
     * Tests that clear empties a non-empty queue.
     */
    @Test
    public void testClear() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.clear();

        assertTrue(queue.isEmpty());
        assertNull(queue.peek());
        assertNull(queue.toList());
    }

    /**
     * Tests that the queue can be reused after being cleared
     */
    @Test
    public void testClearThenEnqueue() {
        queue.enqueue("A");
        queue.clear();
        queue.enqueue("B");

        assertEquals("B", queue.peek());
        assertEquals(List.of("B"), queue.toList());
    }

    /**
     * Tests that toList returns null for an empty queue
     */
    @Test
    public void testToListEmptyResult() {
        assertNull(queue.toList());
    }

    /**
     * Tests that toList returns entries front to back without modifying
     * the queue
     */
    @Test
    public void testToListWithoutModifying() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals(List.of("A", "B"), queue.toList());
        assertEquals("A", queue.peek());
        assertEquals(List.of("A", "B"), queue.toList());
    }
}
