import java.util.HashMap;

/**
 * UseCase4RoomSearch
 *
 * Demonstrates searching available rooms without modifying
 * the inventory state in the Hotel Booking System.
 *
 * @author Student
 * @version 4.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println(" Hotel Booking System - Room Search ");
        System.out.println(" Version 4.1 ");
        System.out.println("=========================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search
        searchService.displayAvailableRooms(single);
        searchService.displayAvailableRooms(doubleRoom);
        searchService.displayAvailableRooms(suite);

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}


/**
 * RoomInventory
 *
 * Centralized inventory using HashMap.
 *
 * @version 4.0
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 0); // Example: Suite unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


/**
 * Search Service responsible for read-only access.
 *
 * @version 4.0
 */
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms(Room room) {

        int available = inventory.getAvailability(room.getRoomType());

        if (available > 0) {
            System.out.println("\nRoom Type: " + room.getRoomType());
            System.out.println("Beds: " + room.getBeds());
            System.out.println("Price per Night: $" + room.getPrice());
            System.out.println("Available Rooms: " + available);
        }
    }
}


/**
 * Abstract Room class
 *
 * @version 4.0
 */
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }
}


/**
 * Single Room
 *
 * @version 4.0
 */
class SingleRoom extends Room {

    public SingleRoom() {
        roomType = "Single Room";
        beds = 1;
        price = 100;
    }
}


/**
 * Double Room
 *
 * @version 4.0
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        roomType = "Double Room";
        beds = 2;
        price = 180;
    }
}


/**
 * Suite Room
 *
 * @version 4.0
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        roomType = "Suite Room";
        beds = 3;
        price = 350;
    }
}