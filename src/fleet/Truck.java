package fleet;

import Util.Date;
/**
 * This class creates a circularly linked list of trips.
 * Additionally, it inserts the trips in order of earliest end date, and can print the list/find the number of trips in the list.
 *
 * @author Carly Chick
 */
public class Truck extends Vehicle{
    public Truck(String plate) {
        super(plate);
    }

    public Truck(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    @Override
    public double charge(int mileageUsed){
        return (mileageUsed * 2.99);
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge){
        if(surcharge){
            return 39.99;
        }
        return -1;
    }
}
