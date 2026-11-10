import java.util.ArrayList;
import java.util.List;

/**
 * Represents a car with a FIFO passenger queue.
 * 
 * @author Aishwarya Shah aishwaryashah28
 * @version 11 Nov 2026
 */
public class Car {
    private int carId;
    private Queue passengerQueue;
    private int capacity;

    /**
     * Creates an empty car.
     *
     * @param carId the car's ID
     * @param capacity the maximum number of passengers
     * @throws IllegalArgumentException if either number is below 1
     */
    public Car(int carId, int capacity) {
        if (carId < 1 || capacity < 1) {
            throw new IllegalArgumentException(
                "Car ID and capacity must both be at least 1.");
        }

        this.carId = carId;
        this.capacity = capacity;
        passengerQueue = new Queue();
    }

    /**
     * Returns the car's ID.
     *
     * @return the ID
     */
    public int getCarId() {
        return carId;
    }

    /**
     * Returns the maximum number of passengers.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the internal queue for model operations.
     *
     * @return the passenger queue
     */
    public Queue getPassengerQueue() {
        return passengerQueue;
    }

    /**
     * Adds a passenger if a seat is available.
     *
     * @param passenger the passenger to add
     * @return true if added, or false if null or full
     */
    public boolean loadPassenger(Passenger passenger) {
        if (passenger == null || isFull()) {
            return false;
        }

        passengerQueue.enqueue(passenger);
        return true;
    }

    /**
     * Removes the passenger who entered first.
     *
     * @return the removed passenger, or null if empty
     */
    public Passenger dropOffPassenger() {
        if (isEmpty()) {
            return null;
        }

        return passengerQueue.dequeue();
    }

    /**
     * Checks whether all seats are occupied.
     *
     * @return true if full
     */
    public boolean isFull() {
        return passengerQueue.size() >= capacity;
    }

    /**
     * Checks whether the car has no passengers.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return passengerQueue.isEmpty();
    }

    /**
     * Returns the next passenger's destination.
     *
     * @return the next destination, or null if empty
     */
    public String getDestination() {
        if (isEmpty()) {
            return null;
        }

        return passengerQueue.peek().getDestination();
    }

    /**
     * Returns a separate list of passengers in drop-off order.
     *
     * @return a passenger list, or an empty list
     */
    public List<Passenger> getPassengers() {
        if (isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(passengerQueue.toList());
    }
}