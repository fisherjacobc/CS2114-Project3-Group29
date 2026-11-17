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
 * @version 10 Nov 2026
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
        Main.clearScreen();

        System.out.println("┌──Queue Ride──────────────────┐");
        System.out.println("│                              │");
        System.out.println("│ [1] Initialize Cars          │");
        System.out.println("│                              │");
        System.out.println("│ [2] Add Passenger            │");
        System.out.println("│ [3] Assign Passenger to Car  │");
        System.out.println("│ [4] Drop off Passenger       │");
        System.out.println("│                              │");
        System.out.println("│ [5] View Passengers          │");
        System.out.println("│ [6] View Cars                │");
        System.out.println("│ [7] Display Roadmap          │");
        System.out.println("│                              │");
        System.out.println("│ [8] Simulate Time (Inactive) │");
        System.out.println("│                              │");
        System.out.println("│ [0] Quit Queue Ride          │");
        System.out.println("├──────────────────────────────┘");
        System.out.print("└ Choice: ");
    }


    /**
     * Prompts for and reads a line of text.
     *
     * @param prompt
     *            the prompt to print
     * @return the line entered
     */
    public String readString(String prompt) {
        System.out.println();

        int width = Math.max(prompt.length() + 2, 13);
        System.out.println("┌──Prompt" + "─".repeat(width - 8) + "┐");
        System.out.println("│" + " ".repeat(width) + "│");
        System.out.println("│ " + prompt + " │");
        System.out.println("│" + " ".repeat(width) + "│");
        System.out.println("├" + "─".repeat(width) + "┘");
        System.out.print("└ Response: ");

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

    public int readIntRaw(String prompt) {
        String line = scanner.nextLine().trim();
            
        try {
            return Integer.parseInt(line);
        }
        catch (NumberFormatException e) {
            displayError("Please enter a whole number.");
            return readInt(prompt);
        }
    }


    /**
     * Prints a message.
     *
     * @param message
     *            the message to print
     */
    public void displayMessage(String message) {
        Main.clearScreen();

        int width = Math.max(message.length() + 2, 13);
        System.out.println("┌──Message" + "─".repeat(width - 9) + "┐");
        System.out.println("│" + " ".repeat(width) + "│");
        System.out.println("│ " + message + " │");
        System.out.println("│" + " ".repeat(width) + "│");
        System.out.println("└" + "─".repeat(width) + "┘");
    }


    /**
     * Prints an error message.
     *
     * @param message
     *            the error to print
     */
    public void displayError(String message) {
        Main.clearScreen();

        int width = Math.max(message.length() + 2, 11);
        System.out.println("┌──Error" + "─".repeat(width - 7) + "┐");
        System.out.println("│" + " ".repeat(width) + "│");
        System.out.println("│ " + message + " │");
        System.out.println("│" + " ".repeat(width) + "│");
        System.out.println("└" + "─".repeat(width) + "┘");

        Main.pause(scanner);
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

        if (cars.isEmpty()) {
            System.out.println("No cars.");
            return;
        }

        for (Car car : cars) {
            System.out.println("Car " + car.getCarId() + ":");
            printPassengerList(car.getPassengers());
        }

        Main.pause(scanner);
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

        Main.pause(scanner);
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
