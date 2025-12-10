package queue;
import java.util.*;

public class RailwaySystem {

    // Passenger class
    static class Passenger {
        private String name;

        public Passenger(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // Reservation System
    static class ReservationSystem {

        private int totalSeats;
        private int waitingListLimit;

        private List<Passenger> confirmedList;
        private Queue<Passenger> waitingList;

        public ReservationSystem(int totalSeats, int waitingListLimit) {
            this.totalSeats = totalSeats;
            this.waitingListLimit = waitingListLimit;

            confirmedList = new ArrayList<>();
            waitingList = new LinkedList<>();
        }

        // Book Ticket
        public void bookTicket(String name) {
            Passenger p = new Passenger(name);

            if (confirmedList.size() < totalSeats) {
                confirmedList.add(p);
                System.out.println("Ticket Confirmed for: " + name);
            } else if (waitingList.size() < waitingListLimit) {
                waitingList.add(p);
                System.out.println("Added to Waiting List: " + name);
            } else {
                System.out.println("No seats available & waiting list full. Booking failed.");
            }
        }

        // Cancel Ticket
        public void cancelTicket(String name) {
            Passenger toRemove = null;

            for (Passenger p : confirmedList) {
                if (p.getName().equalsIgnoreCase(name)) {
                    toRemove = p;
                    break;
                }
            }

            if (toRemove != null) {
                confirmedList.remove(toRemove);
                System.out.println("Ticket Cancelled for: " + name);

                // Promote from waiting list
                if (!waitingList.isEmpty()) {
                    Passenger next = waitingList.poll();
                    confirmedList.add(next);
                    System.out.println("Promoted from Waiting List: " + next.getName());
                }
            } else {
                System.out.println("Passenger not found in confirmed list.");
            }
        }

        // Show Status
        public void showStatus() {
            System.out.println("\n--- Confirmed Tickets ---");
            if (confirmedList.isEmpty()) System.out.println("None");
            for (Passenger p : confirmedList) {
                System.out.println(p.getName());
            }

            System.out.println("\n--- Waiting List ---");
            if (waitingList.isEmpty()) System.out.println("None");
            for (Passenger p : waitingList) {
                System.out.println(p.getName());
            }
            System.out.println();
        }
    }

    // MAIN PROGRAM
    public static void main(String[] args) {

        ReservationSystem rs = new ReservationSystem(3, 3); // seats = 3, waiting list = 3
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Railway Reservation System ===");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Show Status");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter passenger name: ");
                    String name = sc.nextLine();
                    rs.bookTicket(name);
                    break;

                case 2:
                    System.out.print("Enter passenger name to cancel: ");
                    String cancelName = sc.nextLine();
                    rs.cancelTicket(cancelName);
                    break;

                case 3:
                    rs.showStatus();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
