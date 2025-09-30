package fleet;
import java.util.Scanner;
import java.util.Calendar;
/**
 * Console based frontend for the Vehicle Management System.
 *
 * @author Carly Chick
 */
public class Frontend {
    private boolean running = true;
    private final Fleet fleet = new Fleet();
    private final Reservation reservation = new Reservation();
    private final TripList tripList = new TripList();

    /**
     * Constructs a new Frontend object to manage the vehicle system.
     */
    public Frontend() {

    }

    /**
     * Stops the program loop and prints a termination message.
     */
    private void terminate(){
        System.out.println("Vehicle Management System is terminated.");
        running = false;
    }

    /**
     * Routes a command to the appropriate handler.
     *
     * @param cmd the command token
     * @param parts the tokenized input line from the console
     */
    private void processCommand(String cmd, String[] parts) {
        switch(cmd){
            case "A":
                addToFleet(parts);
                break;
            case "D":
                removeFromFleet(parts);
                break;
            case "B":
                bookVehicle(parts);
                break;
            case "C":
                cancelBooking(parts);
                break;
            case "R":
                returnVehicle(parts);
                break;
            case "PF":
                displayVehicles();
                break;
            case "PR":
                displayBookings1();
                break;
            case "PD":
                displayBookings2();
                break;
            case "PT":
                displayCompletedTrips();
                break;
            default:
                System.out.println(cmd + " - invalid command!");
        }
    }

