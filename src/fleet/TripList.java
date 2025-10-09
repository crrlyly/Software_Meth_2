package fleet;
import Util.List;
import java.text.DecimalFormat;
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
        compare = compareTrips(trip, last.trip);

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
        int cmp = t1.getBooking().getVehicle().getPlate().compareTo(t2.getBooking().getVehicle().getPlate());
        if (cmp == 0)
            cmp = t1.getBooking().getEnd().compareTo(t2.getBooking().getEnd());
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

    private void groupTripsByDepartment(
            List<Trip> ba, List<Trip> cs, List<Trip> ee,
            List<Trip> iti, List<Trip> math) {

        Node curr = last.next;
        do {
            Trip t = curr.trip;
            Department d = t.getBooking().getEmployee().getDept();

            if (d == Department.BusinessAnalyticsAndInformationTechnology)
                ba.add(t);
            else if (d == Department.ComputerScience)
                cs.add(t);
            else if (d == Department.ElectricalEngineering)
                ee.add(t);
            else if (d == Department.InformationTechnologyAndInformatics)
                iti.add(t);
            else if (d == Department.Math)
                math.add(t);

            curr = curr.next;
        } while (curr != last.next);
    }

    public void printCharges(){
        DecimalFormat df = new DecimalFormat("0.00");
        if (last == null) { System.out.println("No trips in the list."); return; }
        List<Trip> ba = new List<>(), cs = new List<>(), ee = new List<>(), iti = new List<>(), math = new List<>();
        groupTripsByDepartment(ba, cs, ee, iti, math);
        System.out.println("*List of charges ordered by department.");
        List<Trip>[] depts = new List[]{ba, cs, ee, iti, math};
        Department[] deptEnums = Department.toArray();
        for (int i = 0; i < depts.length; i++) {
            if (depts[i].Size() == 0) continue;
            System.out.println("--" + deptEnums[i].convertToWords() + "--");
            double deptTotal = 0;
            for (int j = 0; j < depts[i].Size(); j++) {
                Trip t = depts[i].get(j);
                System.out.println("\t\t" + t); // Trip.toString() prints all details
                int used = t.getEndMileage() - t.getBeginMileage();
                double charge = t.getBooking().getVehicle().charge(used);
                double surcharge = 0;
                String charge1 = df.format(charge);
                String surcharge1;
                if(t.isSurcharge())
                    surcharge = t.getBooking().getVehicle().surcharge(used,t.isSurcharge());
                surcharge1 = "$"+df.format(surcharge);
                if (surcharge ==0)
                    surcharge1 = "no";
                deptTotal += charge + surcharge;
                System.out.println("\t\t\t" + "[charge: $"+charge1+"] [surcharge: "+surcharge1 + "] [total charge: $" + (charge+surcharge)+"]");
            }
            System.out.println("  <*>Department total: $ " + deptTotal);
        }
        System.out.println("*end of list.");
    }

}
