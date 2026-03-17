import java.util.*;

/*
 * Use Case 8: Booking History & Reporting
 * Demonstrates storing confirmed bookings and generating reports
 */

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

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Booking History (Storage Only)
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation.reservationId);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service (Read-Only Operations)
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Print all bookings
    public void printAllBookings() {
        System.out.println("\n--- Booking History ---");

        List<Reservation> reservations = history.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport() {
        System.out.println("\n--- Booking Summary Report ---");

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            roomTypeCount.put(r.roomType,
                    roomTypeCount.getOrDefault(r.roomType, 0) + 1);
        }

        for (String type : roomTypeCount.keySet()) {
            System.out.println(type + " Rooms Booked: " + roomTypeCount.get(type));
        }

        System.out.println("Total Reservations: " + history.getAllReservations().size());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        Reservation r1 = new Reservation("R101", "Alice", "Single");
        Reservation r2 = new Reservation("R102", "Bob", "Double");
        Reservation r3 = new Reservation("R103", "Charlie", "Single");
        Reservation r4 = new Reservation("R104", "David", "Suite");

        // Add to history
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);
        bookingHistory.addReservation(r4);

        // Reporting
        BookingReportService reportService = new BookingReportService(bookingHistory);

        reportService.printAllBookings();
        reportService.generateSummaryReport();
    }
}