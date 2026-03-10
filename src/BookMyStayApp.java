import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request intake using a FIFO queue
 * in the Hotel Booking System.
 *
 * Booking requests are stored in arrival order and
 * processed later by the allocation system.
 *
 * @author Student
 * @version 5.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println(" Hotel Booking System - Booking Requests ");
        System.out.println(" Version 5.1 ");
        System.out.println("=========================================");

        // Initialize booking queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Guests submit booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        System.out.println("\nCurrent Booking Requests in Queue:");

        requestQueue.displayQueue();

        System.out.println("\nRequests stored successfully.");
        System.out.println("No inventory updates performed at this stage.");
    }
}


/**
 * Reservation
 *
 * Represents a guest's booking request.
 *
 * @version 5.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/**
 * BookingRequestQueue
 *
 * Manages incoming booking requests using FIFO queue.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> bookingQueue;

    public BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }

    /**
     * Add new booking request
     */
    public void addRequest(Reservation reservation) {
        bookingQueue.add(reservation);
    }

    /**
     * Display current queue state
     */
    public void displayQueue() {

        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        for (Reservation r : bookingQueue) {
            System.out.println(
                    "Guest: " + r.getGuestName() +
                            " | Requested Room: " + r.getRoomType()
            );
        }
    }
}