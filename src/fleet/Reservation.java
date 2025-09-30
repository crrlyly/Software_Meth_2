package fleet;
/**
 * This class creates an array of bookings, where if the array is full adds 4 more spaces to the array
 * Then, additional lines of description are added to elaborate on the
 * details if necessary.
 *
 * @author Alec Cotler
 */
public class Reservation {

    public static final int initialArrayLength = 4;
    public static int NOT_FOUND = -1;
    public static int growSize = 4;

    private Booking[] bookings;
    private int size;

    /**
     * Constructor for a new reservation.
     */
    public Reservation() {
        this.size = 0;
        this.bookings = new Booking[initialArrayLength];
    }

    /**
     * Returns the number of bookings in the reservation list..
     *
     * @return size the number of bookings in the reservation list
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the reservation list of bookings.
     *
     * @return bookings
     */
    public Booking[] getBookings() {
        return bookings;
    }

    /**
     * Search for the given booking and return index, -1 if the booking is not in the reservation.
     *
     * @param booking Booking to find in the reservation
     * @return the index of the booking
     */
    private int find(Booking booking) {
        if (booking == null) {
            return NOT_FOUND;
        }
        for (int i = 0; i < size; i++) {
            if (bookings[i].equals(booking)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Resize the array (add 4 spots).
     */
    private void grow() {
        Booking[] newBookings = new Booking[this.bookings.length + growSize];
        for (int i = 0; i < this.bookings.length; i++) {
            newBookings[i] = this.bookings[i]; // copy over each element
        }
        this.bookings = newBookings;
    }

    /**
     * Add booking to end of array.
     *
     * @param booking to add to the end of the array
     */
    public void add(Booking booking) {
        if (booking == null) {
            return;
        }
        if (contains(booking)) {
            return;
        }
        if (this.size == this.bookings.length) {
            this.grow();
        }
        this.bookings[this.size] = booking;
        size++;
    }

    /**
     * Removes a booking from the reservation list by deleting it, and then swapping the last booking in the list into its spot.
     *
     * @param booking to remove from the reservation list
     */
    public void remove(Booking booking) {
        int bookingIndex = NOT_FOUND;
        if (contains(booking)) {
            bookingIndex = find(booking);
        } else return;

        this.bookings[bookingIndex] = bookings[size - 1];
        this.bookings[size - 1] = null;
        size--;
    } //overwrite with last element

    /**
     * Checks if the reservation list contains a specific booking.
     *
     * @param booking to check if the reservation list contains it
     * @return true if the reservation list contains the booking, false if not
     */
    public boolean contains(Booking booking) {
        int index = find(booking);
        if (index == NOT_FOUND) {
            return false;
        }
        return true;
    }

    /**
     * Prints the reservation list in the order of the plates of the vehicle, then beginning date of the reservation.
     */
    public void printByVehicle() {
        sortByPlateThenDate();
        for (int i = 0; i < size; i++) {
            System.out.println(bookings[i]);
        }

    }

    /**
     * Prints the reservation list ordered by department then by employee (alphabetically).
     */
    public void printByDept() {
        sortByDeptThenEmployee();
        Department dept = bookings[0].getEmployee().getDept();
        System.out.println("--" + dept.convertToWords() + "--");
        for (int i = 0; i < size; i++) {
            if(!dept.name().equals(bookings[i].getEmployee().getDept().name())){
                dept = bookings[i].getEmployee().getDept();
                System.out.println("--" + dept.convertToWords() + "--");
            }
            System.out.println(bookings[i]);
        }
    }

    /**
     * Sorts the reservation list in the order of the plates of the vehicle, then beginning date of the reservation.
     */
    public void sortByPlateThenDate() {
        for (int i = 1; i < size; i++) {
            Booking key = bookings[i];
            int index = i - 1;

            while (index >= 0 && (
                    // compare plate alphabetically
                    bookings[index].getVehicle().getPlate().compareTo(key.getVehicle().getPlate()) > 0 ||

                            // if plates are equal, compare by beginDate
                            (bookings[index].getVehicle().getPlate().equals(key.getVehicle().getPlate()) &&
                                    bookings[index].getBegin().compareTo(key.getBegin()) > 0)
            )) {
                bookings[index + 1] = bookings[index];
                index--;
            }
            bookings[index + 1] = key;
        }
    }

    /**
     * Sorts the reservation list ordered by department then by employee (alphabetically).
     */
    public void sortByDeptThenEmployee() {
        for (int i = 1; i < size; i++) {
            Booking key = bookings[i];
            int index = i - 1;
            while (index >= 0 && (
                    // compare department alphabetically
                    bookings[index].getEmployee().getDept().name().compareTo(key.getEmployee().getDept().name()) > 0 ||

                            // if departments are equal, compare employees alphabetically by name
                            (bookings[index].getEmployee().getDept().name().equals(key.getEmployee().getDept().name()) &&
                                    bookings[index].getEmployee().name().compareTo(key.getEmployee().name()) > 0)
            )) {
                bookings[index + 1] = bookings[index];
                index--;
            }
            bookings[index + 1] = key;
        }
    }

    /**
     * Sees if the booking is in the reservation list, and returns the booking if it is or null if not.
     *
     * @param booking to check if it is in the reservation list
     * @return booking if it is in the reservation list
     */
    public Booking bookingLookup(Booking booking) {
        if(booking == null){
            return null;
        }
        int index = find(booking);
        if(index == -1){
            return null;
        }
        return bookings[index];
    }

    /**
     * Finds if a vehicle and ending date combo is in any of the bookings in the reservation list.
     *
     * @param booking to see if its vehicle and end date combo exists in the reservation list
     * @return true if the vehicle and end date combo exists in the reservation list, false if not
     */
    public boolean findInstanceOfVehicle(Booking booking){
        if (booking == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (bookings[i].equalsNoBeginDate(booking)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds if a vehicle is in any of the bookings in the reservation list.
     *
     * @param booking the booking to see if its vehicle exists in the reservation list
     * @return true if a vehicle is in the reservation list, false if not
     */
    public boolean findInstanceOfVehicleOnlyPlate(Booking booking){
        if (booking == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (bookings[i].equalsNoDate(booking)) {
                return true;
            }
        }
        return false;
    }
}

