import java.util.*;

/*
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Demonstrates safe room allocation while preventing double booking
 */

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 3);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

class BookingService {

    private Queue<Reservation> bookingQueue = new LinkedList<>();

    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    private Set<String> usedRoomIds = new HashSet<>();

    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addBookingRequest(Reservation reservation) {
        bookingQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.guestName +
                " (" + reservation.roomType + ")");
    }

    private String generateRoomId(String roomType) {
        String roomId;

        do {
            roomId = roomType.substring(0, 1).toUpperCase() + (100 + new Random().nextInt(900));
        } while (usedRoomIds.contains(roomId));

        usedRoomIds.add(roomId);
        return roomId;
    }

    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            Reservation reservation = bookingQueue.poll();

            System.out.println("\nProcessing booking for " + reservation.guestName);

            if (!inventoryService.isAvailable(reservation.roomType)) {
                System.out.println("No rooms available for type: " + reservation.roomType);
                continue;
            }

            String roomId = generateRoomId(reservation.roomType);

            allocatedRooms.putIfAbsent(reservation.roomType, new HashSet<>());
            allocatedRooms.get(reservation.roomType).add(roomId);

            inventoryService.decrementInventory(reservation.roomType);

            System.out.println("Reservation confirmed!");
            System.out.println("Guest: " + reservation.guestName);
            System.out.println("Room Type: " + reservation.roomType);
            System.out.println("Assigned Room ID: " + roomId);
        }
    }

    public void printAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        bookingService.addBookingRequest(new Reservation("Alice", "Single"));
        bookingService.addBookingRequest(new Reservation("Bob", "Double"));
        bookingService.addBookingRequest(new Reservation("Charlie", "Single"));
        bookingService.addBookingRequest(new Reservation("David", "Suite"));
        bookingService.addBookingRequest(new Reservation("Eva", "Single"));

        System.out.println("\n--- Processing Booking Queue ---");

        bookingService.processBookings();

        bookingService.printAllocatedRooms();

        inventoryService.printInventory();
    }
}