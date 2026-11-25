
public class SeatBooking {

    private final boolean[] booked;
    private int bookingsMade = 0;

    public SeatBooking(int seats) {
        booked = new boolean[seats];
    }

    // Version 1: Without synchronization
    public void bookUnsafe(int seat) {
        if (!booked[seat]) {
            Thread.yield(); // Encourages competing threads
            booked[seat] = true;
            bookingsMade++;
        }
    }

    // Version 2: Synchronized method
    public synchronized void bookSafe(int seat) {
        if (!booked[seat]) {
            booked[seat] = true;
            bookingsMade++;
        }
    }

    // Version 3: Synchronized block
    public void bookWithBlock(int seat) {
        synchronized (this) {
            if (!booked[seat]) {
                booked[seat] = true;
                bookingsMade++;
            }
        }
    }

    public int getBookingsMade() {
        return bookingsMade;
    }
}
