import java.util.ArrayList;
import java.util.List;

/**
 * Manages waiting passengers, cars, and ride assignments.
 * 
 * @author Aishwarya Shah aishwaryashah28
 * @version 11 Nov 2026
 */
public class RideshareModel {
    private Queue waitingQueue;
    private List<Car> cars;
    private long elapsedMinutes;

    /**
     * Creates a model with no waiting passengers and no cars.
     */
    public RideshareModel() {
        waitingQueue = new Queue();
        cars = new ArrayList<>();
        elapsedMinutes = 0;
    }

    /**
     * Sets up cars with IDs starting at 1.
     * Existing cars can be replaced only when they are empty.
     *
     * @param numberOfCars how many cars to create
     * @param capacity the passenger capacity of each car
     * @return true if initialized, or false for invalid values
     *         or occupied existing cars
     */
    public boolean initializeCars(int numberOfCars, int capacity) {
        if (numberOfCars < 1 || capacity < 1) {
            return false;
        }

        for (Car car : cars) {
            if (!car.isEmpty()) {
                return false;
            }
        }

        cars.clear();

        for (int carId = 1; carId <= numberOfCars; carId++) {
            cars.add(new Car(carId, capacity));
        }

        return true;
    }

    /**
     * Creates a passenger and adds them to the waiting queue.
     *
     * @param name the passenger's name
     * @param destination the passenger's destination
     * @return true after the passenger is added
     * @throws IllegalArgumentException if the information is invalid
     */
    public boolean addPassenger(String name, String destination) {
        Passenger passenger = new Passenger(name, destination);
        waitingQueue.enqueue(passenger);
        return true;
    }

    /**
     * Moves the first waiting passenger into the first available car.
     *
     * @return true if assigned, or false if nobody is waiting
     *         or no car has space
     */
    public boolean loadPassengerInCar() {
        if (waitingQueue.isEmpty()) {
            return false;
        }

        for (Car car : cars) {
            if (car.loadPassenger(waitingQueue.peek())) {
                waitingQueue.dequeue();
                return true;
            }
        }

        return false;
    }

    /**
     * Drops off the next passenger from the selected car.
     *
     * @param carId the selected car's ID
     * @return the passenger, or null if the car is missing or empty
     */
    public Passenger dropOffPassenger(int carId) {
        Car car = getCarById(carId);

        if (car == null) {
            return null;
        }

        return car.dropOffPassenger();
    }

    /**
     * Returns a separate list containing the model's cars.
     *
     * @return the car list
     */
    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }

    /**
     * Returns a separate list of waiting passengers in FIFO order.
     *
     * @return the waiting passengers, or an empty list
     */
    public List<Passenger> getWaitingPassengers() {
        if (waitingQueue.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(waitingQueue.toList());
    }

    /**
     * Finds a car by its ID.
     *
     * @param carId the ID to find
     * @return the matching car, or null if it does not exist
     */
    public Car getCarById(int carId) {
        for (Car car : cars) {
            if (car.getCarId() == carId) {
                return car;
            }
        }

        return null;
    }

    /**
     * Returns the number of passengers waiting for a car.
     *
     * @return the waiting count
     */
    public int getWaitingCount() {
        return waitingQueue.size();
    }

    /**
     * Advances the simulation clock.
     *
     * @param minutesElapsed the number of minutes to add
     * @throws IllegalArgumentException if the number is negative
     */
    public void updateTravelTime(int minutesElapsed) {
        if (minutesElapsed < 0) {
            throw new IllegalArgumentException(
                "Elapsed minutes cannot be negative.");
        }

        elapsedMinutes += minutesElapsed;
    }

    /**
     * Returns the total simulated time.
     *
     * @return the elapsed minutes
     */
    public long getElapsedMinutes() {
        return elapsedMinutes;
    }
}