    /**
     * Finds today's date using the Calendar library and returns it as a Date.
     *
     * @return a Date representing the current day (mm/dd/yyyy)
     */
    private Date getToday(){
        Calendar calendar = Calendar.getInstance();
        String todayString = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH)
                + "/" + calendar.get(Calendar.YEAR);
        return new Date(todayString);
    }

    /**
     * Validates an odometer reading.
     *
     * @param odo the odometer value to check
     * @return true if odo is a positive int; false otherwise
     */
    private boolean isValidOdometer(int odo) {
        try {
            return odo > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Converts a Date to a Calendar set to midnight.
     *
     * @param date the date to convert
     * @return a Calendar set to obj's year/month/day at 00:00:00
     */
    private Calendar convertDateToCalendar(Date date){
        int month = date.getMonth() - 1;
        int day  = date.getDay();
        int year = date.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Finds whole day difference between two dates, ignoring time-of-day.
     *
     * @param startingDate the start date
     * @param endingDate the end date
     * @return number of days between end and start; may be negative if endingDate less than or equal to startingDate;
     * returns 0 if either argument is null
     */
    private int daysBetween(Date startingDate, Date endingDate){
        if (startingDate == null || endingDate == null) return 0;
        Calendar start = convertDateToCalendar(startingDate);
        Calendar end   = convertDateToCalendar(endingDate);

        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        long diffMillis = end.getTimeInMillis() - start.getTimeInMillis();
        return (int) (diffMillis / (24L * 60 * 60 * 1000));
    }

    /**
     * Returns whether two closed date ranges overlap.
     * The ranges are inclusive.
     *
     * @param aStart start of the first range
     * @param aEnd end of the first range
     * @param bStart start of the second range
     * @param bEnd end of the second range
     * @return true if the two ranges overlap at any point; false otherwise
     */
    private boolean rangesOverlapInclusive(Date aStart, Date aEnd, Date bStart, Date bEnd) {
        return aStart.compareTo(bEnd) <= 0 && aEnd.compareTo(bStart) >= 0;
    }

    /**
     * Checks if a vehicle is available for a date range by checking all bookings
     * for that vehicle and seeing if any overlap with the range.
     *
     * @param vehicle the vehicle to check
     * @param reqStart requested start date
     * @param reqEnd requested end date
     * @return true if no existing booking for the vehicle overlaps the range; false otherwise
     */
    private boolean isVehicleAvailable(Vehicle vehicle, Date reqStart, Date reqEnd) {
        Booking[] all = reservation.getBookings();
        if (all == null) return true;
        for (int i = 0; i < all.length; i++) {
            Booking b = all[i];
            if (b == null) continue;
            if (!vehicle.equals(b.getVehicle())) continue;
            if (rangesOverlapInclusive(reqStart, reqEnd, b.getBegin(), b.getEnd())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the employee has any of their own bookings that overlap
     * a date range. Used to prevent self conflicts.
     *
     * @param reqStart requested start date
     * @param reqEnd requested end date
     * @param employee the employee name to match against bookings
     * @return true if the employee has no overlapping bookings; false otherwise
     */
    private boolean isVehicleAvailableSelf(Date reqStart, Date reqEnd, String employee) {
        Booking[] all = reservation.getBookings();
        if (all == null) return true;
        for (int i = 0; i < all.length; i++) {
            Booking b = all[i];
            if (b == null) continue;
            if (!b.getEmployee().name().equalsIgnoreCase(employee)) continue;
            if (rangesOverlapInclusive(reqStart, reqEnd, b.getBegin(), b.getEnd())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes the date exactly three calendar months from "today".
     *
     * @return a Date showing the day three months after the current day
     */
    private Date getThreeMonthsFromToday(){
        Calendar calendar = Calendar.getInstance(); // today
        calendar.add(Calendar.MONTH, 3); // add 3 months
        String threeMonthsLater = (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.DAY_OF_MONTH) + "/"
                + calendar.get(Calendar.YEAR);
        return new Date(threeMonthsLater);
    }

    /**
     * Moves a completed reservation into the trip list.
     * Removes the booking from reservations, then updates vehicle mileage.
     *
     * @param originalBooking the completed booking
     * @param originalMileage the mileage at trip start
     * @param currentMileage the mileage at trip end
     */
    private void moveFromReservationToTripList(Booking originalBooking, int originalMileage, int currentMileage){
        reservation.remove(originalBooking);
        originalBooking.getVehicle().setMileage(currentMileage);
        Trip trip = new Trip(originalBooking, originalMileage, currentMileage);
        System.out.println("Trip completed: " + trip.toString());
        tripList.add(trip);
    }

    /**
     * Finds the index (in reservation) of the booking for the same vehicle
     * whose end date is earliest.
     *
     * @param booking a reference booking used for matching
     * @return the index of the earliest ending booking for that vehicle;
     * -1 if none is found
     */
    private int findIndexOfEarliestEndDate(Booking booking){
        Date earliest = booking.getEnd();
        int index = -1;
        for(int i = 0; i < reservation.getSize(); i++){
            if(reservation.getBookings()[i].getVehicle().equals(booking.getVehicle())){
                if(reservation.getBookings()[i].getEnd().compareTo(earliest) <= 0){
                    earliest = reservation.getBookings()[i].getEnd();
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * Checks requested booking dates against rules:
     * Prints a specific error message and returns false on the first violation.
     *
     * @param parts tokenized input
     * @param startingDate requested start date
     * @param endingDate requested end date
     * @param today today's date
     * @param threeMonthsFromToday the date three months from today
     * @return true if all checks pass; false otherwise (and will print error message)
     */
    private boolean bookVehicleDateChecks(String[] parts, Date startingDate, Date endingDate, Date today, Date threeMonthsFromToday){
        if(!startingDate.isValid()){
            System.out.println(parts[1] + " - beginning date is not a valid calendar date.");
            return false;
        }
        if(startingDate.compareTo(today) < 0){
            System.out.println(parts[1] + " - beginning date is not today or a future date.");
            return false;
        }
        if(startingDate.compareTo(threeMonthsFromToday) > 0){
            System.out.println(startingDate + " - beginning date beyond 3 months.");
            return false;
        }
        if(!endingDate.isValid()){
            System.out.println(parts[2] + " - ending date is not a valid calendar date.");
            return false;
        }
        if(endingDate.compareTo(startingDate) < 0){
            System.out.println(parts[2] + " - ending date must be equal or after the beginning date " + startingDate);
            return false;
        }
        if(daysBetween(startingDate, endingDate) >= 7){
            System.out.println(parts[1] + " ~ " + parts[2] + " - duration more than a week.");
            return false;
        }
        return true;
    }

    /**
     * Adds a vehicle to the fleet.
     *
     * @param parts tokenized input (must be length 5)
     */
    private void addToFleet(String[] parts){
        if(parts.length != 5){
            System.out.println("Not a valid A command.");
            return;
        }
        Date isValidDate = new Date(parts[2]);
        Date today = getToday();
        if(!isValidDate.isValid()){
            System.out.println(parts[2] + " - invalid calendar date.");
            return;
        }
        else if(isValidDate.compareTo(today) >= 0){
            System.out.println(parts[2] + " - is today or a future date.");
            return;
        }
        Make make = Make.parseMake(parts[3]);
        if(make == null){
            System.out.println(parts[3] + " - invalid make.");
            return;
        }
        int odo = Integer.parseInt(parts[4]);
        if (!isValidOdometer(odo)) {
            System.out.println(parts[4] + " - invalid mileage.");
            return;
        }
        Vehicle vehicle = new Vehicle(parts[1], isValidDate, make, odo);
        fleet.add(vehicle);
        System.out.println(vehicle.toString() + " has been added to the fleet.");
    }

    /**
     * Removes a vehicle from the fleet.
     *
     * @param parts tokenized input (must be length 2)
     */
    private void removeFromFleet(String[] parts){
        if(parts.length != 2){
            System.out.println("Not a valid D command.");
            return;
        }
        Vehicle vehicle = new Vehicle(parts[1]);
        if(!fleet.contains(vehicle)){
            System.out.println(vehicle.getPlate() + " is not in the fleet.");
            return;
        }
        Booking booking = new Booking(vehicle);
        if(reservation.findInstanceOfVehicleOnlyPlate(booking)){
            System.out.println(vehicle.getPlate() + " - has existing bookings; cannot be removed.");
            return;
        }
        Vehicle getOriginalVehicle = fleet.getVehicle(vehicle);
        System.out.println(getOriginalVehicle.toString() + " has been removed from the fleet.");
        fleet.remove(vehicle);
    }

    /**
     * Creates a reservation.
     *
     * @param parts tokenized input (must be length 5)
     */
    private void bookVehicle(String[] parts){
        if(parts.length != 5){
            System.out.println("Not a valid B command.");
            return;
        }
        Date startingDate = new Date(parts[1]);
        Date endingDate = new Date(parts[2]);
        Date today = getToday();
        Date threeMonthsFromToday = getThreeMonthsFromToday();
        if (!bookVehicleDateChecks(parts, startingDate, endingDate, today, threeMonthsFromToday)){
            return;
        }
        Vehicle vehicle = new Vehicle(parts[3]);
        if(!fleet.contains(vehicle)){
            System.out.println(vehicle.getPlate() + " is not in the fleet.");
            return;
        }
        if (!isVehicleAvailable(vehicle, startingDate, endingDate)) {
            System.out.println(vehicle.getPlate() + " - booking with " + parts[1] + " ~ " + parts[2] + " not available.");
            return;
        }
        Employee employee = Employee.parseEmployee(parts[4]);
        if(employee == null){
            System.out.println(parts[4] + " - not an eligible employee to book.");
            return;
        }
        if (!isVehicleAvailableSelf(startingDate, endingDate, parts[4])) {
            System.out.println(parts[4].toUpperCase() + " - has an existing booking conflicting with the beginning date " + parts[1]);
            return;
        }
        Booking booking = new Booking(startingDate, endingDate, employee, fleet.getVehicle(vehicle));
        System.out.println(booking.getVehicle().toString() + " [beginning " + booking.getBegin() + " ending "
                + booking.getEnd() + ":" + booking.getEmployee() + "] booked.");
        reservation.add(booking);
    }

    /**
     * Cancels an existing reservation.
     *
     * @param parts tokenized input (must be length 4)
     */
    private void cancelBooking(String[] parts){
        if(parts.length != 4){
            System.out.println("Not a valid B command.");
            return;
        }
        Booking booking = new Booking(new Date(parts[1]),new Date(parts[2]),new Vehicle(parts[3]));
        if(!reservation.contains(booking)){
            System.out.println(parts[3] + ":" + parts[1] + " ~ " + parts[2] + " - cannot find the booking.");
            return;
        }
        Booking getOriginalBooking = reservation.bookingLookup(booking);
        System.out.println(getOriginalBooking.getVehicle().getPlate() + ":" + getOriginalBooking.getBegin() + " ~ "
                + getOriginalBooking.getEnd() + " has been canceled.");
        reservation.remove(booking);
    }

    /**
     * Returns a vehicle and archives the trip.
     *
     * @param parts tokenized input (must be length 4)
     */
    private void returnVehicle(String[] parts){
        if(parts.length != 4){
            System.out.println("Not a valid R command.");
            return;
        }
        Booking booking = new Booking(new Date(parts[1]), new Vehicle(parts[2]));
        if(!reservation.findInstanceOfVehicle(booking)){
            System.out.println(parts[2] + " booked with ending date " + parts[1] + " - cannot find the booking.");
            return;
        }
        int index = findIndexOfEarliestEndDate(booking);
        if(index == -1){
            System.out.println("Not Found");
            return;
        }
        if(!reservation.getBookings()[index].getEnd().equals(booking.getEnd())){
            System.out.println(parts[2] + " booked with ending date " + parts[1] + " - returning not in order of ending date.");
            return;
        }
        int currentMileage = Integer.parseInt(parts[3]);
        if (!isValidOdometer(currentMileage)) {
            System.out.println(parts[3] + " - invalid mileage.");
            return;
        }
        Booking originalBooking = reservation.getBookings()[index];
        int originalMileage = originalBooking.getVehicle().getMileage();
        if(currentMileage <= originalMileage){
            System.out.println("Invalid mileage - current mileage: " + originalMileage + " entered mileage: " + parts[3]);
            return;
        }
        moveFromReservationToTripList(originalBooking, originalMileage, currentMileage);
    }

    /**
     * Prints all vehicles in the fleet ordered by make and obtained date.
     */
    private void displayVehicles(){
        if(fleet.getSize() == 0){
            System.out.println("There is no vehicle in the fleet.");
            return;
        }
        System.out.println("*List of vehicles in the fleet, ordered by make and date obtained.");
        fleet.printByMake();
        System.out.println("*end of list.");
    }

    /**
     * Prints reservations ordered by license plate and beginning date.
     */
    private void displayBookings1(){
        if(reservation.getSize() == 0){
            System.out.println("There is no booking record.");
            return;
        }
        System.out.println("*List of reservations ordered by license plate number and beginning date.");
        reservation.printByVehicle();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Prints reservations ordered by department and employee.
     */
    private void displayBookings2(){
        if(reservation.getSize() == 0){
            System.out.println("There is no booking record.");
            return;
        }
        System.out.println("*List of reservations ordered by department and employee.");
        reservation.printByDept();
        System.out.println("*end of list.");
    }

    /**
     * Prints archived trips ordered by ending date.
     */
    private void displayCompletedTrips(){
        if(tripList.getLast() == null){
            System.out.println("There is no archived trips.");
            return;
        }
        System.out.println("*List of completed trips ordered by ending date.");
        tripList.print();
        System.out.println("*end of list.");
    }

    /**
     * Runs the command loop for the Vehicle Management System.
     */
    public void run() {
        System.out.println("Vehicle Management System is running.");
        Scanner sc = new Scanner(System.in);
        while (running) {
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if (line.equals("Q")) {
                terminate();
                return;
            } else if (!line.isEmpty()) {
                String[] parts = line.split("\\s+");
                String command = parts[0];
                processCommand(command, parts);
            }
        }
        sc.close();
    }
}
