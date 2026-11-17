package rideshare;

import java.util.ArrayList;
import java.util.List;
import util.Car;
import util.Passenger;
import util.Queue;

/**
 * Manages waiting passengers, cars, and ride assignments.
 *
 * @author Aishwarya Shah
 * @version 10 Nov 2026
 */
public class RideshareModel {
    private Queue<Passenger> waitingQueue;
    private List<Car> cars;
    private long elapsedMinutes;
    private List<Passenger> droppedOffPassengers;

    /**
    * Creates a model with no passengers or cars.
    */
    public RideshareModel() {
        waitingQueue = new Queue<>();
        cars = new ArrayList<>();
        elapsedMinutes = 0;
        droppedOffPassengers = new ArrayList<>();
    }

    /**
     * Creates cars when the values are valid and existing cars
     * are empty.
     *
     * @param numberOfCars how many cars to create
     * @param capacity the capacity of each car
     * @return whether initialization succeeded
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
     * Adds a passenger to the waiting queue.
     *
     * @param name the passenger's name
     * @param destination the passenger's destination
     * @return true when the passenger is added
     * @throws IllegalArgumentException if passenger information
     *         is invalid
     */
    public boolean addPassenger(String name, String destination) {
        Passenger passenger = new Passenger(name, destination);
        waitingQueue.enqueue(passenger);
        return true;
    }

    /**
     * Moves the first waiting passenger into an available car.
     *
     * @return whether a passenger was assigned
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

        Passenger passenger = car.dropOffPassenger();

        if (passenger != null) {
            droppedOffPassengers.add(passenger);
        }

        return passenger;
    }

    /**
     * Returns a separate list containing the cars.
     *
     * @return the car list
     */
    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }

    /**
     * Returns waiting passengers in FIFO order.
     *
     * @return a separate list of waiting passengers
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
     * @return the matching car, or null if missing
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
     * Returns the number of waiting passengers.
     *
     * @return the waiting count
     */
    public int getWaitingCount() {
        return waitingQueue.size();
    }

    /**
     * Advances the simulation clock.
     *
     * @param minutesElapsed the minutes to add
     * @throws IllegalArgumentException if minutes are negative
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

    /**
    * Returns dropped-off passengers in the order they were dropped off.
     *
     * @return a separate list of dropped-off passengers
     */
    public List<Passenger> getDroppedOffPassengers() {
        return new ArrayList<>(droppedOffPassengers);
    }
}