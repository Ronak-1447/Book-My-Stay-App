import java.util.*;

/*
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 */

// Reservation class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Thread-safe Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Critical section: synchronized method
    public synchronized boolean bookRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            System.out.println(Thread.currentThread().getName() +
                    " → Booking FAILED for " + guestName +
                    " (" + roomType + ")");
            return false;
        }

        // Simulate delay (to expose race conditions if not synchronized)
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        inventory.put(roomType, available - 1);

        System.out.println(Thread.currentThread().getName() +
                " → Booking SUCCESS for " + guestName +
                " (" + roomType + ")");
        return true;
    }

    public void printInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Shared Booking Queue (Thread-safe access)
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }
}

// Booking Processor (Runnable Thread)
class BookingProcessor implements Runnable {

    private BookingQueue bookingQueue;
    private InventoryService inventoryService;

    public BookingProcessor(BookingQueue queue, InventoryService inventory) {
        this.bookingQueue = queue;
        this.inventoryService = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // Critical section: retrieving request
            synchronized (bookingQueue) {
                r = bookingQueue.getNextRequest();
            }

            if (r == null) break;

            inventoryService.bookRoom(r.roomType, r.guestName);
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulate multiple guest requests
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // exceeds inventory
        queue.addRequest(new Reservation("David", "Double"));
        queue.addRequest(new Reservation("Eve", "Double")); // exceeds inventory

        // Create multiple threads (simulating concurrent users)
        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(queue, inventory), "Thread-3");

        System.out.println("--- Starting Concurrent Booking ---");

        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {}

        System.out.println("\n--- Booking Completed ---");
        inventory.printInventory();
    }
}