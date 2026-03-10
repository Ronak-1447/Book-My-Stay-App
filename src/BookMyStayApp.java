/**
 * UseCase2RoomInitialization
 *
 * Demonstrates basic room initialization and static availability
 * using inheritance and abstraction in the Hotel Booking System.
 *
 * @author Student
 * @version 2.1
 */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Hotel Booking System - Version 2.1 ");
        System.out.println("=====================================");

        // Create room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 6;
        int suiteRoomAvailability = 3;

        System.out.println("\n--- Room Details ---");

        System.out.println("\nSingle Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);

        System.out.println("\nDouble Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);

        System.out.println("\nSuite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);

        System.out.println("\nApplication execution completed.");
    }
}

/**
 * Abstract class representing a generic hotel room.
 *
 * @version 2.0
 */
abstract class Room {

    protected String roomType;
    protected int numberOfBeds;
    protected double roomSize;
    protected double pricePerNight;

    public Room(String roomType, int numberOfBeds, double roomSize, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Room Size: " + roomSize + " sqm");
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

/**
 * Single Room class
 *
 * @version 2.0
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 20.0, 100.0);
    }
}

/**
 * Double Room class
 *
 * @version 2.0
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 35.0, 180.0);
    }
}

/**
 * Suite Room class
 *
 * @version 2.0
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 60.0, 350.0);
    }
}