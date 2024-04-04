import java.util.*;

public class HotelBookingManager {
    private final int numberOfRooms;
    private final Map<Integer, Map<Date, String>> bookings;
    private final Object obj = new Object();

    // Initializes a new instance
    public HotelBookingManager(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
        bookings = new HashMap<>();
        for (int i = 1; i <= numberOfRooms; i++) {
            bookings.put(i, new HashMap<>());
        }
    }

    // Book a room for a guest
    public void bookRoom(String guestName, int roomNumber, Date date) {
        synchronized (obj) {
            if (roomNumber < 1 || roomNumber > numberOfRooms) {
                throw new IllegalArgumentException("Invalid room number");
            }
            Map<Date, String> roomBookings = bookings.get(roomNumber);
            if (roomBookings.containsKey(date)) {
                throw new IllegalStateException("Room already booked for this date");
            }
            roomBookings.put(date, guestName);
        }
    }

    // Find available rooms
    public List<Integer> findAvailableRooms(Date date) {
        synchronized (obj) { 
            List<Integer> availableRooms = new ArrayList<>();
            for (Map.Entry<Integer, Map<Date, String>> entry : bookings.entrySet()) {
                if (!entry.getValue().containsKey(date)) {
                    availableRooms.add(entry.getKey());
                }
            }
            return availableRooms;
        }
    }

    // Find rooms booked 
    public List<Integer> findBookedRooms(String guestName) {
        synchronized (obj) {
            List<Integer> bookedRooms = new ArrayList<>();
            for (Map.Entry<Integer, Map<Date, String>> entry : bookings.entrySet()) {
                for (Map.Entry<Date, String> booking : entry.getValue().entrySet()) {
                    if (booking.getValue().equals(guestName)) {
                        bookedRooms.add(entry.getKey());
                        break;
                    }
                }
            }
            return bookedRooms;
        }
    }

    public static void main(String[] args) {
        HotelBookingManager bookingManager = new HotelBookingManager(5);

        // Test cases
     
        // Test finding available rooms
        HotelBookingManager bookingManager1 = new HotelBookingManager(5);
        
	bookingManager1.bookRoom("Rahul", 1, new Date(2024, 3, 4)); // Date format: year, month, day
        bookingManager1.bookRoom("Nikhil", 2, new Date(2024, 3, 4));
	bookingManager1.bookRoom("Miloni", 3, new Date(2024, 3, 4));
	bookingManager1.bookRoom("Mayuri", 4, new Date(2024, 3, 4));
	bookingManager1.bookRoom("Goel", 5, new Date(2024, 3, 5));
        
	System.out.println("Available rooms on 2024-03-04: " + bookingManager1.findAvailableRooms(new Date(2024, 3, 4)).size());
        System.out.println("Available rooms on 2024-03-05: " + bookingManager1.findAvailableRooms(new Date(2024, 3, 5)).size());

        // Test finding booked rooms
        HotelBookingManager bookingManager2 = new HotelBookingManager(5);

        bookingManager2.bookRoom("Rahul", 1, new Date(2024, 3, 4)); // Date format: year, month, day
        bookingManager2.bookRoom("Nikhil", 2, new Date(2024, 3, 4));
	bookingManager2.bookRoom("Miloni", 3, new Date(2024, 3, 4));
	bookingManager2.bookRoom("Mayuri", 4, new Date(2024, 3, 4));
	bookingManager2.bookRoom("Goel", 5, new Date(2024, 3, 5));

        System.out.println("Rooms booked by Rahul: " + bookingManager2.findBookedRooms("Rahul").size());
        System.out.println("Rooms booked by Nikhil: " + bookingManager2.findBookedRooms("Nikhil").size());
    }

}
