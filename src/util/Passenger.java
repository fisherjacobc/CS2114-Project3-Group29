/**
 * Stores one person's name and destination
 * 
 * @author Aishwarya Shah aishwaryashah28
 * @version 11 Nov 2026
 */

public class Passenger {
    private String name;
    private String destination;

    /**
     * creates a passenger with valid information
     * 
     * @param name name of the passenger
     * @param destination where passenger is going
     * @throws IllegalArgumentException is name or destination is null or empty
     */

    public Passenger(String name, String destination) {
        if (name == null || destination == null) {
            throw new IllegalArgumentException(
                "Name and destination are required.");
        }

        String cleanedName = name.trim();
        String cleanedDestination = destination.trim();

        if (cleanedName.isEmpty() || cleanedDestination.isEmpty()) {
            throw new IllegalArgumentException(
                "Name and destination cannot be blank.");
        }

        if (cleanedName.length() > 50) {
            throw new IllegalArgumentException(
                "Name cannot be longer than 50 characters.");
        }

        if (cleanedDestination.length() > 100) {
            throw new IllegalArgumentException(
                "Destination cannot be longer than 100 characters.");
        }

        this.name = cleanedName;
        this.destination = cleanedDestination;
    }

    /**
     * Returns the passenger's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the passenger's destination.
     *
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }
}