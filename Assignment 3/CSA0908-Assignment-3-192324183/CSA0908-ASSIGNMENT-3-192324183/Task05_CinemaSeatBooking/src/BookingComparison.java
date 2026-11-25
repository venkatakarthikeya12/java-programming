
public class BookingComparison {

    static int runTest(int version) throws InterruptedException {

        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];

        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;

            users[i] = new Thread(() -> {
                if (version == 1) {
                    screen.bookUnsafe(seat);
                } else if (version == 2) {
                    screen.bookSafe(seat);
                } else {
                    screen.bookWithBlock(seat);
                }
            });

            users[i].start();
        }

        for (Thread t : users) {
            t.join();
        }

        return screen.getBookingsMade();
    }

    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("CINEMA SEAT BOOKING SYSTEM");

        System.out.println("\n--- Without Synchronization ---");

        for (int i = 1; i <= 10; i++) {
            System.out.println("Run " + i + ": Seats sold = "
                    + runTest(1));
        }

        System.out.println("\n--- Synchronized Method ---");
        System.out.println("Seats sold: " + runTest(2));

        System.out.println("\n--- Synchronized Block ---");
        System.out.println("Seats sold: " + runTest(3));
    }
}
