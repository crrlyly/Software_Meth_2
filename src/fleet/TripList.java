package fleet;
import Util.List;
/**
 * This class creates a circularly linked list of trips.
 * Additionally, it inserts the trips in order of earliest end date, and can print the list/find the number of trips in the list.
 *
 * @author Alec Cotler
 */
public class TripList extends List<Trip>{
    private Node last; //the reference to the last node of the linked list.

    /**
     * Constructor for TripList
     */
    public TripList() {

    }

    /**
     * Adds a trip to the trip linked list in order of ending date.
     *
     * @param trip , the trip object to be added to the list
     */
    @Override
    public void add(Trip trip) {
        if (trip == null)
            return;
        Node newNode = new Node(trip);

        if (last == null) {
            last = newNode;
            last.next = last; // circular link
            return;
        }

        Node curr = last.next; // head
        Node prev = last;

        int compare = compareTrips(trip, curr.trip);
        // If trip ends before or equal to head
        if (compare<0) {
            newNode.next = curr;
            last.next = newNode;
            return;
        }

        // if new trip ends after or equal to the last trip
        compare = compareTrips(trip, curr.trip);

        if (compare>=0) {
            newNode.next = curr;      // new last  point to head
            last.next = newNode;      // previous last points to new last
            last = newNode;           // update last
            return;
        }

        // Traverse to find insertion point
        while (curr != last) {
            compare = compareTrips(trip, curr.trip);

            if (compare < 0)
                break;

            prev = curr;
            curr = curr.next;
        }

        // Insert between prev and curr
        newNode.next = curr;
        prev.next = newNode;
    }

    private int compareTrips(Trip t1, Trip t2) {
        int cmp = t1.getBooking().getEnd().compareTo(t2.getBooking().getEnd());
        if (cmp == 0)
            cmp = t1.getBooking().getVehicle().getPlate().compareTo(t2.getBooking().getVehicle().getPlate());
        return cmp;
    }
    /**
     * Prints all the trips in the trip linked list.
     */
    public void print() {
        if (last == null) {
            System.out.println("No trips in the list.");
            return;
        }
        Node head = last.next;
        Node curr = head;

        do {
            System.out.println(curr.trip);
            curr = curr.next;
        } while (curr != head);
    }

    /**
     * Returns the last node in the linked list.
     *
     * @return last the last node in the linked list
     */
    public Node getLast() {
        return last;
    }
}
