package fleet;
/**
 * The class represents a vehicle with a license plate, obtained date, make, and mileage,
 * Also has methods for comparison, equality checks, and string representation.
 *
 * @author Carly Chick
 */
public class Vehicle implements Comparable<Vehicle> {
    private String plate;
    private Date obtained;
    private Make make;
    private int mileage;

    /**
     * Creates a Vehicle with the given license plate.
     *
     * @param plate the license plate identifier for the vehicle
     */
    public Vehicle(String plate) {
        this.plate = plate;
    }

    /**
     * Creates a Vehicle with the given license plate, obtained date, make, and mileage.
     *
     * @param plate the license plate identifier for the vehicle
     * @param obtained the date the vehicle was acquired
     * @param make the manufacturer or brand of the vehicle
     * @param mileage the mileage (in miles) recorded for the vehicle
     */
    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;
    }

    /**
     * Checks if two vehicles are equal by their license plates.
     *
     * @param obj the reference object with which to compare.
     * @return true if the plates are not null and their license plates are the same; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Vehicle other = (Vehicle) obj;
        return this.plate != null && this.plate.equals(other.plate);
    }

    /**
     * Method that creates a String that shows the information of the Vehicle.
     *
     * @return a string explaining all the vehicle information
     */
    @Override
    public String toString() {
        return this.plate + ":" + this.make + ":" + this.obtained + " [mileage:" + this.mileage + "]";
    }

    /**
     * Compares "this" vehicle to another vehicle (passed in parameter) based on Make and then obtained date.
     *
     * @param other the Vehicle to compare to
     * @return 0 if both the Make and obtained date are the same;
     * -1 if this Vehicle's Make comes before the other Vehicle's Make alphabetically,
     * or if the Makes are equal but this Vehicle's obtained date is earlier;
     * 1 if this Vehicle's Make comes after the other Vehicle's Make alphabetically,
     * or if the Makes are equal but this Vehicle's obtained date is later;
     * Null pointer exception if the given Vehicle is null
     */
    @Override
    public int compareTo(Vehicle other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare to null Vehicle");
        }
        int byMake = Make.compareStringsIgnoreCase(this.getMake().name(), other.getMake().name());
        if (byMake != 0) {
            return byMake < 0 ? -1 : 1;
        }
        int byDate = this.getObtained().compareTo(other.getObtained());
        if (byDate != 0) {
            return byDate < 0 ? -1 : 1;
        }
        return 0;
    }

    /**
     * Method to get the license plate.
     *
     * @return the license plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Method to get the obtained date.
     *
     * @return the obtained date
     */
    public Date getObtained() {
        return obtained;
    }

    /**
     * Method to get the Make.
     *
     * @return the Make
     */
    public Make getMake() {
        return make;
    }

    /**
     * Method to get the mileage.
     *
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Method to set a new mileage.
     *
     * @param mileage the new mileage to be set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * Test Case 1: Testing method that compares the Make and Obtained Date of two equal vehicles.
     */
    private static void testVehiclesEqual() {
        Vehicle one = new Vehicle("192837D", new Date("1/15/2008"), Make.CHEVY, 38);
        Vehicle two = new Vehicle("192837D", new Date("1/15/2008"), Make.CHEVY, 38);

        int expectedOutput = 0;
        int actualOutput = one.compareTo(two);
        System.out.println("**Test case 1: Testing two same vehicles");
        testResult(one, two, expectedOutput, actualOutput);
    }

    /**
     * Test Case 2: Testing method that compares the Make and Obtained Date of two vehicles where the makes
     * are the same but the first vehicle has an earlier Obtained Date.
     */
    private static void testVehiclesSameMakeEarlierThis() {
        Vehicle one = new Vehicle("192437D", new Date("1/15/2008"), Make.CHEVY, 38);
        Vehicle two = new Vehicle("194837D", new Date("1/16/2008"), Make.CHEVY, 38);

        int expectedOutput = -1;
        int actualOutput = one.compareTo(two);
        System.out.println("**Test case 2: Testing two vehicles with the same make but this vehicle has an earlier date " +
                "than the vehicle that was passed in.");
        testResult(one, two, expectedOutput, actualOutput);
    }

    /**
     * Test Case 3: Testing method that compares the Make and Obtained Date of two vehicles where the
     * Make of the first vehicle comes before the second vehicle's make alphabetically.
     */
    private static void testVehiclesDiffMakesBiggerThis() {
        Vehicle one = new Vehicle("192437D", new Date("1/15/2008"), Make.FORD, 38);
        Vehicle two = new Vehicle("194837D", new Date("1/16/2008"), Make.CHEVY, 38);

        int expectedOutput = 1;
        int actualOutput = one.compareTo(two);
        System.out.println("**Test case 3: Testing two vehicles where this vehicle has a " +
                "make that comes after the second vehicle's make alphabetically.");
        testResult(one, two, expectedOutput, actualOutput);
    }

    /**
     * Method that tests the testing methods.
     *
     * @param one the first Vehicle object
     * @param two the second Vehicle object
     * @param expectedOutput the correct output
     * @param actualOutput the output that the testing method gave
     */
    private static void testResult(Vehicle one, Vehicle two, int expectedOutput, int actualOutput) {
        System.out.println("Test input: " + one + " and " + two);
        System.out.println("Expected output: " + expectedOutput);
        System.out.println("Actual output: " + actualOutput);
        System.out.println(expectedOutput != actualOutput ? " (FAIL)\n" : " (PASS)\n");
    }

    /**
     * Testbed main to test the compareTo method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        testVehiclesEqual();
        testVehiclesSameMakeEarlierThis();
        testVehiclesDiffMakesBiggerThis();
    }
}
