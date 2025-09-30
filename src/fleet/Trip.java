package fleet;
/**
 * This class represents a trip with a booking, beginning mileage, and ending mileage,
 * Also has methods for equality checks and string representation.
 *
 * @author Carly Chick
 */
public class Trip {
    private Booking booking;
    private int beginMileage;
    private int endMileage;

    /**
     * Creates a Trip with the booking, beginning mileage, and ending mileage.
     *
     * @param booking the booking given
     * @param beginMileage the beginning mileage given
     * @param endMileage the ending mileage given
     */
    public Trip(Booking booking, int beginMileage, int endMileage) {
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
    }

    /**
     * Method to get the booking.
     *
     * @return the booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Checks if this booking is equal to the given booking.
     *
     * @param obj the reference object with which to compare.
     * @return true if both bookings are equal;
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Trip other = (Trip) obj;
        return this.booking.equals(other.booking);
    }

    /**
     * Creates a String that shows all the Trip information.
     *
     * @return String that shows all the Trip information
     */
    @Override
    public String toString(){
        String plate = booking.getVehicle().getPlate();
        String beginDate = booking.getBegin().toString();
        String endDate = booking.getEnd().toString();
        String originalMileage = Integer.toString(beginMileage);
        String currentMileage = Integer.toString(endMileage);
        String mileageUsed = Integer.toString(endMileage - beginMileage);
        return plate + " " + beginDate + " ~ " + endDate + " original mileage: " + originalMileage
                + " current mileage: " + currentMileage + " mileage used: " + mileageUsed;
    }
}
