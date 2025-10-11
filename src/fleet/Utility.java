package fleet;

import Util.Date;
/**
 * This class creates a circularly linked list of trips.
 * Additionally, it inserts the trips in order of earliest end date, and can print the list/find the number of trips in the list.
 *
 * @author Carly Chick
 */
public class Utility extends Vehicle{
    public Utility(String plate) {
        super(plate);
    }

    public Utility(String plate, Date obtained, Make make, int mileage, Campus campus) {
        super(plate, obtained, make, mileage, campus);
    }

    @Override
    public double charge(int mileageUsed){
        return (mileageUsed * 1.99);
    }

    @Override
    public double surcharge(int mileageUsed, boolean surcharge){
        if(surcharge){
            return (.3 * mileageUsed < 35.99 ? (.3 * mileageUsed) : 35.99);
        }
        return -1;
    }
}
