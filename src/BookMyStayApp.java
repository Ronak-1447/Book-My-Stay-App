import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management using HashMap.
 * The application initializes the inventory and displays available rooms.
 *
 * @author Student
 * @version 3.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println(" Hotel Booking System - Inventory Setup ");
        System.out.println(" Version 3.1 ");
        System.out.println("=========================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        System.out.println("\nCurrent Room Availability:");
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating Inventory...");
        inventory.updateAvailability("Single Room", -2);

        System.out.println("\nUpdated Room Availability:");
        inventory.displayInventory();

        System.out.println("\nApplication execution completed.");
    }
}


/**
 * RoomInventory
 *
 * Manages centralized room availability using a HashMap.
 * This class acts as the single source of truth for inventory data.
 *
 * @version 3.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /**
     * Constructor initializes room availability.
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types with availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    /**
     * Retrieve availability for a specific room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability for a room type
     */
    public void updateAvailability(String roomType, int change) {
        int current = getAvailability(roomType);
        inventory.put(roomType, current + change);
    }

    /**
     * Display full inventory
     */
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}