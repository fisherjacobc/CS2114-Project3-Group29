package rideshare;

import java.util.List;
import java.util.Scanner;
import util.Car;
import util.Passenger;

/**
 * <h3>RideshareView</h3>
 *
 * Basic console view for the Rideshare Program. Prints output and reads
 * input from the console.
 *
 * @author fisherjc
 * @version 11 Nov 2026
 */
public class RideshareView {
    private Scanner scanner;

    /**
     * Creates a new RideshareView that reads from the console.
     */
    public RideshareView() {
        scanner = new Scanner(System.in);
    }


    /**
     * Prints the main menu.
     */
    public void displayMenu() {
        System.out.println();
        System.out.println("Rideshare");
        System.out.println("1. Initialize cars");
        System.out.println("2. Add passenger");
        System.out.println("3. Assign passenger to car");
        System.out.println("4. Drop off passenger");
        System.out.println("5. View passengers");
        System.out.println("6. View cars");
        System.out.println("7. Display roadmap");
        System.out.println("8. Simulate time");
        System.out.println("0. Quit");
    }


    /**
     * Prompts for and reads a line of text.
     *
     * @param prompt
     *            the prompt to print
     * @return the line entered
     */
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    /**
     * Prompts for and reads a whole number, asking again until one is
     * entered.
     *
     * @param prompt
     *            the prompt to print
     * @return the number entered
     */
    public int readInt(String prompt) {
        while (true) {
            String line = readString(prompt).trim();
            try {
                return Integer.parseInt(line);
            }
            catch (NumberFormatException e) {
                displayError("Please enter a whole number.");
            }
        }
    }


    /**
     * Prints a message.
     *
     * @param message
     *            the message to print
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }


    /**
     * Prints an error message.
     *
     * @param message
     *            the error to print
     */
    public void displayError(String message) {
        System.out.println("Error: " + message);
    }


    /**
     * Prints the waiting passengers and the passengers in each car.
     *
     * @param waiting
     *            the waiting passengers
     * @param cars
     *            the cars
     */
    public void displayPassengers(List<Passenger> waiting, List<Car> cars) {
        System.out.println("Waiting:");
        printPassengerList(waiting);

        for (Car car : cars) {
            System.out.println("Car " + car.getCarId() + ":");
            printPassengerList(car.getPassengers());
        }
    }


    /**
     * Prints each car's id and capacity.
     *
     * @param cars
     *            the cars
     */
    public void displayCars(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("No cars.");
            return;
        }

        for (Car car : cars) {
            System.out.println("Car " + car.getCarId() + " - "
                + car.getPassengers().size() + "/" + car.getCapacity()
                + " seats filled");
        }
    }


    /**
     * Prints a list of passengers, one per line.
     *
     * @param passengers
     *            the passengers to print
     */
    private void printPassengerList(List<Passenger> passengers) {
        if (passengers.isEmpty()) {
            System.out.println("  (none)");
            return;
        }

        for (Passenger passenger : passengers) {
            System.out.println("  " + passenger.getName() + " -> "
                + passenger.getDestination());
        }
    }
}
