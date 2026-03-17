import java.util.*;

/*
 * Use Case 7: Add-On Service Selection
 * Demonstrates attaching optional services to a reservation
 */

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

class AddOnService {
    String serviceName;
    double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.serviceName +
                " to Reservation ID: " + reservationId);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {
        double total = 0.0;

        List<AddOnService> services = getServices(reservationId);

        for (AddOnService service : services) {
            total += service.cost;
        }

        return total;
    }

    // Print all services for a reservation
    public void printServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        System.out.println("\nServices for Reservation ID: " + reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService service : services) {
            System.out.println("- " + service);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // Sample reservations (Assume already confirmed from Use Case 6)
        Reservation r1 = new Reservation("R101", "Alice", "Single");
        Reservation r2 = new Reservation("R102", "Bob", "Double");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);

        // Add services to reservations
        manager.addService(r1.reservationId, wifi);
        manager.addService(r1.reservationId, breakfast);

        manager.addService(r2.reservationId, airportPickup);

        // Display services
        manager.printServices(r1.reservationId);
        manager.printServices(r2.reservationId);
    }
}