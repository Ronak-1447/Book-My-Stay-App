import java.util.*;

/*
 * Use Case 10: Booking Cancellation & Inventory Rollback
 */

// Reservation class
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType + " | RoomID: " + roomId;
    }
}

// Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public void incrementInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void decrementInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.reservationId, r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void removeReservation(String id) {
        reservations.remove(id);
    }

    public void printAll() {
        System.out.println("\nActive Bookings:");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}

// Cancellation Service
class CancellationService {

    private BookingHistory history;
    private InventoryService inventoryService;

    // Stack for rollback tracking (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(BookingHistory history, InventoryService inventoryService) {
        this.history = history;
        this.inventoryService = inventoryService;
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nAttempting cancellation for: " + reservationId);

        // Step 1: Validate existence
        Reservation reservation = history.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation FAILED: Reservation does not exist.");
            return;
        }

        // Step 2: Push room ID to rollback stack
        rollbackStack.push(reservation.roomId);

        // Step 3: Restore inventory
        inventoryService.incrementInventory(reservation.roomType);

        // Step 4: Remove booking from history
        history.removeReservation(reservationId);

        System.out.println("Cancellation SUCCESS for " + reservationId +
                ". Released Room ID: " + reservation.roomId);
    }

    public void printRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        Reservation r1 = new Reservation("R101", "Alice", "Single", "S101");
        Reservation r2 = new Reservation("R102", "Bob", "Double", "D201");
        Reservation r3 = new Reservation("R103", "Charlie", "Suite", "SU301");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        CancellationService cancellationService =
                new CancellationService(history, inventory);

        history.printAll();
        inventory.printInventory();

        // Perform cancellations
        cancellationService.cancelBooking("R102"); // valid
        cancellationService.cancelBooking("R999"); // invalid
        cancellationService.cancelBooking("R102"); // duplicate cancel

        history.printAll();
        inventory.printInventory();
        cancellationService.printRollbackStack();
    }
}