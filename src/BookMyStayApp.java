import java.io.*;
import java.util.*;

/*
 * Use Case 12: Data Persistence & System Recovery
 */

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Wrapper class to persist system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting fresh.");
            return null;
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;

        // Step 1: Load previous state
        SystemState loadedState = PersistenceService.load();

        if (loadedState != null) {
            bookingHistory = loadedState.bookingHistory;
            inventory = loadedState.inventory;
        } else {
            // Initialize fresh state
            bookingHistory = new ArrayList<>();
            inventory = new HashMap<>();

            inventory.put("Single", 2);
            inventory.put("Double", 1);
        }

        // Step 2: Simulate booking operations
        System.out.println("\n--- Current System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Bookings: " + bookingHistory);

        // Add new booking
        Reservation r1 = new Reservation("R101", "Alice", "Single");

        if (inventory.get("Single") > 0) {
            bookingHistory.add(r1);
            inventory.put("Single", inventory.get("Single") - 1);
            System.out.println("\nBooking added: " + r1);
        }

        // Step 3: Save state before shutdown
        SystemState state = new SystemState(bookingHistory, inventory);
        PersistenceService.save(state);

        System.out.println("\n--- Final System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Bookings: " + bookingHistory);
    }
}