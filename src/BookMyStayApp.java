import java.util.*;

/*
 * Use Case 9: Error Handling & Validation
 * Demonstrates validation, custom exceptions, and fail-fast design
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
    }

    public boolean isRoomTypeValid(String roomType) {
        return inventory.containsKey(roomType);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementInventory(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);

        if (count <= 0) {
            throw new InvalidBookingException("Inventory cannot be negative for room type: " + roomType);
        }

        inventory.put(roomType, count - 1);
    }

    public void printInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

// Validator Class
class InvalidBookingValidator {

    public static void validate(Reservation reservation, InventoryService inventoryService)
            throws InvalidBookingException {

        if (reservation == null) {
            throw new InvalidBookingException("Reservation cannot be null");
        }

        if (reservation.guestName == null || reservation.guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name is required");
        }

        if (!inventoryService.isRoomTypeValid(reservation.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + reservation.roomType);
        }

        if (!inventoryService.isAvailable(reservation.roomType)) {
            throw new InvalidBookingException("No rooms available for type: " + reservation.roomType);
        }
    }
}

// Booking Service
class BookingService {

    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void confirmBooking(Reservation reservation) {

        try {
            // Step 1: Validate (Fail-Fast)
            InvalidBookingValidator.validate(reservation, inventoryService);

            // Step 2: Allocate (only if valid)
            inventoryService.decrementInventory(reservation.roomType);

            System.out.println("Booking CONFIRMED for " + reservation.guestName +
                    " (" + reservation.roomType + ")");

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("Booking FAILED: " + e.getMessage());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        // Test Cases

        Reservation r1 = new Reservation("R101", "Alice", "Single");   // Valid
        Reservation r2 = new Reservation("R102", "", "Double");        // Invalid name
        Reservation r3 = new Reservation("R103", "Charlie", "Luxury"); // Invalid room type
        Reservation r4 = new Reservation("R104", "David", "Suite");    // No availability
        Reservation r5 = new Reservation("R105", "Eve", "Single");     // Valid
        Reservation r6 = new Reservation("R106", "Frank", "Single");   // Exceeds inventory

        System.out.println("--- Processing Bookings ---");

        bookingService.confirmBooking(r1);
        bookingService.confirmBooking(r2);
        bookingService.confirmBooking(r3);
        bookingService.confirmBooking(r4);
        bookingService.confirmBooking(r5);
        bookingService.confirmBooking(r6);

        System.out.println("\nFinal Inventory State:");
        inventoryService.printInventory();
    }
}