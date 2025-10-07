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
    private boolean surcharge;

    /**
     * Creates a Trip with the booking, beginning mileage, and ending mileage.
     *
     * @param booking the booking given
     * @param beginMileage the beginning mileage given
     * @param endMileage the ending mileage given
     */
    public Trip(Booking booking, int beginMileage, int endMileage, boolean surcharge) {
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
        this.surcharge = surcharge;
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
    //TODO
    @Override
    public String toString(){
        String plate = booking.getVehicle().getPlate();
        String beginDate = booking.getBegin().toString();
        String endDate = booking.getEnd().toString();
        String originalMileage = Integer.toString(beginMileage);
        String currentMileage = Integer.toString(endMileage);
        String mileageUsed = Integer.toString(endMileage - beginMileage);
        String surchargeAsterick = surcharge ? "**" : "";
        return plate + " " + beginDate + " ~ " + endDate + " mileage(old): " + originalMileage
                + " mileage(new): " + currentMileage + " mileage(used): " + mileageUsed + " [dropped off: " + this.booking.getDropoff()
                + surchargeAsterick + "] [picked up:" + this.booking.getVehicle().getCampus() + "]";
    }
}
